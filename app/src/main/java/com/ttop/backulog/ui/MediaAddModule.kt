package com.ttop.backulog.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.ttop.backulog.repository.MediaRepository
import com.ttop.backulog.repository.MediaRepositoryImpl
import com.ttop.backulog.wrapper.FirestoreWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MediaAddModule {

    private lateinit var factory: MediaAddViewModelFactory

    fun getViewModel(fragment: Fragment): MediaAddViewModel {
        if (::factory.isInitialized.not()) {
            factory = MediaAddViewModelFactory(
                dispatcher = Dispatchers.IO,
                repository = MediaRepositoryImpl(FirestoreWrapper())
            )
        }
        return ViewModelProvider(fragment as ViewModelStoreOwner, factory).get(MediaAddViewModel::class.java)
    }

    class MediaAddViewModelFactory(
        private val dispatcher: CoroutineDispatcher,
        private val repository: MediaRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MediaAddViewModel(dispatcher, repository) as T
        }
    }

}