package com.parthjadav.passwordmanager.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.dao.UserDao
import com.parthjadav.passwordmanager.db.AppDatabase
import com.parthjadav.passwordmanager.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {

    lateinit var oldPassword: String
    lateinit var newPassword: String
    lateinit var cnfPassword: String
    lateinit var preferenceManager: PreferenceManager

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null

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
                val myToast =
                    Toast.makeText(
                        applicationContext,
                        "Password change",
                        Toast.LENGTH_SHORT
                    )
                myToast.show()
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}
