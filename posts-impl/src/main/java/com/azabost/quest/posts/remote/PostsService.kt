package com.azabost.quest.posts.remote

import com.azabost.quest.posts.remote.model.PostsResponse
import com.azabost.quest.posts.remote.model.UsersResponse
import retrofit2.http.GET

interface PostsService {

    @GET("posts")
    suspend fun getPosts(): PostsResponse

    @GET("users")
    suspend fun getUsers(): UsersResponse

}