package com.ttop.backulog.ui.jeux

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttop.backulog.domain.Category
import com.ttop.backulog.domain.Media
import com.ttop.backulog.repository.MediaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class JeuxViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val repository: MediaRepository
) : ViewModel() {

    val state = MutableLiveData<State>()

    fun getMediaJeuList() = viewModelScope.launch(dispatcher) {
        state.postValue(State.Loading)
        val list = repository.getMedia(Category.JEU)
        if (list.isNotEmpty()) {
            state.postValue(State.Success(list))
        }
    }

    sealed class State {
        object Loading : State()
        object Failure : State()
        data class Success(val medias: List<Media>): State()
    }

}