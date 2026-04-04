package com.example.personal_finance_app.ui.main

import android.content.Intent
import com.example.personal_finance_app.R
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({

            val sharedPref = getSharedPreferences("auth_prefs", MODE_PRIVATE)
            val lastLoginTime = sharedPref.getLong("last_login_time", 0)

            val currentTime = System.currentTimeMillis()
            val sevenDays = 7 * 24 * 60 * 60 * 1000L

            if (lastLoginTime != 0L && (currentTime - lastLoginTime) < sevenDays) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                sharedPref.edit { clear() }
                startActivity(Intent(this, AuthPageActivity::class.java))
            }
            finish()
        }, 2000) // 2 seconds
    }
}