package com.example.submission1.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user")
    fun getFavorite(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    fun check(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    fun removeFavorite(id: Int): Int
}