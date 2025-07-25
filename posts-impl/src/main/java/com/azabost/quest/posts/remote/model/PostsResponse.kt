package com.azabost.quest.posts.remote.model

import kotlinx.serialization.Serializable

typealias PostsResponse = List<PostsResponseItem>

@Serializable
data class PostsResponseItem(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)
