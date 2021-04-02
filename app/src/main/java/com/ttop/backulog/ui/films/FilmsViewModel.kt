package com.ttop.backulog.ui.films

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttop.backulog.domain.Category
import com.ttop.backulog.domain.Media
import com.ttop.backulog.repository.MediaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class FilmsViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val repository: MediaRepository
) : ViewModel() {

    val medias = MutableLiveData<List<Media>>()

    fun getMediaFilmList() = viewModelScope.launch(dispatcher) {
        medias.postValue(repository.getMedia(Category.FILM))
    }

}