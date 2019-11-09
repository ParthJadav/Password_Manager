package com.parthjadav.passwordmanager.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {

    var isPinsSet: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.parthjadav.passwordmanager.R.layout.activity_settings)

        isPinsSet = PreferenceManager(this).getKeyValueBoolean("isPinSet")

        switchPassCode.isChecked = isPinsSet

        switchPassCode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (!isPinsSet) {
                    val mainIntent = Intent(this, SetPinActivity::class.java)
                    startActivity(mainIntent)
                }
            }else{
                if (isPinsSet) {
                    val mainIntent = Intent(this, SetPinActivity::class.java)
                    startActivity(mainIntent)
                }
            }
        }

        menuChangePassword.setOnClickListener {
            val mainIntent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(mainIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        isPinsSet = PreferenceManager(this).getKeyValueBoolean("isPinSet")
        switchPassCode.isChecked = isPinsSet
    }
}
