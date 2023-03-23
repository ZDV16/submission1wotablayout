package com.example.submission1.api

import com.example.submission1.GithubResponse
import com.example.submission1.GithubResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getGithub(@Query("q") q: String
    ): Call<GithubResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getDetail(@Path("username") username: String
    ): Call<DetailResponse>

    @Headers("Authorization: token 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")
    @GET("user/{username}/following")
    fun getDetailFollower(@Path("username") username: String
    ): Call<ArrayList<GithubResponseItem>>

    @Headers("Authorization: token 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")
    @GET("user/{username}/followers")
    fun getDetailFollowing(@Path("username") username: String
    ): Call<ArrayList<GithubResponseItem>>


}