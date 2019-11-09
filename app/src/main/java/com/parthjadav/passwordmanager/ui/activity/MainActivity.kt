package com.parthjadav.passwordmanager.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.ui.fragment.SettingsFragment
import com.parthjadav.passwordmanager.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_welcome.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PreferenceManager(this).setKeyValueBoolean("isLock",false)

        imgBtnSettings.setOnClickListener {
            val mainIntent = Intent(this, SettingsActivity::class.java)
            startActivity(mainIntent)
        }

        btnAddPassword.setOnClickListener {
            val mainIntent = Intent(this, AddPasswordActivity::class.java)
            startActivity(mainIntent)
        }
    }
}
