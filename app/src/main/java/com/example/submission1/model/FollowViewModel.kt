package com.example.submission1.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1.GithubResponse
import com.example.submission1.GithubResponseItem
import com.example.submission1.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class FollowViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _followers = MutableLiveData<ArrayList<GithubResponseItem>>()
    val followers: LiveData<ArrayList<GithubResponseItem>> = _followers

    private val _following = MutableLiveData<ArrayList<GithubResponseItem>>()
    val following: LiveData<ArrayList<GithubResponseItem>> = _following


    fun getFollowers(username: String) {
        _isLoading.value = true
        val userFollowers = ApiConfig.getApiService().getDetailFollower(username)
        userFollowers.enqueue(object : Callback<List<GithubResponseItem>> {

            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body() as ArrayList<GithubResponseItem>?
                }
                else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
    fun getFollowing(username: String) {
        _isLoading.value = true
        val userFollowing = ApiConfig.getApiService().getDetailFollowing(username)
        userFollowing.enqueue(object : Callback<List<GithubResponseItem>> {

            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body() as ArrayList<GithubResponseItem>?
                }
                else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
companion object {
   private const val TAG = "FollowViewModel"
}

}


