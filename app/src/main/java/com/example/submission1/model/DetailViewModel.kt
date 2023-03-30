package com.example.submission1.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submission1.api.ApiConfig
import com.example.submission1.api.DetailResponse
import com.example.submission1.room.FavoriteUser
import com.example.submission1.room.FavoriteUserDao
import com.example.submission1.room.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: FavoriteUserDao?
    private var userDB: UserDatabase?

    init {
        userDB = UserDatabase.getDatabase(application)
        userDao = userDB?.favoriteUserDao()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userSelected = MutableLiveData<DetailResponse>()
    val userSelected: LiveData<DetailResponse> = _userSelected

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun findDetail(username: String) {
        _isLoading.value = true
        val userSelect = ApiConfig.getApiService().getDetail(username)
        userSelect.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userSelected.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun addToFavorite(id: Int, login: String, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                id, login, avatarUrl
            )
            userDao?.addFavorite(user)
        }
    }

    fun checkUser(id: Int) = userDao?.check(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavorite(id)
        }
    }
}