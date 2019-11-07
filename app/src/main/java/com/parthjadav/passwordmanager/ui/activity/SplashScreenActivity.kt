package com.parthjadav.passwordmanager.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.parthjadav.passwordmanager.R
import android.os.Handler
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.parthjadav.passwordmanager.utils.AppUtils

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this, WelcomeActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, 3000)
    }
}
