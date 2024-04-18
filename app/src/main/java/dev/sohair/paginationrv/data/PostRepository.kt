package dev.sohair.paginationrv.data

import dev.sohair.paginationrv.data.remote.PostApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PostRepository(private val api: PostApi) {

    suspend fun getPosts(page: Int): List<Post> {
        return (api.getPosts(page).body() ?: emptyList())
    }
}