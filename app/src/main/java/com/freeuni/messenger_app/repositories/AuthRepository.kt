package com.freeuni.messenger_app.repositories

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AuthRepository(private var application: Application) {
  private var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
  private var auth: FirebaseAuth = FirebaseAuth.getInstance()

  init {
    if (auth.currentUser != null) {
      firebaseUserMutableLiveData.postValue(auth.currentUser)
    }
  }

  fun getFirebaseUserLiveData(): LiveData<FirebaseUser> {
    return firebaseUserMutableLiveData
  }

  fun register(email: String, pass: String) {
    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        firebaseUserMutableLiveData.postValue(auth.currentUser)
      } else {
        Toast.makeText(application, task.exception?.message, Toast.LENGTH_SHORT).show()
      }
    }
  }

  fun login(email: String, pass: String) {
    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        firebaseUserMutableLiveData.postValue(auth.currentUser)
      } else {
        Toast.makeText(application, task.exception?.message, Toast.LENGTH_SHORT).show()
      }
    }
  }

  fun signOut() {
    auth.signOut()
  }
}