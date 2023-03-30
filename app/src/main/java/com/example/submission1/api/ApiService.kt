package com.example.submission1.api

import com.example.submission1.GithubResponse
import com.example.submission1.GithubResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token ")
    @GET("search/users")
    fun getGithub(@Query("q") q: String
    ): Call<GithubResponse>

    @Headers("Authorization: token ")
    @GET("users/{username}")
    fun getDetail(@Path("username") username: String
    ): Call<DetailResponse>

    @Headers("Authorization: token ")
    @GET("users/{username}/followers")
    fun getDetailFollower(@Path("username") username: String
    ): Call<List<GithubResponseItem>>

    @Headers("Authorization: token ")
    @GET("users/{username}/following")
    fun getDetailFollowing(@Path("username") username: String
    ): Call<List<GithubResponseItem>>


}