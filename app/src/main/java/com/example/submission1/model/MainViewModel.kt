package com.example.submission1.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1.GithubResponse
import com.example.submission1.GithubResponseItem
import com.example.submission1.api.ApiConfig
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {
    var URL = "ZDV"

    private val _itemsitem = MutableLiveData<ArrayList<GithubResponseItem>>()
    val itemsitem: LiveData<ArrayList<GithubResponseItem>> = _itemsitem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
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
}