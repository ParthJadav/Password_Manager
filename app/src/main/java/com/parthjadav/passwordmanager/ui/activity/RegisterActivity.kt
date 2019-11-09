package com.parthjadav.passwordmanager.ui.activity

import android.os.Bundle
import android.util.Log
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

    var lastId: Long? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {
            registerNewUser()
        }
        btnRegisterBack.setOnClickListener { finish() }


    }

    private fun registerNewUser() {

        Observable.fromCallable {
            db = AppDatabase.getAppDataBase(context = this)
            userDao = db?.userDao()

            var fname = edtFirstname.text.toString()
            var lname = edtLastname.text.toString()
            var username = edtUsername.text.toString()
            var password = edtPassword.text.toString()

            val user = User()
            user.setFirstName(fname)
            user.setLastName(lname)
            user.setUsername(username)
            user.setPassword(password)

            with(userDao) {
                lastId = this?.insertUser(user)
            }
        }.doOnNext { list ->
            Log.e("**** Last Id - ", lastId.toString())
            PreferenceManager(this@RegisterActivity).setLoginSession()
            PreferenceManager(this@RegisterActivity).registeredUserId = lastId.toString()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}
