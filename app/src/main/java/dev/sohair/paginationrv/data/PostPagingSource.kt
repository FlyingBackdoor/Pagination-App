package dev.sohair.paginationrv.data

import android.net.http.HttpException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.io.IOException
import kotlin.math.max

private const val STARTING_KEY = 1

class PostPagingSource(private val repository: PostRepository) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        //Explain this code
        return try {
            val position = params.key ?: STARTING_KEY
            val response = repository.getPosts(page = position)
            val prevKey = if (position == STARTING_KEY) null else position - 1
            val nextKey = if (response.isEmpty()) null else position + 1
            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}