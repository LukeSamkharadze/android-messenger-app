package com.freeuni.messenger_app.repositories

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.freeuni.messenger_app.models.Friend
import com.freeuni.messenger_app.models.FriendDocument
import com.freeuni.messenger_app.models.FriendListDocument
import com.freeuni.messenger_app.models.User
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await
import java.lang.Error

class UserRepository {
  private var auth: FirebaseAuth = FirebaseAuth.getInstance()
  private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
  private var storage: FirebaseStorage = FirebaseStorage.getInstance()

  private val friendsLiveData: MutableLiveData<ArrayList<Friend>> = MutableLiveData()

  init {
    db.collection("friends").document(auth.uid!!).get().addOnSuccessListener {
      try {

        val friendDocuments = it.toObject(FriendListDocument::class.java)!!.friends

        val friends = arrayListOf<Friend>()

        friendDocuments!!.forEach { friendDocument ->
          friendDocument.userId!!.get().addOnSuccessListener {
            friends.add(
              Friend(
                it.toObject(User::class.java)!!,
                friendDocument.lastMessage!!,
                friendDocument.lastMessageDate!!
              )
            )
            friendsLiveData.postValue(friends)
          }
        }
      } catch (err: Error) {

      }
    }
  }

  fun uploadProfile(uri: Uri): UploadTask {
    return storage.getReference(auth.uid!!).putFile(uri)
  }

  fun saveUser(uid: String, email: String, bio: String): Task<Void> {
    return db.collection("users").document(uid).set(User(uid, email, bio))
  }

  suspend fun getProfilePicUrl(userId: String): Uri? {
    try {
      val image = storage.reference.child(userId)
      return image.downloadUrl.await()
    } catch (err: Error) {
      return null
    }
  }
}