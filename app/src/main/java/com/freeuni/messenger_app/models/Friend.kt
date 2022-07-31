package com.freeuni.messenger_app.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class Friend(val user: User, val lastMessage: String, val lastMessageDate: Timestamp)

class FriendDocument {
  var userId: DocumentReference? = null
  var lastMessage: String? = null
  var lastMessageDate: Timestamp? = null

  constructor() {}

  constructor (userId: DocumentReference, lastMessage: String, lastMessageDate: Timestamp) {
    this.userId = userId
    this.lastMessage = lastMessage
    this.lastMessageDate = lastMessageDate
  }
}

class FriendListDocument {
  var friends: List<FriendDocument>? = null

  constructor() {}

  constructor (friendDocuments: List<FriendDocument>) {
    this.friends = friendDocuments
  }
}