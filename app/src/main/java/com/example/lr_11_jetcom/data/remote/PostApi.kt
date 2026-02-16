package com.example.lr_11_jetcom.data.remote

import com.example.lr_11_jetcom.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {
    @GET("posts")
    suspend fun getPosts(
        @Query("_page") page: Int = 1,
        @Query("_limit") limit: Int = 20
    ): List<Post>
}