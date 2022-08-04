package com.freeuni.messenger_app.models

import com.google.firebase.Timestamp

class Message {
  var message: String? = null
  var date: Timestamp? = null


  constructor() {}

  constructor (message: String, date: Timestamp) {
    this.message = message
    this.date = date
  }
}

class MessagesListDocument {
  var messages: List<Message>? = null

  constructor() {}

  constructor (messages: List<Message>) {
    this.messages = messages
  }
}