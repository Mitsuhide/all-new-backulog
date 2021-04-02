package com.ttop.backulog.wrapper

import android.util.Log
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ttop.backulog.wrapper.FirestoreWrapper.Response.Failure
import com.ttop.backulog.wrapper.FirestoreWrapper.Response.Success
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirestoreWrapper {

    private val db = Firebase.firestore

    suspend fun fetch(collectionName: String): Response {
        val collection = db.collection(collectionName).get()
        return suspendCoroutine { continuation ->
            collection
                .addOnSuccessListener {
                    continuation.resume(Success(it))
                }
                .addOnFailureListener { exception ->
                    Log.w(FIRESTORE_TAG, "Error getting documents.", exception)
                    continuation.resume(Failure)
                }
        }
    }

    fun store(mediaToStore: HashMap<String, String>, collectionName: String) {
        db.collection(collectionName)
            .add(mediaToStore)
            .addOnSuccessListener { documentReference ->
                Log.d(FIRESTORE_TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(FIRESTORE_TAG, "Error adding document", e)
            }
    }

    sealed class Response {
        data class Success(val result: QuerySnapshot) : Response()
        object Failure : Response()
    }

    companion object {
        const val FIRESTORE_TAG = "FIRESTORE"
        const val TITLE_FIELD = "title"
        const val STATUS_FIELD = "status"
    }

}