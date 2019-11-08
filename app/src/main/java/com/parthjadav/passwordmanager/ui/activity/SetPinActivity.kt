package com.parthjadav.passwordmanager.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.R
import kotlinx.android.synthetic.main.activity_set_pin.*

class SetPinActivity : AppCompatActivity() {

    val handler = Handler()
    var pin: String = ""
    var confirmPin: String = ""
    var isCreate: Boolean? = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_pin)

        btnSetPinBack.setOnClickListener {
            finish()
        }

        circleField.isEnabled = false

        tv1.setOnClickListener {
            circleField.setText(circleField.text.toString() + "1")
            confirmText()
        }

        tv2.setOnClickListener {
            circleField.setText(circleField.text.toString() + "2")
            confirmText()
        }

        tv3.setOnClickListener {
            circleField.setText(circleField.text.toString() + "3")
            confirmText()
        }

        tv4.setOnClickListener {
            circleField.setText(circleField.text.toString() + "4")
            confirmText()
        }

        tv5.setOnClickListener {
            circleField.setText(circleField.text.toString() + "5")
            confirmText()
        }

        tv6.setOnClickListener {
            circleField.setText(circleField.text.toString() + "6")
            confirmText()
        }

        tv7.setOnClickListener {
            circleField.setText(circleField.text.toString() + "7")
            confirmText()
        }

        tv8.setOnClickListener {
            circleField.setText(circleField.text.toString() + "8")
            confirmText()
        }

        tv9.setOnClickListener {
            circleField.setText(circleField.text.toString() + "9")
            confirmText()
        }

        tv0.setOnClickListener {
            circleField.setText(circleField.text.toString() + "0")
            confirmText()
        }

        btnNumberBack.setOnClickListener {
            var number: String = circleField.text.toString()
            if (number.length >= 1) {
                number = number.substring(0, number.length - 1)
                circleField.setText(number)
            }
            if (number.length < 1) {
                circleField.setText("")
            }
        }

        btnNumberBack.setOnLongClickListener {
            handler.postDelayed(object : Runnable {
                override fun run() {
                    var number: String = circleField.text.toString()
                    if (number.length >= 1) {
                        number = number.substring(0, number.length - 1)
                        circleField.setText(number)
                        handler.postDelayed(this, 100)
                    }
                    if (number.length < 1) {
                        circleField.setText("")
                    }
                }
            }, 100)
            return@setOnLongClickListener true
        }

        btnSavePin.setOnClickListener {
            if (pin.equals(confirmPin)){
                tvErrorMessage.visibility = View.GONE
                Toast.makeText(this,"Pincode Set",Toast.LENGTH_SHORT).show()
            }else{
                tvErrorMessage.visibility = View.VISIBLE
            }
        }
    }

    fun confirmText() {
        if (circleField.text?.length == 4) {
            if (isCreate == false) {
                isCreate = true
                pin = circleField.text.toString()
                Handler().postDelayed({
                    tvTitleMessage.text = "Confirm your 4-digit Pincode"
                    circleField.setText("")
                }, 100)
            } else {
                confirmPin = circleField.text.toString()

                if (pin.equals(confirmPin)){
                    tvErrorMessage.visibility = View.GONE
                }else{
                    tvErrorMessage.visibility = View.VISIBLE
                }
            }
        }

        Log.e("*** pin - ", pin)
        Log.e("*** confirm pin - ", confirmPin)
    }
}
