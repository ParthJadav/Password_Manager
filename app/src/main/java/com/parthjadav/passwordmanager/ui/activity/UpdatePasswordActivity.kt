package com.parthjadav.passwordmanager.ui.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_add_password.*
import kotlinx.android.synthetic.main.activity_password_details.*
import kotlinx.android.synthetic.main.activity_password_details.imgPassList
import kotlinx.android.synthetic.main.activity_password_details.tvPassDetailsTitle
import kotlinx.android.synthetic.main.activity_update_password.*
import kotlinx.android.synthetic.main.activity_update_password.edtPassPassword
import kotlinx.android.synthetic.main.activity_update_password.edtPasswordTitle
import kotlinx.android.synthetic.main.activity_update_password.edtPasswordUserId
import kotlinx.android.synthetic.main.activity_update_password.tvPasswordVisibility

class UpdatePasswordActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager
    var pwd :Int = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        preferenceManager = PreferenceManager(this)

        val id: String = intent.getStringExtra("id")!!
        val accountName: String = intent.getStringExtra("accountName")!!
        val title: String = intent.getStringExtra("title")!!
        val userId: String = intent.getStringExtra("userId")!!
        val password: String = intent.getStringExtra("password")!!

        val accountImage: ByteArray = intent.getByteArrayExtra("accountImage")!!

        val bitmap = BitmapFactory.decodeByteArray(
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

        tvPasswordVisibility.setOnClickListener {
            if (pwd == 0) {
                pwd = 1;
                tvPasswordVisibility.text = "Hide"
                edtPassPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
                edtPassPassword.setSelection(edtPassPassword.getText().length);
            } else if (pwd == 1) {
                pwd = 0;
                tvPasswordVisibility.text = "Show"
                edtPassPassword.transformationMethod = PasswordTransformationMethod.getInstance();
                edtPassPassword.setSelection(edtPassPassword.getText().length);
            }
        }
    }
}