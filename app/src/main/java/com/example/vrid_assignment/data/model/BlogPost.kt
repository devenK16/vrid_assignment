package com.example.vrid_assignment.data.model

data class BlogPost(
    val id: Int,
    val title: Title,
    val link: String
)

data class Title(
    val rendered: String
)

data class Content(
    val rendered: String
)
