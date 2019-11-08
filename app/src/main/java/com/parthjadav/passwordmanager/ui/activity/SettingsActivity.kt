package com.parthjadav.passwordmanager.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.parthjadav.passwordmanager.R.layout.activity_settings)

        switchPassCode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                val mainIntent = Intent(this, SetPinActivity::class.java)
                startActivity(mainIntent)
            }
        }
    }
}
