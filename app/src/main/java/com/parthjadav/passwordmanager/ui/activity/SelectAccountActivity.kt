package com.parthjadav.passwordmanager.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.model.Account
import com.parthjadav.passwordmanager.ui.adapter.AccountAdapter
import kotlinx.android.synthetic.main.activity_select_account.*


class SelectAccountActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_account)

        btnAccountBack.setOnClickListener {
            finish()
        }

        recyclerViewAccounts.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val accounts = ArrayList<Account>()

        accounts.add(Account("Gmail", R.drawable.gmail))
        accounts.add(Account("Google", R.drawable.google))
        accounts.add(Account("Google Drive", R.drawable.drive))
        accounts.add(Account("Yahoo", R.drawable.yahoo))
        accounts.add(Account("GitHub", R.drawable.github))
        accounts.add(Account("Twitter", R.drawable.twitter))
        accounts.add(Account("Facebook", R.drawable.facebook))
        accounts.add(Account("Slack", R.drawable.slack))
        accounts.add(Account("RSS", R.drawable.rss))
        accounts.add(Account("Spotify", R.drawable.spotify))
        accounts.add(Account("Stack Overflow", R.drawable.stack_overflow))
        accounts.add(Account("LinkedIn", R.drawable.linkedin))
        accounts.add(Account("Amazon", R.drawable.amazon))
        accounts.add(Account("YouTube", R.drawable.youtube))
        accounts.add(Account("Instagram", R.drawable.instagram))
        accounts.add(Account("WhatsApp", R.drawable.whatsapp))
        accounts.add(Account("Flipkart", R.drawable.flipkart))
        accounts.add(Account("Google Pay", R.drawable.google_pay))
        accounts.add(Account("PhonePe", R.drawable.phone_pe))
        accounts.add(Account("Paytm", R.drawable.paytm))
        accounts.add(Account("Snapchat", R.drawable.snapchat))
        accounts.add(Account("Windows", R.drawable.windows))
        accounts.add(Account("skype", R.drawable.skype))
        accounts.add(Account("Line", R.drawable.line))
        accounts.add(Account("WeChat", R.drawable.we_chat))
        accounts.add(Account("State Bank of India", R.drawable.sbi))
        accounts.add(Account("Bank of Baroda", R.drawable.bob_bank))
        accounts.add(Account("ICICI Bank", R.drawable.icici))

        val accountAdapter =
            AccountAdapter(accounts, accounts, object : AccountAdapter.OnAccountClickListener {
                override fun onClick(position: Int, accountName: String, accountImage: Int) {
                    val intent = Intent()
                    intent.putExtra("accountName", accountName)
                    intent.putExtra("accountImage", accountImage)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            })
        recyclerViewAccounts.adapter = accountAdapter

        edtSearchAccount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                accountAdapter.search(p0!!, layoutNoData, recyclerViewAccounts)
            }
        })

    }
}
