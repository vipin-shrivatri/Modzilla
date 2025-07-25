package com.azabost.quest.posts.ui.list

import com.azabost.quest.posts.PostsRepository
import com.azabost.quest.posts.model.Post

class FailingPostsRepository : PostsRepository {
    override suspend fun getPosts(): List<Post> = throw RuntimeException("Network error")
    override suspend fun getPost(id: Int): Post? = throw RuntimeException("Network error")
}