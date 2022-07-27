package com.freeuni.messenger_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.freeuni.messenger_app.activities.auth.AuthActivity

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_auth)

    finish()
    startActivity(Intent(this, AuthActivity::class.java))
  }
}