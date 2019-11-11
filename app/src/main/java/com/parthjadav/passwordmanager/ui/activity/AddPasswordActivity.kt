package com.parthjadav.passwordmanager.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.dao.PasswordDao
import com.parthjadav.passwordmanager.db.AppDatabase
import com.parthjadav.passwordmanager.model.Password
import com.parthjadav.passwordmanager.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_password.*
import java.io.ByteArrayOutputStream


class AddPasswordActivity : AppCompatActivity() {

    private val ADD_TASK_REQUEST = 1
    var image: Int? = 0
    var lastId: Long? = -1
    var accountName: String = ""
    private var db: AppDatabase? = null
    private var passwordDao: PasswordDao? = null

    private var title: String = ""
    private var userId: String = ""
    private var pass: String = ""

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_password)

        preferenceManager = PreferenceManager(this)

        btnAddPassBack.setOnClickListener { finish() }
        tvSelectAccount.setOnClickListener {
            val intent = Intent(this, SelectAccountActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }
        btnAddPassword.setOnClickListener {

            title = accountName
            userId = edtPasswordUserId.text.toString()
            pass = edtPassPassword.text.toString()

            if (userId.isEmpty()) {
                edtPasswordUserId.error = "Please enter user id"
                edtPasswordUserId.requestFocus()
            } else if (pass.isEmpty()) {
                edtPassPassword.error = "Please enter password"
                edtPassPassword.requestFocus()
            } else if (accountName.isEmpty()) {
                val myToast =
                    Toast.makeText(
                        applicationContext,
                        "Please select account.",
                        Toast.LENGTH_SHORT
                    )
                myToast.show()
            } else {
                savePassword()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_TASK_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e("**** No Data", "1")
                cardViewSelectedAccount.visibility = View.VISIBLE
                accountName = data?.getStringExtra("accountName").toString()
                tvAccountName.text = accountName
                image = data?.getIntExtra("accountImage", 0)
                imgAccount.setImageResource(image!!)
            }
        } else {
            Log.e("**** No Data", "0")
        }
    }

    private fun drawableToByteArray(image: Int): ByteArray {
        val drawable = ContextCompat.getDrawable(this@AddPasswordActivity, image)
        val bitmap = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    private fun savePassword() {

        Observable.fromCallable {
            db = AppDatabase.getAppDataBase(context = this)
            passwordDao = db?.passwordDao()

            //val title = edtPasswordTitle.text.toString()


            val password = Password()
            password.setAccountName(accountName)
            password.setAccountImage(drawableToByteArray(image!!))
            password.setTitle(title)
            password.setUserId(userId)
            password.setPassword(pass)

            with(passwordDao) {
                lastId = this?.insertPassword(password)
            }
        }.doOnNext { list ->
            runOnUiThread {
                if (lastId!! >= 0) {
                    val myToast =
                        Toast.makeText(
                            applicationContext,
                            "Password added",
                            Toast.LENGTH_SHORT
                        )
                    myToast.show()
                    Handler().postDelayed({
                        finish()
                    }, 300)
                } else {
                    val myToast =
                        Toast.makeText(
                            applicationContext,
                            "Failed to add password!!",
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
