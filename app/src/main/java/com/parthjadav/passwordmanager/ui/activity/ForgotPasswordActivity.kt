package com.parthjadav.passwordmanager.ui.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.R
import kotlinx.android.synthetic.main.activity_forgot_password.*


class ForgotPasswordActivity : AppCompatActivity() {

    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btnForgotBack.setOnClickListener {
            finish()
        }

        tv1.setOnClickListener {
            edtMobileNumber.setText(edtMobileNumber.text.toString() + "1")
        }

        tv2.setOnClickListener {
            edtMobileNumber.setText(edtMobileNumber.text.toString() + "2")
        }

        tv3.setOnClickListener {
            edtMobileNumber.setText(edtMobileNumber.text.toString() + "3")
        }

        tv4.setOnClickListener {
            edtMobileNumber.setText(edtMobileNumber.text.toString() + "4")
        }

        tv5.setOnClickListener {
            edtMobileNumber.setText(edtMobileNumber.text.toString() + "5")
        }

        tv6.setOnClickListener {
            edtMobileNumber.setText(edtMobileNumber.text.toString() + "6")
        }

        tv7.setOnClickListener {
            edtMobileNumber.setText(edtMobileNumber.text.toString() + "7")
        }

        tv8.setOnClickListener {
            edtMobileNumber.setText(edtMobileNumber.text.toString() + "8")
        }

        tv9.setOnClickListener {
            edtMobileNumber.setText(edtMobileNumber.text.toString() + "9")
        }

        tv0.setOnClickListener {
            edtMobileNumber.setText(edtMobileNumber.text.toString() + "0")
        }

        btnNumberBack.setOnClickListener {
            var number: String = edtMobileNumber.text.toString()
            if (number.length >= 1) {
                number = number.substring(0, number.length - 1)
                edtMobileNumber.text = number
            }
            if (number.length < 1) {
                edtMobileNumber.text = ""
            }
        }

        btnNumberBack.setOnLongClickListener {
            handler.postDelayed(object : Runnable {
                override fun run() {
                    var number: String = edtMobileNumber.text.toString()
                    if (number.length >= 1) {
                        number = number.substring(0, number.length - 1)
                        edtMobileNumber.text = number
                        handler.postDelayed(this, 100)
                    }
                    if (number.length < 1) {
                        edtMobileNumber.text = ""
                    }
                    //Do something after 20 seconds
                }
            }, 100)
            return@setOnLongClickListener true
        }
    }
}
