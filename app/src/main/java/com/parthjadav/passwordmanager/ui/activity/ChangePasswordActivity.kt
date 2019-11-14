package com.parthjadav.passwordmanager.ui.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.an.customfontview.CustomTextView
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.dao.UserDao
import com.parthjadav.passwordmanager.db.AppDatabase
import com.parthjadav.passwordmanager.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_password.*
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {

    lateinit var oldPassword: String
    lateinit var newPassword: String
    lateinit var cnfPassword: String
    lateinit var preferenceManager: PreferenceManager

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null

    var pwdOld :Int = 0
    var pwdNew :Int = 0
    var pwdConfirm :Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        preferenceManager = PreferenceManager(this)

        btnChangePassBack.setOnClickListener {
            finish()
        }

        btnChangePassword.setOnClickListener {
            oldPassword = edtOldPassword.text.toString()
            newPassword = edtNewPassword.text.toString()
            cnfPassword = edtConfirmPassword.text.toString()

            if (oldPassword.isEmpty()) {
                edtOldPassword.error = "Please enter old password"
                edtOldPassword.requestFocus()
            } else if (newPassword.isEmpty()) {
                edtNewPassword.error = "Please enter new password"
                edtNewPassword.requestFocus()
            } else if (cnfPassword.isEmpty()) {
                edtConfirmPassword.error = "Please enter confirm password"
                edtConfirmPassword.requestFocus()
            } else if (!newPassword.equals(cnfPassword)) {
                edtConfirmPassword.error = "Confirm password don't match"
                edtConfirmPassword.requestFocus()
            } else if (!oldPassword.equals(preferenceManager.getKeyValueString("old_password"))) {
                edtOldPassword.error = "The old password don't match"
                edtOldPassword.requestFocus()
            }else {
                changePassword(newPassword)
            }
        }

        tvOldPasswordVisibility.setOnClickListener {
            if (pwdOld == 0) {
                pwdOld = 1;
                tvOldPasswordVisibility.text = "Hide"
                edtOldPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
                edtOldPassword.setSelection(edtOldPassword.getText().length);
            } else if (pwdOld == 1) {
                pwdOld = 0;
                tvOldPasswordVisibility.text = "Show"
                edtOldPassword.transformationMethod = PasswordTransformationMethod.getInstance();
                edtOldPassword.setSelection(edtOldPassword.getText().length);
            }
        }

        tvNewPasswordVisibility.setOnClickListener {
            if (pwdNew == 0) {
                pwdNew = 1;
                tvNewPasswordVisibility.text = "Hide"
                edtNewPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
                edtNewPassword.setSelection(edtNewPassword.getText().length);
            } else if (pwdNew == 1) {
                pwdNew = 0;
                tvNewPasswordVisibility.text = "Show"
                edtNewPassword.transformationMethod = PasswordTransformationMethod.getInstance();
                edtNewPassword.setSelection(edtNewPassword.getText().length);
            }
        }
        tvCNFPasswordVisibility.setOnClickListener {
            if (pwdConfirm == 0) {
                pwdConfirm = 1;
                tvCNFPasswordVisibility.text = "Hide"
                edtConfirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
                edtConfirmPassword.setSelection(edtConfirmPassword.getText().length);
            } else if (pwdConfirm == 1) {
                pwdConfirm = 0;
                tvCNFPasswordVisibility.text = "Show"
                edtConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance();
                edtConfirmPassword.setSelection(edtConfirmPassword.getText().length);
            }
        }
    }

    private fun changePassword(password: String) {

        Observable.fromCallable {
            db = AppDatabase.getAppDataBase(context = this)
            userDao = db?.userDao()

            with(userDao) {
                this?.changePassword(password, preferenceManager.registeredUserId)
            }
        }.doOnNext { list ->
            runOnUiThread {
                /*val myToast =
                    Toast.makeText(
                        applicationContext,
                        "Password change",
                        Toast.LENGTH_SHORT
                    )
                myToast.show()*/
                Handler().postDelayed({
                    showLogout(this)
                }, 300)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
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

            title.text = "Password Changed"
            message.text = "Please logout and login again?"

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
