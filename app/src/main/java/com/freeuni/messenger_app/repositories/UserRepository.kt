package com.freeuni.messenger_app.repositories

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.freeuni.messenger_app.models.Friend
import com.freeuni.messenger_app.models.FriendDocument
import com.freeuni.messenger_app.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await
import java.lang.Error
import java.util.*

class UserRepository {
  private var auth: FirebaseAuth = FirebaseAuth.getInstance()
  private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
  private var storage: FirebaseStorage = FirebaseStorage.getInstance()

  private val friendsLiveData: MutableLiveData<List<Friend>> = MutableLiveData()

  init {
    db.collection("friends").document(auth.uid!!).get().addOnSuccessListener {
      val friends: List<Friend>? = it.toObject(FriendDocument::class.java)!!.friends

      if (friends != null) {
        friendsLiveData.postValue(friends!!)
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