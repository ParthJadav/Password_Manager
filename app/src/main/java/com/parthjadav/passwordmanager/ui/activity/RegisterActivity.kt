package com.parthjadav.passwordmanager.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.dao.UserDao
import com.parthjadav.passwordmanager.db.AppDatabase
import com.parthjadav.passwordmanager.model.User
import com.parthjadav.passwordmanager.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null

    private var usernameExists: List<User>? = null
    private var mobileNo: String = ""
    var lastId: Long? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {
            isMobileNoExists()
            //registerNewUser()
        }
        btnRegisterBack.setOnClickListener { finish() }


    }

    private fun registerNewUser() {

        Observable.fromCallable {
            db = AppDatabase.getAppDataBase(context = this)
            userDao = db?.userDao()

            val fname = edtFirstname.text.toString()
            val lname = edtLastname.text.toString()
            val mobileNumber = edtMobile.text.toString()
            val password = edtPassword.text.toString()
            val cnfPassword = edtCnfPassword.text.toString()

            val user = User()
            user.setFirstName(fname)
            user.setLastName(lname)
            user.setMobileNumber(mobileNumber)
            user.setPassword(password)

            if (password.equals(cnfPassword)) {
                with(userDao) {
                    lastId = this?.insertUser(user)
                }
            } else {
                runOnUiThread {
                    edtCnfPassword.error = "Password don't match."
                    edtCnfPassword.requestFocus()
                }
            }
        }.doOnNext { list ->
            runOnUiThread {
                if (lastId!! >= 0) {
                    PreferenceManager(this@RegisterActivity).setKeyValueBoolean("login",true)
                    PreferenceManager(this@RegisterActivity).registeredUserId =
                        lastId.toString()
                    val myToast =
                        Toast.makeText(
                            applicationContext,
                            "Account Created.",
                            Toast.LENGTH_SHORT
                        )
                    myToast.show()

                    Handler().postDelayed({
                        val mainIntent = Intent(this, MainActivity::class.java)
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(mainIntent)
                        finish()
                    }, 300);
                } else {
                    val myToast =
                        Toast.makeText(
                            applicationContext,
                            "Something went wrong!!",
                            Toast.LENGTH_SHORT
                        )
                    myToast.show()
                }
            }
            //isUserNameExists()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun isMobileNoExists() {

        Observable.fromCallable {
            db = AppDatabase.getAppDataBase(context = this)
            userDao = db?.userDao()

            mobileNo = edtMobile.text.toString()

            with(userDao) {
                usernameExists = this?.isMobileNumberExists(mobileNo)
            }
        }.doOnNext { list ->
            if (usernameExists?.size!! > 0) {
                if (mobileNo.equals(usernameExists?.get(0)?.getMobileNumber())) {
                    runOnUiThread {
                        edtMobile.error = "Mobile already exists."
                        edtMobile.requestFocus()
                    }
                } else {
                    registerNewUser()
                }
            } else {
                registerNewUser()
            }

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}
