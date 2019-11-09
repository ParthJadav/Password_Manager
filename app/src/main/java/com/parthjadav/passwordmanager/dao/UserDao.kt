package com.parthjadav.passwordmanager.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.parthjadav.passwordmanager.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    @Query("SELECT * FROM user WHERE id=:userId")
    fun login(userId: String): LiveData<List<User>>

    @Update
    fun updateUser(user: User)


    @Delete
    fun deleteUser(user: User)

}
