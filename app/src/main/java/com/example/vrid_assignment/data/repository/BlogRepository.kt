package com.example.vrid_assignment.data.repository

import com.example.vrid_assignment.data.model.BlogPost
import com.example.vrid_assignment.data.remote.BlogApi
import javax.inject.Inject


class BlogRepository @Inject constructor(
    private val api: BlogApi
) {
    suspend fun getBlogPosts(page: Int, perPage: Int): List<BlogPost> {
        return api.getPosts(page, perPage)
    }
}