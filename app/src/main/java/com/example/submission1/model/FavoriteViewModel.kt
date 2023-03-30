package com.example.submission1.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.submission1.room.FavoriteUser
import com.example.submission1.room.FavoriteUserDao
import com.example.submission1.room.UserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: FavoriteUserDao?
    private var userDB: UserDatabase?

    init {
        userDB = UserDatabase.getDatabase(application)
        userDao = userDB?.favoriteUserDao()
    }

    fun getFavorite(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavorite()
    }
}