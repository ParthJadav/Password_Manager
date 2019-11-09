package com.parthjadav.passwordmanager.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
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

        recyclerViewAccounts.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val accounts = ArrayList<Account>()

        accounts.add(Account("Gmail", R.drawable.gmail))
        accounts.add(Account("Google", R.drawable.google))
        accounts.add(Account("Yahoo", R.drawable.yahoo))
        accounts.add(Account("Twitter", R.drawable.twitter))
        accounts.add(Account("Facebook", R.drawable.facebook))
        accounts.add(Account("Slack", R.drawable.slack))
        accounts.add(Account("Stack Overflow", R.drawable.stack_overflow))
        accounts.add(Account("LinkedIn", R.drawable.linkedin))
        accounts.add(Account("Amazon", R.drawable.amazon))
        accounts.add(Account("Instagram", R.drawable.instagram))
        accounts.add(Account("WhatsApp", R.drawable.whatsapp))
        accounts.add(Account("Flipkart", R.drawable.flipkart))
        accounts.add(Account("Snapchat", R.drawable.snapchat))
        accounts.add(Account("Line", R.drawable.line))
        accounts.add(Account("WeChat", R.drawable.we_chat))

        val accountAdapter = AccountAdapter(accounts)
        recyclerViewAccounts.adapter = accountAdapter
    }
}
