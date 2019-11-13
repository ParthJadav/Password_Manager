package com.parthjadav.passwordmanager.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.an.customfontview.CustomTextView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.db.AppDatabase
import com.parthjadav.passwordmanager.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import android.widget.Toast
import android.util.Log


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

        menuBackup.setOnClickListener {
            /*Dexter.withActivity(this)
                .withPermissions(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.RECORD_AUDIO
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted()) {

                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest>,
                        token: PermissionToken
                    ) {
                        token.continuePermissionRequest()
                    }
                }).check()*/
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
                val currentDbPath = "//data//com.parthjadav.passwordmanager//databases//password_manager"
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
