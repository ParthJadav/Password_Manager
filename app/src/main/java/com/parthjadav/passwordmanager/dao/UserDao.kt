package com.parthjadav.passwordmanager.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.parthjadav.passwordmanager.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    @Query("SELECT * FROM user WHERE id=:userId")
    fun getUserDetails(userId: String): List<User>

    @Query("SELECT * FROM user WHERE mobile_number=:mobileNumber")
    fun isMobileNumberExists(mobileNumber: String): List<User>

    @Query("SELECT * FROM user WHERE mobile_number=:mobileNumber AND password=:password")
    fun login(mobileNumber: String,password: String): List<User>

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

}
