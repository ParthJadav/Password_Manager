package com.parthjadav.passwordmanager.ui.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.an.customfontview.CustomTextView
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.db.AppDatabase
import com.parthjadav.passwordmanager.ui.fragment.CheckPasswordFragment
import com.parthjadav.passwordmanager.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class SettingsActivity : AppCompatActivity() {

    var isPinsSet: Boolean = false
    private var db: AppDatabase? = null
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
                    val checkPasswordFragment = CheckPasswordFragment()
                    checkPasswordFragment.isCancelable = false
                    checkPasswordFragment.CheckPasswordFragment(true,
                        object : CheckPasswordFragment.OnPasswordCheck {
                            override fun onCancel(isCancel: Boolean) {
                                checkPasswordFragment.dismiss()
                                switchPassCode.isChecked = true
                            }

                            override fun onClick(isPasswordTrue: Boolean) {
                                if (isPasswordTrue) {
                                    checkPasswordFragment.dismiss()
                                    val mainIntent = Intent(this@SettingsActivity, SetPinActivity::class.java)
                                    startActivity(mainIntent)
                                } else {
                                    val myToast =
                                        Toast.makeText(
                                            applicationContext,
                                            "Invalid Password",
                                            Toast.LENGTH_SHORT
                                        )
                                    myToast.show()
                                }
                            }
                        })
                    checkPasswordFragment.showNow(supportFragmentManager, "Check Password")
                }
            } else {
                if (isPinsSet) {
                    val checkPasswordFragment = CheckPasswordFragment()
                    checkPasswordFragment.isCancelable = false
                    checkPasswordFragment.CheckPasswordFragment(true,
                        object : CheckPasswordFragment.OnPasswordCheck {
                            override fun onCancel(isCancel: Boolean) {
                                checkPasswordFragment.dismiss()
                                switchPassCode.isChecked = true
                            }

                            override fun onClick(isPasswordTrue: Boolean) {
                                if (isPasswordTrue) {
                                    checkPasswordFragment.dismiss()
                                    val mainIntent = Intent(this@SettingsActivity, SetPinActivity::class.java)
                                    startActivity(mainIntent)
                                } else {
                                    val myToast =
                                        Toast.makeText(
                                            applicationContext,
                                            "Invalid Password",
                                            Toast.LENGTH_SHORT
                                        )
                                    myToast.show()
                                }
                            }
                        })
                    checkPasswordFragment.showNow(supportFragmentManager, "Check Password")
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

        menuBackup.setOnClickListener {
            db = AppDatabase.getAppDataBase(this@SettingsActivity)
            db?.close()
            backup()
        }
    }

    private fun backup() {
        try {
            val sd = Environment.getExternalStorageDirectory()
            val data = Environment.getDataDirectory()
            if (sd.canWrite()) {
                //val currentDbPath = "//data//com.parthjadav.passwordmanager//databases//password_manager"
                val currentDbPath =
                    "//data//com.parthjadav.passwordmanager//databases//password_manager"
                val backupPath = "manager_backup.db"
                val currentDb = File(data, currentDbPath)
                val backupDb = File(sd, backupPath)
                val src = FileInputStream(currentDb).channel
                val dst = FileOutputStream(backupDb).channel
                dst.transferFrom(src, 0, src.size())
                src.close()
                dst.close()
                Toast.makeText(this@SettingsActivity, "Backup successfully", Toast.LENGTH_LONG)
                    .show()
            }

        } catch (e: Exception) {
            Log.e("Error", e.message)
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
