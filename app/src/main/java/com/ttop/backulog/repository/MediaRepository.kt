package com.ttop.backulog.repository

import android.util.Log
import com.google.firebase.firestore.QuerySnapshot
import com.ttop.backulog.domain.Category
import com.ttop.backulog.domain.Media
import com.ttop.backulog.domain.Status
import com.ttop.backulog.wrapper.FirestoreWrapper
import com.ttop.backulog.wrapper.FirestoreWrapper.Companion.STATUS_FIELD
import com.ttop.backulog.wrapper.FirestoreWrapper.Companion.TITLE_FIELD
import com.ttop.backulog.wrapper.FirestoreWrapper.Response.*

interface MediaRepository {
    suspend fun addMedia(media: Media)
    suspend fun getMedia(categorie: Category): List<Media>
}

class MediaRepositoryImpl(private val firestoreWrapper: FirestoreWrapper): MediaRepository {

    override suspend fun addMedia(media: Media) {
        val collectionName = when (media) {
            is Media.Jeu -> GAMES_COLLECTION
            is Media.Film -> FILMS_COLLECTION
        }
        val mediaToStore = buildMediaToStore(media)
        firestoreWrapper.store(mediaToStore, collectionName)
    }

    override suspend fun getMedia(categorie: Category): List<Media> {
        val response = firestoreWrapper.fetch(getCollectionName(categorie))
        return if (response is Success) {
            response.result.toListMedia()
        } else {
            emptyList()
        }
    }

    private fun getCollectionName(categorie: Category) = if (categorie != Category.FILM) {
        GAMES_COLLECTION
    } else {
        FILMS_COLLECTION
    }

    private fun QuerySnapshot.toListMedia() = this.map { document ->
        Log.d(FirestoreWrapper.FIRESTORE_TAG, "${document.id} => ${document.data}")
        Media.Jeu(
            titre = document.data[TITLE_FIELD] as String,
            etat = (document.data[STATUS_FIELD] as String).toStatus()
        )
    }

    private fun buildMediaToStore(media: Media): HashMap<String, String> = hashMapOf(
        "title" to media.titre,
        "status" to media.etat.toStatusString()
    )

    companion object {
        const val GAMES_COLLECTION = "games"
        const val FILMS_COLLECTION = "movies"
    }

}

fun Status.toStatusString() = when (this) {
    Status.ONGOING -> "ongoing"
    Status.PLANNED -> "planned"
    Status.FINISHED -> "finished"
}

fun String.toStatus() = when (this) {
    "ongoing" -> Status.ONGOING
    "planned" -> Status.PLANNED
    "finished" -> Status.FINISHED
    else -> Status.PLANNED
}
