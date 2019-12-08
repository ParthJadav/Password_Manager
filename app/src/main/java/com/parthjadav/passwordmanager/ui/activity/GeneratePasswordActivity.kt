package com.parthjadav.passwordmanager.ui.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.seekbar.OnSeekBarChangeListener
import com.parthjadav.passwordmanager.seekbar.SeekBar
import com.parthjadav.passwordmanager.utils.PasswordGenerator
import com.parthjadav.passwordmanager.utils.Tools
import kotlinx.android.synthetic.main.activity_generate_password.*


class GeneratePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_password)

        btnGeneratePassword.setOnClickListener {
            finish()
        }
        btnCopyPassword.setOnClickListener {
            val password: String = tvGeneratedPassword.text.toString()
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(password, password)

            clipboard.setPrimaryClip(clip)
            Tools.makeToast(this,"Password Copied",R.drawable.ic_copy,Toast.LENGTH_SHORT)
        }
        seekBarPassword.isEnabled = true;
        seekBarPassword.progress = 8
        seekBarPassword.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int) {
                tvGeneratedPassword.text = getPasswordGenerator()?.generate(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }

    private fun getPasswordGenerator(): PasswordGenerator? {
        return PasswordGenerator.Builder()
            .useLower(switchLowercase.isChecked)
            .useUpper(switchUppercase.isChecked)
            .useDigits(switchDigits.isChecked)
            .usePunctuation(switchSpecialChars.isChecked)
            .build()
    }
}
