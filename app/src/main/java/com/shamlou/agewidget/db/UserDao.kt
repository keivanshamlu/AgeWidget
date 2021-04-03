package com.shamlou.agewidget.db

import androidx.room.*
import com.shamlou.agewidget.db.entities.UserBirth

@Dao
interface UserDao {
    @Query("SELECT * FROM UserBirth LIMIT 1")
    fun getUserBirth(): UserBirth?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userBirths: UserBirth)

    @Query("DELETE FROM UserBirth")
    fun deleteRaw(): Int
}