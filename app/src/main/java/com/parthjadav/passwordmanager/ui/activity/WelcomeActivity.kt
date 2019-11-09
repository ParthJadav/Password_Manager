package com.parthjadav.passwordmanager.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.R
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        btnWelcomeLogin.setOnClickListener {
            val mainIntent = Intent(this, LoginActivity::class.java)
            startActivity(mainIntent)
        }

        btnWelcomeRegister.setOnClickListener {
            val mainIntent = Intent(this, RegisterActivity::class.java)
            startActivity(mainIntent)
        }
    }
}
