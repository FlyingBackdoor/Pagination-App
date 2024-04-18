package dev.sohair.paginationrv.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.sohair.paginationrv.data.Post
import dev.sohair.paginationrv.databinding.ItemPostBinding

/**
* Data adapter to load data from PagingSource
* */
class PostAdapters(
    private val onItemClicked: (Post) -> Unit
) : PagingDataAdapter<Post, PostAdapters.PostViewHolder>(POST_DIFF_CALLBACK) {
    inner class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            with(holder.binding) {
                tvTitle.text = "Title: ${item.title}"
                tvId.text = "Id: ${item.id}"
                root.setOnClickListener {
                    onItemClicked(item)
                }
            }
        }
    }

    companion object {
        private val POST_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem == newItem
        }
    }
}