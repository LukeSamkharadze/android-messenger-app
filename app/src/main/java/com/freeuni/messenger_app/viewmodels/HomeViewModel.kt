package com.freeuni.messenger_app.viewmodels

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.freeuni.messenger_app.models.Friend
import com.freeuni.messenger_app.models.User
import com.freeuni.messenger_app.repositories.AuthRepository
import com.freeuni.messenger_app.repositories.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.UploadTask

class HomeViewModel(application: Application) : AndroidViewModel(application) {
  private val authRepository: AuthRepository = AuthRepository(application)
  private val userRepository: UserRepository = UserRepository()

  val friendsLiveData: LiveData<ArrayList<Friend>> = userRepository.getFriendsLiveData()
  val userFirebaseLiveData: LiveData<FirebaseUser?> = authRepository.getFirebaseUserLiveData()
  var userLiveData: LiveData<User?> = userRepository.getUserLiveData()

//  val homePageUsers: LiveData<List<FirebaseUser>> = authRepository

  fun uploadProfile(uri: Uri): UploadTask {
    return userRepository.uploadProfile(uri)
  }

  suspend fun getProfilePicUrl(userId: String): Uri? {
    return userRepository.getProfilePicUrl(userId)
  }

  fun saveUser(uid: String, email: String, bio: String): Task<Void> {
    return userRepository.saveUser(uid, email, bio)
  }

  fun signOut() {
    authRepository.signOut()
  }

  suspend fun searchUsers(name: String): List<User> {
    return userRepository.searchUsers(name)
  }
}