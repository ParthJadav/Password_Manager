package com.parthjadav.passwordmanager.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.parthjadav.passwordmanager.model.User
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.room.Delete
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User): Long?

    @Query("SELECT * FROM User WHERE user_id=:userId")
    fun login(userId: String): LiveData<List<User>>

    @Update
    fun updateUser(user: User)


    @Delete
    fun deleteUser(user: User)

}
