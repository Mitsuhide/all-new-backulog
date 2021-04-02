package com.ttop.backulog.ui.jeux

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.ttop.backulog.repository.MediaRepository
import com.ttop.backulog.repository.MediaRepositoryImpl
import com.ttop.backulog.wrapper.FirestoreWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class JeuxModule {

    private lateinit var factory: JeuxViewModelFactory

    fun getViewModel(fragment: Fragment): JeuxViewModel {

        if (::factory.isInitialized.not()) {
            factory = JeuxViewModelFactory(
                dispatcher = Dispatchers.IO,
                repository = MediaRepositoryImpl(FirestoreWrapper())
            )
        }
        return ViewModelProvider(fragment as ViewModelStoreOwner, factory).get(JeuxViewModel::class.java)
    }

    class JeuxViewModelFactory(
        private val dispatcher: CoroutineDispatcher,
        private val repository: MediaRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return JeuxViewModel(dispatcher, repository) as T
        }
    }

}