package com.parthjadav.passwordmanager.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.R
import kotlinx.android.synthetic.main.activity_add_password.*

class AddPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_password)

        btnAddPassBack.setOnClickListener { finish() }
    }
}
