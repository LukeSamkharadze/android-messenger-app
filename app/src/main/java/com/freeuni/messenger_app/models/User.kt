package com.freeuni.messenger_app.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

class User {
  var uid: String? = null
  var email: String? = null
  var bio: String? = null

  constructor() {}

  constructor (uid: String, email: String, bio: String) {
    this.uid = uid
    this.email = email
    this.bio = bio
  }
}