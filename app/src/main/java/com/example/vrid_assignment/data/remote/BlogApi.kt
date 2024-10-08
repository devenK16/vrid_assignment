package com.example.vrid_assignment.data.remote

import com.example.vrid_assignment.data.model.BlogPost
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogApi {
    @GET("posts")
    suspend fun getPosts(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<BlogPost>
}