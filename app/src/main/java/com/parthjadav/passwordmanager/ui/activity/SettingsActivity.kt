package com.parthjadav.passwordmanager.ui.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.an.customfontview.CustomTextView
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {

    var isPinsSet: Boolean = false

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        preferenceManager = PreferenceManager(this)

        isPinsSet = preferenceManager.getKeyValueBoolean("isPinSet")
        preferenceManager.setKeyValueBoolean("viewPassword", false)

        switchPassCode.isChecked = isPinsSet

        switchPassCode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (!isPinsSet) {
                    val mainIntent = Intent(this, SetPinActivity::class.java)
                    startActivity(mainIntent)
                }
            } else {
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

        menuLogout.setOnClickListener {
            showLogout(this)
        }
    }

    override fun onResume() {
        super.onResume()
        isPinsSet = preferenceManager.getKeyValueBoolean("isPinSet")
        switchPassCode.isChecked = isPinsSet
    }

    @SuppressLint("SetTextI18n")
    private fun showLogout(mContext: Context) {
        try {
            val inflater =
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val alertLayout = inflater.inflate(R.layout.dialog_logout, null)

            val alert = AlertDialog.Builder(mContext)
            alert.setView(alertLayout)
            alert.setCancelable(false)

            val title = alertLayout.findViewById<CustomTextView>(R.id.tvTitle)
            val message = alertLayout.findViewById<CustomTextView>(R.id.tvMessage)
            val btnLogout = alertLayout.findViewById<CustomTextView>(R.id.tvPositive)
            val btnCancel = alertLayout.findViewById<CustomTextView>(R.id.tvNegative)

            title.text = "Logout"
            message.text = "Are you sure you want to logout?"

            val dialog: AlertDialog
            dialog = alert.create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            btnLogout.setOnClickListener {
                dialog.dismiss()
                preferenceManager.clearPreferences()
                val mainIntent = Intent(this, WelcomeActivity::class.java)
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(mainIntent)
                finish()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        } catch (e: Exception) {

        }

    }

}
