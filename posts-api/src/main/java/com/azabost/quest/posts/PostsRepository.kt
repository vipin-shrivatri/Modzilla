package com.azabost.quest.posts

import com.azabost.quest.posts.model.Post

interface PostsRepository {
    suspend fun getPosts(): List<Post>
    suspend fun getPost(id: Int): Post?
}