package com.parthjadav.passwordmanager.repository

import android.content.Context
import com.parthjadav.passwordmanager.db.PasswordManagerDatabase
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.parthjadav.passwordmanager.utils.AppUtils
import android.R.attr.description
import android.provider.ContactsContract.CommonDataKinds.Note
import com.parthjadav.passwordmanager.model.User
import android.os.AsyncTask




class PasswordManagerRepository(
    var passwordManagerDatabase: PasswordManagerDatabase,
    context: Context
) {
    private val DB_NAME = "db_password_manager"

    init {
        passwordManagerDatabase =
            databaseBuilder(context, PasswordManagerDatabase::class.java, DB_NAME).build();
    }

    fun insertUser(firstName:String,lastName:String,username:String,password:String){
        val user = User()
        user.setFirstName(firstName)
        user.setLastName(lastName)
        user.setUsername(username)
        user.setPassword(password)

        insertUser(user)
    }

    fun insertUser(user: User) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                passwordManagerDatabase.userDao().insertUser(user)
                return null
            }
        }.execute()
    }
}