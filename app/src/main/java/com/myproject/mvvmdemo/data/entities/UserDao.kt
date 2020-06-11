package com.myproject.mvvmdemo.data.entities

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao{


    @Insert(onConflict = OnConflictStrategy.REPLACE) // we can override the currently saved user with the new one
    suspend fun upsert(user:User): Long

    @Query("SELECT * FROM User WHERE uid = $CURRENT_USER_ID")
    fun getUser() : LiveData<User>


}