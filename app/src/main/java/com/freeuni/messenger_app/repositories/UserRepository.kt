package com.freeuni.messenger_app.repositories

import android.net.Uri
import com.freeuni.messenger_app.models.User
import com.google.android.gms.tasks.Task
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

  fun uploadProfile(uri: Uri): UploadTask {
    return storage.getReference(auth.uid!!).putFile(uri)
  }

  fun saveUser(uid: String, email: String, bio: String): Task<Void> {
    return db.collection("users").document(uid).set(User(uid, email, bio))
//    document.a = 1;?
//    return db.child("users").child(uid).setValue(User(uid, email, bio))
  }

  suspend fun getProfilePicUrl(userId: String): Uri? {
    try {
      val image = storage.reference.child(userId)
      return image.downloadUrl.await()
    } catch (err: Error) {
      return null
    }
//    val ref = storage.reference.child("images/mountains.jpg")
//    val uploadTask = ref.putFile(file)
//
//    val urlTask = uploadTask.continueWithTask { task ->
//      if (!task.isSuccessful) {
//        task.exception?.let {
//          throw it
//        }
//      }
//      ref.downloadUrl
//    }.addOnCompleteListener { task ->
//      if (task.isSuccessful) {
//        val downloadUri = task.result
//      } else {
//        // Handle failures
//        // ...
//      }
//    }
  }
}