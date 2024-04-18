package dev.sohair.paginationrv.data.remote

import dev.sohair.paginationrv.data.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//Retrofit service interface
interface PostApi {
    @GET("/posts")
    suspend fun getPosts(
        @Query("_page") page: Int
    ): Response<List<Post>>

}