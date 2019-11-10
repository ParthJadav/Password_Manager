package com.parthjadav.passwordmanager.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.utils.PreferenceManager

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            if (PreferenceManager(this@SplashScreenActivity).getKeyValueBoolean("isPinSet")) {
                PreferenceManager(this@SplashScreenActivity).setKeyValueBoolean("isLock", true)
                val mainIntent = Intent(this, SetPinActivity::class.java)
                startActivity(mainIntent)
                finish()
            } else {
                if (PreferenceManager(this@SplashScreenActivity).getKeyValueBoolean("login")) {
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                } else {
                    val mainIntent = Intent(this, WelcomeActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }
            }
        }, 3000)
    }
}
