package com.parthjadav.passwordmanager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.model.Account


class AccountAdapter(
    private val accountList: ArrayList<Account>,
    private var accountListSearch: ArrayList<Account>,
    private val onAccountClickListener: OnAccountClickListener
) :
    RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_accounts, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvAccountName.text = accountListSearch[position].accountName
        holder.imgAccount.setImageResource(accountListSearch[position].accountImage)

        holder.linLayAccount.setOnClickListener {
            onAccountClickListener.onClick(
                position,
                accountListSearch[position].accountName,
                accountListSearch[position].accountImage
            )
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return accountListSearch.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal val tvAccountName: TextView = itemView.findViewById(R.id.tvAccountName)
        internal val imgAccount: AppCompatImageView = itemView.findViewById(R.id.imgAccount)
        internal var linLayAccount: LinearLayout = itemView.findViewById(R.id.layoutAccount)

    }

    interface OnAccountClickListener {
        fun onClick(position: Int, accountName: String, accountImage: Int)
    }

    @SuppressLint("DefaultLocale")
    fun search(charSequence: CharSequence, l: LinearLayout, r: RecyclerView) {
        try {
            val charString = charSequence.toString()
            if (charString.isEmpty() || charString == null) {
                accountListSearch = accountList
                r.visibility = View.VISIBLE
                l.visibility = View.GONE
                notifyDataSetChanged()
            } else {
                var flag = 0
                val filteredList = ArrayList<Account>()
                for (row in accountList) {
                    if (row.accountName.toLowerCase().contains(charString.toLowerCase())) {
                        filteredList.add(row)
                        flag = 1
                    }
                }

                if (flag == 1) {
                    accountListSearch = filteredList
                    r.visibility = View.VISIBLE
                    l.visibility = View.GONE
                } else {
                    r.visibility = View.GONE
                    l.visibility = View.VISIBLE
                }

                notifyDataSetChanged()
            }
        } catch (e: Exception) {

        }

    }
}