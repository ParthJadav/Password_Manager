package com.parthjadav.passwordmanager.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.ui.fragment.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_welcome.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgBtnSettings.setOnClickListener {
            /*supportFragmentManager?.beginTransaction()?.add(SettingsFragment(), "test")
                ?.commit()*/
        }

        btnAddPassword.setOnClickListener {
            val mainIntent = Intent(this, AddPasswordActivity::class.java)
            startActivity(mainIntent)
        }
    }
}
