package com.parthjadav.passwordmanager.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.dao.UserDao
import com.parthjadav.passwordmanager.db.AppDatabase
import com.parthjadav.passwordmanager.model.User
import com.parthjadav.passwordmanager.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var mobileNo: String = ""
    private var password: String = ""
    private var userDetails: List<User>? = null

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.parthjadav.passwordmanager.R.layout.activity_login)

        preferenceManager = PreferenceManager(this)

        btnLogin.setOnClickListener {
            login()
        }
        btnBack.setOnClickListener {
            finish()
        }

        btnNeedHelp.setOnClickListener {
            val mainIntent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(mainIntent)
        }
        btnRegister.setOnClickListener {
            val mainIntent = Intent(this, RegisterActivity::class.java)
            startActivity(mainIntent)
        }
    }

    private fun login() {

        Observable.fromCallable {
            db = AppDatabase.getAppDataBase(context = this)
            userDao = db?.userDao()

            mobileNo = edtMobileNo.text.toString()
            password = edtLoginPassword.text.toString()

            with(userDao) {
                userDetails = this?.login(mobileNo,password)
            }
        }.doOnNext { list ->
            runOnUiThread {
                if (userDetails?.size!! > 0) {
                    preferenceManager.setKeyValueBoolean("login",true)
                    preferenceManager.registeredUserId = userDetails?.get(0)?.getId().toString()
                    val myToast = Toast.makeText(
                        applicationContext,
                        "Successfully logged in",
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
                    val myToast = Toast.makeText(
                        applicationContext,
                        "Invalid mobile number or password",
                        Toast.LENGTH_SHORT
                    )
                    myToast.show()
                }
            }

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}
