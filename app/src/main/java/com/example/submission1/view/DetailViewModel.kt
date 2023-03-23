package com.example.submission1.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1.GithubResponseItem
import com.example.submission1.api.ApiConfig
import com.example.submission1.api.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userSelected = MutableLiveData<DetailResponse>()
    val userSelected: LiveData<DetailResponse> = _userSelected

    private val _follower = MutableLiveData<List<GithubResponseItem>>()
    val follower: LiveData<List<GithubResponseItem>> = _follower

    private val _following = MutableLiveData<List<GithubResponseItem>>()
    val following: LiveData<List<GithubResponseItem>> = _following

    companion object{
        private const val TAG = "DetailViewModel"
    }

    fun findDetail(username: String) {
        _isLoading.value = true
        val userSelect = ApiConfig.getApiService().getDetail(username)
        userSelect.enqueue(object : retrofit2.Callback<DetailResponse> {
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
    fun getFollower(query: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailFollower(query)
        client.enqueue(object : Callback<ArrayList<GithubResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<GithubResponseItem>>,
                response: Response<ArrayList<GithubResponseItem>>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _follower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}222")
                }
            }
            override fun onFailure(call: Call<ArrayList<GithubResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


}