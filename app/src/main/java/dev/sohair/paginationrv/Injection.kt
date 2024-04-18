package dev.sohair.paginationrv

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import dev.sohair.paginationrv.data.PostRepository
import dev.sohair.paginationrv.data.remote.PostApi
import dev.sohair.paginationrv.utils.ViewModelFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injection {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private fun provideRetrofit(): Retrofit {
        val loggingClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(loggingClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val postApiService: PostApi
        get() = provideRetrofit()
            .create(PostApi::class.java)

    fun providePostRepository() = PostRepository(postApiService)

    fun provideViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return ViewModelFactory(owner, providePostRepository())
    }

}