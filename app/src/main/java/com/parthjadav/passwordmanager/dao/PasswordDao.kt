package com.parthjadav.passwordmanager.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.parthjadav.passwordmanager.model.Password
import com.parthjadav.passwordmanager.model.User

@Dao
interface PasswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPassword(password: Password): Long

    @Query("SELECT * FROM password WHERE id=:passwordId")
    fun getPasswordDetails(passwordId: String): List<Password>

    @Query("SELECT * FROM password ORDER BY id DESC")
    fun getAllPassword(): MutableList<Password>

    @Update
    fun updatePassword(password: Password)

    @Delete
    fun deletePassword(password: Password)

    @Query("DELETE FROM password WHERE id=:passwordId")
    fun deletePassword(passwordId: String):Int

}
