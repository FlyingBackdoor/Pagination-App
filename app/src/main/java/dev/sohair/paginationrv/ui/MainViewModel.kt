package dev.sohair.paginationrv.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.sohair.paginationrv.data.Post
import dev.sohair.paginationrv.data.PostPagingSource
import dev.sohair.paginationrv.data.PostRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    val repository: PostRepository
) : ViewModel() {
    /**
     * Stream of [Post]s for the UI.
     */
    val posts: Flow<PagingData<Post>> = Pager(
        config = PagingConfig(
            pageSize = 15,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PostPagingSource(repository) }
    )
        .flow
        .cachedIn(viewModelScope)
}