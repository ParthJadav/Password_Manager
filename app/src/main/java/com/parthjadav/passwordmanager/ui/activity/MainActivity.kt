package com.parthjadav.passwordmanager.ui.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.an.customfontview.CustomTextView
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.dao.PasswordDao
import com.parthjadav.passwordmanager.db.AppDatabase
import com.parthjadav.passwordmanager.model.Password
import com.parthjadav.passwordmanager.ui.adapter.PasswordAdapter
import com.parthjadav.passwordmanager.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var passwordDao: PasswordDao? = null
    lateinit var passwords: MutableList<Password>
    lateinit var layoutManager: LinearLayoutManager
    private var deletePassStatus: Int = 0
    private var isDetails: Boolean = false
    private var isEdit: Boolean = false
    private var isBack: Boolean = false
    private var isPasswordChange: Boolean = false

    private lateinit var preferenceManager: PreferenceManager

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferenceManager = PreferenceManager(this)

        passwords = ArrayList()

        swipePasswordList.setOnRefreshListener {
            swipePasswordList.isRefreshing = true
            passwords.clear()
            getPasswords()
        }

        preferenceManager.setKeyValueBoolean("isLock", false)
        layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerViewPassword.layoutManager = layoutManager

        getPasswords()

        imgBtnSettings.setOnClickListener {
            val mainIntent = Intent(this, SettingsActivity::class.java)
            startActivity(mainIntent)
        }

        btnAddPassword.setOnClickListener {
            isDetails = false
            val mainIntent = Intent(this, AddPasswordActivity::class.java)
            startActivity(mainIntent)
        }

    }

    override fun onBackPressed() {
        dialogExit(this)
    }

    @SuppressLint("SetTextI18n")
    private fun dialogExit(mContext: Context) {
        try {
            val inflater =
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val alertLayout = inflater.inflate(R.layout.dialog_logout, null)

            val alert = AlertDialog.Builder(mContext)
            alert.setView(alertLayout)
            alert.setCancelable(false)

            val title = alertLayout.findViewById<CustomTextView>(R.id.tvTitle)
            val message = alertLayout.findViewById<CustomTextView>(R.id.tvMessage)
            val btnExit = alertLayout.findViewById<CustomTextView>(R.id.tvPositive)
            val btnCancel = alertLayout.findViewById<CustomTextView>(R.id.tvNegative)

            title.text = "Exit"
            message.text = "Are you sure you want to exit?"
            btnExit.text = "Exit"

            val dialog: AlertDialog
            dialog = alert.create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            btnExit.setOnClickListener {
                dialog.dismiss()
                super.onBackPressed()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        } catch (e: Exception) {

        }
    }

    @SuppressLint("SetTextI18n")
    private fun getPasswords() {

        Observable.fromCallable {
            db = AppDatabase.getAppDataBase(context = this)
            passwordDao = db?.passwordDao()


            with(passwordDao) {

                if (passwords.size > 0) {
                    passwords.clear()
                }

                passwords = this?.getAllPassword()!!
            }
        }.doOnNext { list ->
            runOnUiThread {

                if (passwords.size > 0) {
                    swipePasswordList.isRefreshing = false
                    recyclerViewPassword.visibility = View.VISIBLE
                    layoutNoDataMain.visibility = View.GONE

                    val passwordAdapter = PasswordAdapter(
                        passwords,
                        passwords,
                        object : PasswordAdapter.OnPasswordClickListener {
                            override fun onDelete(position: Int, password: Password) {
                                deletePassword(password.getId().toString())
                            }

                            override fun onClick(
                                position: Int,
                                imageView: AppCompatImageView,
                                password: Password
                            ) {
                                isDetails = true
                                val intent =
                                    Intent(this@MainActivity, PasswordDetailsActivity::class.java)
                                intent.putExtra("accountName", password.getAccountName().toString())
                                intent.putExtra("id", password.getId().toString())
                                intent.putExtra("accountImage", password.getAccountImage())
                                intent.putExtra("title", password.getTitle().toString())
                                intent.putExtra("userId", password.getUserId().toString())
                                intent.putExtra("password", password.getPassword().toString())

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    val options: ActivityOptionsCompat =
                                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                            this@MainActivity,
                                            androidx.core.util.Pair<View, String>(
                                                imageView,
                                                "imageTransition"
                                            )
                                        )
                                    startActivity(intent, options.toBundle())
                                } else {
                                    startActivity(intent)
                                }
                            }

                            override fun onEdit(
                                position: Int,
                                imageView: AppCompatImageView,
                                password: Password
                            ) {
                                isEdit = true
                                val intent =
                                    Intent(this@MainActivity, UpdatePasswordActivity::class.java)
                                intent.putExtra("accountName", password.getAccountName().toString())
                                intent.putExtra("id", password.getId().toString())
                                intent.putExtra("accountImage", password.getAccountImage())
                                intent.putExtra("title", password.getTitle().toString())
                                intent.putExtra("userId", password.getUserId().toString())
                                intent.putExtra("password", password.getPassword().toString())

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    val options: ActivityOptionsCompat =
                                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                            this@MainActivity,
                                            androidx.core.util.Pair<View, String>(
                                                imageView,
                                                "imageTransition"
                                            )
                                        )
                                    startActivity(intent, options.toBundle())
                                } else {
                                    startActivity(intent)
                                }
                            }
                        })

                    recyclerViewPassword.adapter = passwordAdapter
                    edtSearchPassword.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(p0: Editable?) {
                        }

                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            passwordAdapter.search(
                                p0!!,
                                layoutNoDataMain,
                                recyclerViewPassword,
                                tvError1,
                                tvError2,
                                tvError3
                            )
                        }
                    })
                } else {
                    recyclerViewPassword.visibility = View.GONE
                    layoutNoDataMain.visibility = View.VISIBLE
                }
            }

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun onResume() {
        super.onResume()
        isPasswordChange = preferenceManager.getKeyValueBoolean("isPasswordChange")
        if (!isDetails || !isEdit) {
            getPasswords()
        }

        if (isPasswordChange){
            preferenceManager.setKeyValueBoolean("isPasswordChange",false)
            getPasswords()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deletePassword(passwordId: String) {

        Observable.fromCallable {
            db = AppDatabase.getAppDataBase(context = this)
            passwordDao = db?.passwordDao()

            with(passwordDao) {
                deletePassStatus = this?.deletePassword(passwordId)!!
            }
        }.doOnNext { list ->
            runOnUiThread {

                if (deletePassStatus > 0) {
                    val myToast =
                        Toast.makeText(
                            applicationContext,
                            "Password Deleted",
                            Toast.LENGTH_SHORT
                        )
                    myToast.show()

                    getPasswords();
                } else {
                    val myToast =
                        Toast.makeText(
                            applicationContext,
                            "Something went wrong, Please try again.",
                            Toast.LENGTH_SHORT
                        )
                    myToast.show()
                }
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}
