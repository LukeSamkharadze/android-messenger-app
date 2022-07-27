package com.codingstuff.loginsignupmvvm.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.freeuni.messenger_app.repositories.AuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: AuthRepository = AuthRepository(application)

  val user: LiveData<FirebaseUser?> = repository.getFirebaseUserLiveData()

  fun register(email: String, pass: String, callback: ((o: Task<AuthResult>) -> Unit)?) {
    repository.register(email, pass, callback)
  }

  fun signIn(email: String, pass: String, callback: ((o: Task<AuthResult>) -> Unit)?) {
    repository.login(email, pass, callback)
  }

  fun signOut() {
    repository.signOut()
  }

  fun signIn(email: String, pass: String) {
    this.signIn(email, pass, null)
  }

  fun register(email: String, pass: String) {
    this.register(email, pass, null)
  }
}
