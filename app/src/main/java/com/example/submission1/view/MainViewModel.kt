package com.example.submission1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1.api.ApiConfig
import com.example.submission1.api.DetailResponse
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {
    var URL = "ZDV"

    private val _itemsitem = MutableLiveData<ArrayList<GithubResponseItem>>()
    val itemsitem: LiveData<ArrayList<GithubResponseItem>> = _itemsitem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userSelected = MutableLiveData<DetailResponse>()
    val userSelected: LiveData<DetailResponse> = _userSelected

    companion object{
        private const val TAG = "MainViewModel"
    }

    init{
        findUser()
    }

      fun findUser() {
        _isLoading.value = true
        val user = ApiConfig.getApiService().getGithub(URL)
        user.enqueue(object : retrofit2.Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _itemsitem.value = response.body()?.gitHubResponse
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
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
}