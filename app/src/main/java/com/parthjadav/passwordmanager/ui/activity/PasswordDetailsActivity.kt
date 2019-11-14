package com.parthjadav.passwordmanager.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.ui.fragment.CheckPasswordFragment
import com.parthjadav.passwordmanager.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_password_details.*

class PasswordDetailsActivity : AppCompatActivity() {

    private val ADD_TASK_REQUEST = 1
    var passView: String = ""

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_details)

        preferenceManager = PreferenceManager(this)

        preferenceManager.setKeyValueBoolean("viewPassword", false)

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
            /*if (!preferenceManager.getKeyValueBoolean("isPinSet")) {
                tvPinNotSet.visibility = View.VISIBLE
            } else {
                tvPinNotSet.visibility = View.GONE
                preferenceManager.setKeyValueBoolean("viewPassword", true)
                val intent = Intent(this, SetPinActivity::class.java)
                startActivityForResult(intent, ADD_TASK_REQUEST)
            }*/

            val checkPasswordFragment = CheckPasswordFragment()
            checkPasswordFragment.CheckPasswordFragment(true,object :CheckPasswordFragment.OnPasswordCheck{
                override fun onCancel(isCancel: Boolean) {
                    checkPasswordFragment.dismiss()
                }

                override fun onClick(isPasswordTrue: Boolean) {
                    if (isPasswordTrue){
                        btnViewPassword.visibility = View.GONE
                        btnHidePassword.visibility = View.VISIBLE
                        tvPassPassword.inputType = InputType.TYPE_CLASS_TEXT
                        checkPasswordFragment.dismiss()
                    }else{
                        val myToast =
                            Toast.makeText(
                                applicationContext,
                                "Invalid Password",
                                Toast.LENGTH_SHORT
                            )
                        myToast.show()
                    }
                }

            })
            checkPasswordFragment.isCancelable = false
            checkPasswordFragment.showNow(supportFragmentManager,"Check Password")
        }

        btnHidePassword.setOnClickListener {
            tvPassPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            btnViewPassword.visibility = View.VISIBLE
            btnHidePassword.visibility = View.GONE
        }

        tvOpenSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_TASK_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e("**** No Data", "1")
                passView = data?.getStringExtra("view").toString()

                if (passView.equals("1")) {
                    preferenceManager.setKeyValueBoolean("viewPassword", false)
                    btnViewPassword.visibility = View.GONE
                    btnHidePassword.visibility = View.VISIBLE
                    tvPassPassword.inputType = InputType.TYPE_CLASS_TEXT
                }
            }
        } else {
            Log.e("**** No Data", "0")
        }
    }

    override fun onResume() {
        super.onResume()
        if (preferenceManager.getKeyValueBoolean("isPinSet")) {
            tvPinNotSet.visibility = View.GONE
        }
    }
}
