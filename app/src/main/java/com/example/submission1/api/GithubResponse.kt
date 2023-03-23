package com.example.submission1

import com.google.gson.annotations.SerializedName

data class GithubResponse(

	@field:SerializedName("items")
	val gitHubResponse: ArrayList<GithubResponseItem>
)


data class GithubResponseItem(


	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("html_url")
	var htmlUrl: String,

	@field:SerializedName("avatar_url")
	var avatarUrl: String,


)