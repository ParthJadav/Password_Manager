package com.parthjadav.passwordmanager.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.R
import kotlinx.android.synthetic.main.activity_add_password.*


class AddPasswordActivity : AppCompatActivity() {

    private val ADD_TASK_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_password)

        btnAddPassBack.setOnClickListener { finish() }
        tvSelectAccount.setOnClickListener {
            val intent = Intent(this, SelectAccountActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_TASK_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e("**** No Data", "1")
                cardViewSelectedAccount.visibility = View.VISIBLE


                tvAccountName.text = data?.getStringExtra("accountName")
                val image: Int? = data?.getIntExtra("accountImage", 0)
                imgAccount.setImageResource(image!!)
            }
        } else {
            Log.e("**** No Data", "0")
        }
    }
}
