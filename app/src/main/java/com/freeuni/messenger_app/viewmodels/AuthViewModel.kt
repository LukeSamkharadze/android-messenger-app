package com.codingstuff.loginsignupmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.freeuni.messenger_app.repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: AuthRepository = AuthRepository(application)

  val userData: LiveData<FirebaseUser> = repository.getFirebaseUserLiveData()

  fun register(email: String, pass: String) {
    repository.register(email, pass)
  }

  fun signIn(email: String, pass: String) {
    repository.login(email, pass)
  }

  fun signOut() {
    repository.signOut()
  }
}
