package com.freeuni.messenger_app.models

import com.google.firebase.Timestamp

class Friend {
  var userId: String? = null
  var lastMessage: String? = null
  var lastMessageDate: Timestamp? = null

  constructor() {}

  constructor (userId: String, lastMessage: String, lastMessageDate: Timestamp) {
    this.userId = userId
    this.lastMessage = lastMessage
    this.lastMessageDate = lastMessageDate
  }
}


class FriendDocument {
  var friends: List<Friend>? = null

  constructor() {}

  constructor (friends: List<Friend>) {
    this.friends = friends
  }
}