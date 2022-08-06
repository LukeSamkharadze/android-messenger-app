package com.freeuni.messenger_app.models

import com.google.firebase.Timestamp

class Message {
  var message: String? = null
  var date: Timestamp? = null
  var from: String? = null

  constructor() {}

  constructor (message: String, date: Timestamp, from: String) {
    this.message = message
    this.date = date
    this.from = from
  }
}

class MessagesListDocument {
  var messages: List<Message>? = null

  constructor() {}

  constructor (messages: List<Message>) {
    this.messages = messages
  }
}