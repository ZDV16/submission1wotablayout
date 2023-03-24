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
    init{
        getFollowers(username = "")
    }

/*  fun getFollowing(username: String) {
      _isLoading.value = true
      val client = ApiConfig.getApiService().getDetailFollowing(username)
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
  }*/

/* fun getFollowers(username: String) {
     _isLoading.value = true
     val user = ApiConfig.getApiService().getDetailFollower(username)
     user.enqueue(object : retrofit2.Callback<GithubResponse> {
         override fun onResponse(
             call: Call<GithubResponse>,
             response: Response<GithubResponse>
         ) {
             _isLoading.value = false
             if (response.isSuccessful) {
                 _followers.value = response.body()?.gitHubResponse
             } else {
                 Log.e(TAG, "onFailure: ${response.message()}")
             }
         }
         override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
             _isLoading.value = false
             Log.e(TAG, "onFailure: ${t.message.toString()}")
         }
     })
 }*/

    fun getFollowers(username: String) {
        _isLoading.value = true
        val userFollowers = ApiConfig.getApiService().getDetailFollower(username)
        userFollowers.enqueue(object : Callback<List<GithubResponseItem>> {
            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                println("gagal")
            }

            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>
            ) {
                _followers.value = response.body() as ArrayList<GithubResponseItem>?
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val userFollowing = ApiConfig.getApiService().getDetailFollowing(username)
        userFollowing.enqueue(object : Callback<List<GithubResponseItem>> {
            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                println("gagal")
            }

            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>
            ) {
                _following.value = response.body() as ArrayList<GithubResponseItem>?
            }
        })
    }

companion object {
   private const val TAG = "FollowViewModel"
}

}


