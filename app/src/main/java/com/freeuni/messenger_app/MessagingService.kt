package com.freeuni.messenger_app

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {
  override fun onMessageReceived(message: RemoteMessage) {
    super.onMessageReceived(message)
  }

  override fun onNewToken(token: String) {
    super.onNewToken(token)
  }
}