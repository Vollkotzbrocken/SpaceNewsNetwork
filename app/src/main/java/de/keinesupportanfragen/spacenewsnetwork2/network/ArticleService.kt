package de.keinesupportanfragen.spacenewsnetwork2.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleService {
    @GET("v3/articles")
    fun getAllArticles() : Call<List<Article>>

    @GET("v3/articles/{id}")
    fun getSpecificArticle(@Path("id") id: Int) : Call<Article>
}