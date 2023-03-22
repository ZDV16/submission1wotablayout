package com.example.submission1.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1.GithubResponseItem
import com.example.submission1.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _followings = MutableLiveData<List<GithubResponseItem>>()
    val followings: LiveData<List<GithubResponseItem>> = _followings

    private val _followers = MutableLiveData<List<GithubResponseItem>>()
    val followers: LiveData<List<GithubResponseItem>> = _followers

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailFollower(username)
        client.enqueue(object : Callback<ArrayList<GithubResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<GithubResponseItem>>,
                response: Response<ArrayList<GithubResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d(TAG, "${response.body()}")
                    _followers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure :  ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<GithubResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailFollower(username)
        client.enqueue(object : Callback<ArrayList<GithubResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<GithubResponseItem>>,
                response: Response<ArrayList<GithubResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d(TAG, "${response.body()}")
                    _followers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure :  ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<GithubResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "FollowViewModel"
    }

}