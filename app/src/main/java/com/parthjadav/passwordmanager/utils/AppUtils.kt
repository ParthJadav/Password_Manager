package com.parthjadav.passwordmanager.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Handler
import android.util.Base64
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.parthjadav.passwordmanager.R


import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import android.content.res.Resources


public class AppUtils {

    fun dpToPx(dp: Float): Float {
        return dp * Resources.getSystem().getDisplayMetrics().density
    }

    val currentDateTime: Date
        get() = Calendar.getInstance().time

    fun getFormattedDateString(date: Date): String? {

        try {

            var spf = SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy")
            val dateString = spf.format(date)

            val newDate = spf.parse(dateString)
            spf = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
            return spf.format(newDate!!)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    fun generateHash(password: String): String? {
        var md: MessageDigest? = null
        try {
            md = MessageDigest.getInstance("SHA-512")
            md!!.update(password.toByteArray())
            val byteData = md.digest()
            return Base64.encodeToString(byteData, Base64.NO_WRAP)

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return null
    }

    fun setSystemBarColor(act: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = act.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = act.resources.getColor(R.color.colorBlack)
        }
    }

    fun setSystemBarColorWhite(act: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = act.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = act.resources.getColor(R.color.colorWhite)
        }
    }

    fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun openKeyboard(context: Context) {
        Handler().postDelayed({
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }, 500)
    }

    fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }
}
