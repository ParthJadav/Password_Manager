package com.parthjadav.passwordmanager.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.utils.PreferenceManager
import com.parthjadav.passwordmanager.utils.Tools
import kotlinx.android.synthetic.main.activity_set_pin.*


class SetPinActivity : AppCompatActivity() {

    val handler = Handler()
    var pin: String = ""
    var confirmPin: String = ""
    var isCreate: Boolean? = false
    var isPinSet: Boolean = false

    private lateinit var preferenceManager: PreferenceManager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_pin)

        preferenceManager = PreferenceManager(this)

        isPinSet =preferenceManager.getKeyValueBoolean("isPinSet")

        if (isPinSet) {
            if (preferenceManager.getKeyValueBoolean("isLock")){
                btnSetPinBack.visibility = View.INVISIBLE
            }else if (preferenceManager.getKeyValueBoolean("viewPassword")){
                btnSetPinBack.visibility = View.INVISIBLE
            }
            tvTitleMessage.text = "Enter your 6-digit Pincode"
        }

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

            if (isPinSet) {
                val savedPin = preferenceManager.getKeyValueString("pincode")

                if (savedPin.equals(circleField.text.toString())) {
                    if (preferenceManager.getKeyValueBoolean("isLock")){
                        val mainIntent = Intent(this, MainActivity::class.java)
                        startActivity(mainIntent)
                        finish()
                    }else if (preferenceManager.getKeyValueBoolean("viewPassword")){
                        val mainIntent = Intent()
                        mainIntent.putExtra("view", "1")
                        setResult(Activity.RESULT_OK, mainIntent)
                        finish()
                    }else {
                        preferenceManager.setKeyValueBoolean("isPinSet", false)
                        preferenceManager.setKeyValueString("pincode", "")
                        Tools.makeToast(this,"PinCode removed successfully.",R.drawable.splash_one,Toast.LENGTH_SHORT)

                        Handler().postDelayed({
                            finish()
                        }, 500)
                    }
                }else{
                    tvErrorMessage.visibility = View.VISIBLE
                    Handler().postDelayed({
                        circleField.setText("")
                    }, 200)
                }
            } else {
                if (pin.equals(confirmPin)) {
                    tvErrorMessage.visibility = View.GONE
                    preferenceManager.setKeyValueBoolean("isPinSet", true)
                    preferenceManager.setKeyValueString("pincode", pin)

                    Tools.makeToast(this,"PinCode set successfully.",R.drawable.splash_one,Toast.LENGTH_SHORT)

                    Handler().postDelayed({
                        finish()
                    }, 500)

                } else {
                    tvErrorMessage.visibility = View.VISIBLE
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun confirmText() {
        if(!isPinSet) {
            if (circleField.text?.length == 6) {
                if (isCreate == false) {
                    isCreate = true
                    pin = circleField.text.toString()
                    Handler().postDelayed({
                        tvTitleMessage.text = "Confirm your 6-digit Pincode"
                        circleField.setText("")
                    }, 100)
                } else {
                    confirmPin = circleField.text.toString()

                    if (pin.equals(confirmPin)) {
                        tvErrorMessage.visibility = View.GONE
                    } else {
                        tvErrorMessage.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}
