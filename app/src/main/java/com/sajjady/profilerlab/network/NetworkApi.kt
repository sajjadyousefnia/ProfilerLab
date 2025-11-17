package com.sajjady.profilerlab.network


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class PostDto(val userId: Int, val id: Int, val title: String, val body: String)

interface JsonPlaceholderService {
    @GET("posts")
    suspend fun getPosts(): List<PostDto>
}

object NetworkApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: JsonPlaceholderService = retrofit.create(JsonPlaceholderService::class.java)
}