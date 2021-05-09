package com.app.shadi.database.dao

import androidx.room.*
import com.app.shadi.database.entity.UserEntity

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(userEntity: List<UserEntity>)

    @Query(value = "Select * from UserEntity")
    fun getAllUsers(): List<UserEntity>

    @Query(value = "SELECT * FROM UserEntity WHERE phone =:phone")
    fun getUser(phone: String): UserEntity

    @Update
    fun updateUser(userEntity: UserEntity)
}