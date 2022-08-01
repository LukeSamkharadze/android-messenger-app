package com.freeuni.messenger_app.extensions

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

fun Timestamp.toMessageDate(): String {
  val nowSeconds = System.currentTimeMillis().div(1000)
  val secondsDifference = nowSeconds - seconds
  val minutesDifference = secondsDifference.div(60)
  val hoursDifference = secondsDifference.div(3600)

  if (minutesDifference < 60) {
    return "${minutesDifference} MINS"
  }

  if (hoursDifference < 24) {
    return "${hoursDifference} HRS"
  }

  return SimpleDateFormat("dd MMM").format(this.toDate()).uppercase()
}