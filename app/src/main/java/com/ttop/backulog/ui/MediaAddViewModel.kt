package com.ttop.backulog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttop.backulog.domain.Media
import com.ttop.backulog.domain.Status
import com.ttop.backulog.repository.MediaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MediaAddViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val repository: MediaRepository
) : ViewModel() {

    fun storeMedia(name: String) = viewModelScope.launch(dispatcher){
        repository.addMedia(
            Media.Jeu(
                titre = name,
                etat = Status.ONGOING
            )
        )
    }
}