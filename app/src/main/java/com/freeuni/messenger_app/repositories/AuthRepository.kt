package com.freeuni.messenger_app.repositories

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AuthRepository(private var application: Application) {
  private var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
  private var auth: FirebaseAuth = FirebaseAuth.getInstance()

  init {
    firebaseUserMutableLiveData.postValue(auth.currentUser)
  }

  fun getFirebaseUserLiveData(): LiveData<FirebaseUser?> {
    return firebaseUserMutableLiveData
  }

  fun register(email: String, pass: String, callback: ((o: Task<AuthResult>) -> Unit)?) {
    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        firebaseUserMutableLiveData.postValue(auth.currentUser)
      } else {
        Toast.makeText(application, task.exception?.message, Toast.LENGTH_SHORT).show()
      }
      if (callback != null) {
        callback(task)
      }
    }
  }

  fun login(email: String, pass: String, callback: ((o: Task<AuthResult>) -> Unit)?) {
    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        firebaseUserMutableLiveData.postValue(auth.currentUser)
      } else {
        Toast.makeText(application, task.exception?.message, Toast.LENGTH_SHORT).show()
      }
      if (callback != null) {
        callback(task)
      }
    }
  }

  fun signOut() {
    auth.signOut()
    firebaseUserMutableLiveData.postValue(null)
  }
}