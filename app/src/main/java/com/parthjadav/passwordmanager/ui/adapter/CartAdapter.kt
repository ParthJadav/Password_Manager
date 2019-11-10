package com.parthjadav.passwordmanager.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.parthjadav.passwordmanager.R

import butterknife.ButterKnife

class CartAdapter(internal var context: Context) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_accounts, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.linLayAccount.setOnClickListener { onClickListener!!.onClick(position) }
    }

    override fun getItemCount(): Int {
        return 10
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var linLayAccount: LinearLayout = itemView.findViewById(R.id.layoutAccount)

    }
}
