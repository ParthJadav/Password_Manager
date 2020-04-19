package com.parthjadav.passwordmanager.ui.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.dao.PasswordDao
import com.parthjadav.passwordmanager.db.AppDatabase
import com.parthjadav.passwordmanager.utils.PreferenceManager
import com.parthjadav.passwordmanager.utils.Tools
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_password_details.imgPassList
import kotlinx.android.synthetic.main.activity_update_password.*

class UpdatePasswordActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager
    var pwd: Int = 0

    private var db: AppDatabase? = null
    private var passwordDao: PasswordDao? = null
    private lateinit var id: String;
    private lateinit var accountName: String;
    private lateinit var title: String;
    private lateinit var userId: String;
    private lateinit var password: String;
    private lateinit var accountImage: ByteArray;
    private lateinit var bitmap: Bitmap;

    private lateinit var newPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        preferenceManager = PreferenceManager(this)

        id = intent.getStringExtra("id")!!
        accountName = intent.getStringExtra("accountName")!!
        title = intent.getStringExtra("title")!!
        userId = intent.getStringExtra("userId")!!
        password = intent.getStringExtra("password")!!

        accountImage = intent.getByteArrayExtra("accountImage")!!

        bitmap = BitmapFactory.decodeByteArray(
            accountImage, 0,
            accountImage.size
        )

        imgPassList.setImageBitmap(bitmap)
        edtPasswordTitle.text = Editable.Factory.getInstance().newEditable(title)
        edtPasswordUserId.text = Editable.Factory.getInstance().newEditable(userId)
        edtPassPassword.text = Editable.Factory.getInstance().newEditable(password)

        btnUpdatePassBack.setOnClickListener {
            supportFinishAfterTransition()
            super.onBackPressed()
        }

        btnUpdatePassword.setOnClickListener {
            newPassword = edtPassPassword.text.toString();

            if (newPassword.isEmpty()) {
                edtPassPassword.error = "Please enter password"
                edtPassPassword.requestFocus()
            } else {
                changePassword(newPassword)
            }
        }

        tvPasswordVisibility.setOnClickListener {
            if (pwd == 0) {
                pwd = 1;
                tvPasswordVisibility.text = "Hide"
                edtPassPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance();
                edtPassPassword.setSelection(edtPassPassword.getText().length);
            } else if (pwd == 1) {
                pwd = 0;
                tvPasswordVisibility.text = "Show"
                edtPassPassword.transformationMethod = PasswordTransformationMethod.getInstance();
                edtPassPassword.setSelection(edtPassPassword.getText().length);
            }
        }
    }

    private fun changePassword(password: String) {

        Observable.fromCallable {
            db = AppDatabase.getAppDataBase(context = this)
            passwordDao = db?.passwordDao()

            with(passwordDao) {
                this?.changePassword(password, id)
            }
        }.doOnNext { list ->
            runOnUiThread {
                preferenceManager.setKeyValueBoolean("isPasswordChange", true)
                Tools.makeToast(
                    this,
                    "Password successfully changed.",
                    R.drawable.splash_one,
                    Toast.LENGTH_SHORT
                )
                Handler().postDelayed({
                }, 300)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}