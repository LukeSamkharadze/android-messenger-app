package com.codingstuff.loginsignupmvvm.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.freeuni.messenger_app.models.User
import com.freeuni.messenger_app.repositories.AuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: AuthRepository = AuthRepository(application)

  val user: LiveData<FirebaseUser?> = repository.getFirebaseUserLiveData()

  fun register(email: String, pass: String): Task<AuthResult> {
    return repository.register(email, pass)
  }

  fun signIn(email: String, pass: String): Task<AuthResult> {
    return repository.login(email, pass)
  }

  fun signOut() {
    repository.signOut()
  }

  fun saveUser(uid: String, email: String, bio: String): Task<Void> {
    return repository.saveUser(uid, email, bio)
  }
}
