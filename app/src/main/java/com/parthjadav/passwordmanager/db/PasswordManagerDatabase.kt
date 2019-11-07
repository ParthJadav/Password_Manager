package com.parthjadav.passwordmanager.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.parthjadav.passwordmanager.dao.UserDao
import com.parthjadav.passwordmanager.model.User

@Database(entities = [User::class],version = 1,exportSchema = false)
abstract class PasswordManagerDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}