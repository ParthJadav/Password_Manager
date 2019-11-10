package com.parthjadav.passwordmanager.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_password_details.*

class PasswordDetailsActivity : AppCompatActivity() {

    private val ADD_TASK_REQUEST = 1
    var passView: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_details)

        PreferenceManager(this).setKeyValueBoolean("viewPassword", false)

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
        tvPassDetailsTitle.text = title
        tvPasswordUserId.text = userId
        tvPassPassword.text = password


        btnPassDetailsBack.setOnClickListener {
            supportFinishAfterTransition()
            super.onBackPressed()
        }

        btnViewPassword.setOnClickListener {
            PreferenceManager(this).setKeyValueBoolean("viewPassword", true)
            val intent = Intent(this, SetPinActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
        }

        btnHidePassword.setOnClickListener {
            tvPassPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            btnViewPassword.visibility = View.VISIBLE
            btnHidePassword.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_TASK_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e("**** No Data", "1")
                passView = data?.getStringExtra("view").toString()

                if (passView.equals("1")) {
                    btnViewPassword.visibility = View.GONE
                    btnHidePassword.visibility = View.VISIBLE
                    tvPassPassword.inputType = InputType.TYPE_CLASS_TEXT
                }
            }
        } else {
            Log.e("**** No Data", "0")
        }
    }
}
