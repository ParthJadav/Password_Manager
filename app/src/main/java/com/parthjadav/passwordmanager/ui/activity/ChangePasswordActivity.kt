package com.parthjadav.passwordmanager.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.parthjadav.passwordmanager.R
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        btnChangePassBack.setOnClickListener {
            finish()
        }
    }
}
