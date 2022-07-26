package com.freeuni.messenger_app.repositories

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AuthRepository {
  private lateinit var application: Application
  private lateinit var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser>
  private lateinit var auth: FirebaseAuth

  fun init(application: Application) {
    this.application = application
    firebaseUserMutableLiveData = MutableLiveData()
    auth = FirebaseAuth.getInstance()
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