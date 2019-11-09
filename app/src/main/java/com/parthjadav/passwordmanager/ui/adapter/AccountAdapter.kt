package com.parthjadav.passwordmanager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.model.Account

class AccountAdapter (val accountList:ArrayList<Account>) : RecyclerView.Adapter<AccountAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_accounts, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: AccountAdapter.ViewHolder, position: Int) {
        holder.bindItems(accountList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return accountList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(account: Account) {
            val tvAccountName = itemView.findViewById(R.id.tvAccountName) as TextView
            val imgAccount  = itemView.findViewById(R.id.imgAccount) as AppCompatImageView
            tvAccountName.text = account.accountName
            imgAccount.setImageResource(account.accountImage)
        }
    }
}