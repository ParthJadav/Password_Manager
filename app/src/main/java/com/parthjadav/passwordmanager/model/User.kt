package com.parthjadav.passwordmanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
class User {
    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    @ColumnInfo(name = "firstname")
    private var firstName: String? = null
    @ColumnInfo(name = "lastname")
    private var lastName: String? = null
    @ColumnInfo(name = "mobile_number")
    private var mobileNumber: String? = null
    @ColumnInfo(name = "password")
    private var password: String? = null

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getFirstName(): String? {
        return firstName
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
    }

    fun getLastName(): String? {
        return lastName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
    }

    fun getMobileNumber(): String? {
        return mobileNumber
    }

    fun setMobileNumber(mobileNumber: String) {
        this.mobileNumber = mobileNumber
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }
}