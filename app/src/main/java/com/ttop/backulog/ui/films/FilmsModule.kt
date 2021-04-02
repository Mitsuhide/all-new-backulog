package com.ttop.backulog.ui.films

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.ttop.backulog.repository.MediaRepository
import com.ttop.backulog.repository.MediaRepositoryImpl
import com.ttop.backulog.wrapper.FirestoreWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class FilmsModule {

    private lateinit var factory: FilmsViewModelFactory

    fun getViewModel(fragment: Fragment): FilmsViewModel {

        if (::factory.isInitialized.not()) {
            factory = FilmsViewModelFactory(
                dispatcher = Dispatchers.IO,
                repository = MediaRepositoryImpl(FirestoreWrapper())
            )
        }
        return ViewModelProvider(fragment as ViewModelStoreOwner, factory).get(FilmsViewModel::class.java)
    }

    class FilmsViewModelFactory(
        private val dispatcher: CoroutineDispatcher,
        private val repository: MediaRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FilmsViewModel(dispatcher, repository) as T
        }
    }

}