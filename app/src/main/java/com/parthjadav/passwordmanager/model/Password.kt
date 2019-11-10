package com.parthjadav.passwordmanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "password")
class Password {
    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    @ColumnInfo(name = "account_name")
    private var accountName: String? = null
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private var accountImage: ByteArray? = null
    @ColumnInfo(name = "title")
    private var title: String? = null
    @ColumnInfo(name = "user_id")
    private var userId: String? = null
    @ColumnInfo(name = "password")
    private var password: String? = null

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getAccountName(): String? {
        return accountName.toString()
    }

    fun setAccountName(accountName: String) {
        this.accountName = accountName
    }

    fun getAccountImage(): ByteArray? {
        return accountImage
    }

    fun setAccountImage(accountImage: ByteArray) {
        this.accountImage = accountImage
    }

    fun getTitle(): String? {
        return title.toString()
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getUserId(): String? {
        return userId.toString()
    }

    fun setUserId(userId: String) {
        this.userId = userId
    }

    fun getPassword(): String? {
        return password.toString()
    }

    fun setPassword(password: String) {
        this.password = password
    }
}