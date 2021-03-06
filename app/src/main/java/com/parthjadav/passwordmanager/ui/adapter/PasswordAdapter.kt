package com.parthjadav.passwordmanager.ui.adapter

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.model.Password
import kotlinx.android.synthetic.main.activity_main.*


class PasswordAdapter(
    val passwordList: MutableList<Password>,
    var passwordListSearch: MutableList<Password>,
    val onPasswordClickListener: OnPasswordClickListener
) :
    RecyclerView.Adapter<PasswordAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_password, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvPassListTitle.text = passwordListSearch[position].getTitle()
        holder.tvPassListUserId.text = passwordListSearch[position].getUserId()

        val byteArray: ByteArray? = passwordListSearch[position].getAccountImage()
        val bitmap = BitmapFactory.decodeByteArray(
            byteArray, 0,
            byteArray?.size!!
        )
        holder.imgPassList.setImageBitmap(bitmap)

        holder.layoutPassList.setOnClickListener {
            onPasswordClickListener.onClick(position, holder.imgPassList, passwordListSearch[position]
            )
        }

        holder.linLayDeletePass.setOnClickListener {
            onPasswordClickListener.onDelete(position, passwordListSearch[position])
        }

        holder.linLayEditPass.setOnClickListener {
            onPasswordClickListener.onEdit(position, holder.imgPassList, passwordListSearch[position])
        }
    }

    fun removeAt(position: Int) {
        passwordListSearch.removeAt(position)
        passwordList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemChanged(position)
        notifyDataSetChanged()
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return passwordListSearch.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal val tvPassListTitle: TextView = itemView.findViewById(R.id.tvPassListTitle)
        internal val tvPassListUserId: TextView = itemView.findViewById(R.id.tvPassListUserId)
        internal val imgPassList: AppCompatImageView = itemView.findViewById(R.id.imgPassList)
        internal var layoutPassList: FrameLayout = itemView.findViewById(R.id.layoutPassList)
        internal var linLayDeletePass: LinearLayout = itemView.findViewById(R.id.linLayDeletePass)
        internal var linLayEditPass: LinearLayout = itemView.findViewById(R.id.linLayEditPass)

    }

    interface OnPasswordClickListener {
        fun onClick(position: Int, imageView: AppCompatImageView, password: Password)
        fun onEdit(position: Int, imageView: AppCompatImageView, password: Password)
        fun onDelete(position: Int, password: Password)
    }

    @SuppressLint("DefaultLocale", "SetTextI18n")
    fun search(
        charSequence: CharSequence,
        l: LinearLayout,
        r: RecyclerView,
        tvError1: TextView,
        tvError2: TextView,
        tvError3: TextView
    ) {
        try {
            val charString = charSequence.toString()
            if (charString.isEmpty() || charString == null) {
                passwordListSearch = passwordList
                r.visibility = View.VISIBLE
                l.visibility = View.GONE
                notifyDataSetChanged()
            } else {
                var flag = 0
                val filteredList = ArrayList<Password>()
                for (row in passwordList) {
                    if (row.getAccountName()?.toLowerCase()?.contains(charString.toLowerCase())!! ||
                        row.getTitle()?.toLowerCase()?.contains(charString.toLowerCase())!! ||
                        row.getUserId()?.toLowerCase()?.contains(charString.toLowerCase())!!
                    ) {
                        filteredList.add(row)
                        flag = 1
                    }
                }

                if (flag == 1) {
                    passwordListSearch = filteredList
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