package com.parthjadav.passwordmanager.ui.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.an.customfontview.CustomTextView
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PreferenceManager(this).setKeyValueBoolean("isLock", false)

        imgBtnSettings.setOnClickListener {
            val mainIntent = Intent(this, SettingsActivity::class.java)
            startActivity(mainIntent)
        }

        btnAddPassword.setOnClickListener {
            val mainIntent = Intent(this, AddPasswordActivity::class.java)
            startActivity(mainIntent)
        }
    }

    override fun onBackPressed() {
        dialogExit(this)
    }

    @SuppressLint("SetTextI18n")
    private fun dialogExit(mContext: Context) {
        try {
            val inflater =
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val alertLayout = inflater.inflate(R.layout.dialog_logout, null)

            val alert = AlertDialog.Builder(mContext)
            alert.setView(alertLayout)
            alert.setCancelable(false)

            val title = alertLayout.findViewById<CustomTextView>(R.id.tvTitle)
            val message = alertLayout.findViewById<CustomTextView>(R.id.tvMessage)
            val btnExit = alertLayout.findViewById<CustomTextView>(R.id.tvPositive)
            val btnCancel = alertLayout.findViewById<CustomTextView>(R.id.tvNegative)

            title.text = "Exit"
            message.text = "Are you sure you want to exit?"
            btnExit.text = "Exit"

            val dialog: AlertDialog
            dialog = alert.create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            btnExit.setOnClickListener {
                dialog.dismiss()
                super.onBackPressed()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        } catch (e: Exception) {

        }

    }
}
