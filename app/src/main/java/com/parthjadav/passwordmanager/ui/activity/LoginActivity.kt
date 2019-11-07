package com.parthjadav.passwordmanager.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.parthjadav.passwordmanager.R.layout.activity_login)


        btnLogin.setOnClickListener {
        }
        btnBack.setOnClickListener {
            finish()
        }

        btnNeedHelp.setOnClickListener {
            val mainIntent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(mainIntent)
        }
        btnRegister.setOnClickListener {
            val mainIntent = Intent(this, RegisterActivity::class.java)
            startActivity(mainIntent)
        }
    }
}
