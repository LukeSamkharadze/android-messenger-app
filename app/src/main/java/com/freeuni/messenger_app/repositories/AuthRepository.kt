package com.freeuni.messenger_app.repositories

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.freeuni.messenger_app.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class AuthRepository(private var application: Application) {
  private var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
  private var auth: FirebaseAuth = FirebaseAuth.getInstance()
  private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

  init {
    firebaseUserMutableLiveData.postValue(auth.currentUser)
  }

  fun getFirebaseUserLiveData(): LiveData<FirebaseUser?> {
    return firebaseUserMutableLiveData
  }

  fun register(email: String, pass: String): Task<AuthResult> {
    return auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        firebaseUserMutableLiveData.postValue(auth.currentUser)
      } else {
        Toast.makeText(application, task.exception?.message, Toast.LENGTH_SHORT).show()
      }
    }
  }

  fun login(email: String, pass: String): Task<AuthResult> {
    return auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        firebaseUserMutableLiveData.postValue(auth.currentUser)
      } else {
        Toast.makeText(application, task.exception?.message, Toast.LENGTH_SHORT).show()
      }
    }
  }

  fun signOut() {
    auth.signOut()
    firebaseUserMutableLiveData.postValue(null)
  }

  fun saveUser(uid: String, email: String, bio: String): Task<Void> {
    return db.collection("users").document(uid).set(User(uid, email, bio))
//    document.a = 1;?
//    return db.child("users").child(uid).setValue(User(uid, email, bio))
  }
}