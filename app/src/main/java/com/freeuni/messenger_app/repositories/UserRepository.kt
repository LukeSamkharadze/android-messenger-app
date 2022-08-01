package com.freeuni.messenger_app.repositories

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.freeuni.messenger_app.models.Friend
import com.freeuni.messenger_app.models.FriendDocument
import com.freeuni.messenger_app.models.FriendListDocument
import com.freeuni.messenger_app.models.User
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.android.gms.tasks.Tasks
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

  private var userLiveData: MutableLiveData<User?> = MutableLiveData()

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

    db.collection("users").document(auth.currentUser!!.uid).addSnapshotListener { snapshot, e ->
      if (e != null) {
        return@addSnapshotListener
      }

      if (snapshot != null && snapshot.exists()) {
        userLiveData.postValue(snapshot.toObject(User::class.java))
      } else {
        userLiveData.postValue(null)
      }
    }
  }

  fun emitCurrUser() {
    if (auth.currentUser != null) {
      db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {
        val user = it.toObject(User::class.java)
        userLiveData.postValue(user)
      }
    }
  }

  fun getUserLiveData(): LiveData<User?> {
    return userLiveData
  }

  suspend fun searchUsers(name: String): List<User> {
    return db.collection("users")
      .whereGreaterThanOrEqualTo("email", name)
      .whereLessThanOrEqualTo("email", name + "\uf8ff").get().await().toObjects(User::class.java)
  }

  fun uploadProfile(uri: Uri): UploadTask {
    return storage.getReference(auth.uid!!).putFile(uri)
  }

  fun saveUser(uid: String, email: String, bio: String): Task<Void> {
//      return auth.currentUser!!.updateEmail(email).addOnCompleteListener { it  ->
//        if(it.isSuccessful) {
//
//        }
//
//        Log.e("oe", it.exception.toString())
//      }
    return db.collection("users").document(uid).set(User(uid, email, bio))
//    return Tasks.whenAllSuccess(
//    )
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