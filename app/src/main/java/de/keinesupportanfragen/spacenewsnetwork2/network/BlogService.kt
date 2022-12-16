package de.keinesupportanfragen.spacenewsnetwork2.network

import retrofit2.Call
import retrofit2.http.GET

interface BlogService {
    @GET("v3/blogs")
    fun getAllBlogItems() : Call<List<Article>>
}