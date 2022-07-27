package com.freeuni.messenger_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.freeuni.messenger_app.repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser

class HomeViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: AuthRepository = AuthRepository(application)

  val user: LiveData<FirebaseUser?> = repository.getFirebaseUserLiveData()

  fun signOut() {
    repository.signOut()
  }
}