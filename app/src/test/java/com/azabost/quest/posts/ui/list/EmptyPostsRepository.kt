package com.azabost.quest.posts.ui.list

import com.azabost.quest.posts.PostsRepository
import com.azabost.quest.posts.model.Post

class EmptyPostsRepository : PostsRepository {
    override suspend fun getPosts(): List<Post> = emptyList()
    override suspend fun getPost(id: Int): Post? = null
}