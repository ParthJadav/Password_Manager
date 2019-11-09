package com.parthjadav.passwordmanager.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.parthjadav.passwordmanager.R
import android.os.Handler
import com.parthjadav.passwordmanager.utils.PreferenceManager

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            if (PreferenceManager(this).getKeyValueBoolean("isPinSet")){
                PreferenceManager(this).setKeyValueBoolean("isLock",true)
                val mainIntent = Intent(this, SetPinActivity::class.java)
                startActivity(mainIntent)
            }else {
                val mainIntent = Intent(this, WelcomeActivity::class.java)
                startActivity(mainIntent)
            }
        }, 3000)
    }
}
