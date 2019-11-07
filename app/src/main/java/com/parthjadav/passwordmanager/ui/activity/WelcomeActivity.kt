package com.parthjadav.passwordmanager.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.utils.AppUtils
import kotlinx.android.synthetic.main.activity_login.*
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
