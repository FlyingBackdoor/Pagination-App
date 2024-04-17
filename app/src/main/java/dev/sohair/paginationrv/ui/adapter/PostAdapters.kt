package dev.sohair.paginationrv.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.sohair.paginationrv.data.Post
import dev.sohair.paginationrv.databinding.ItemPostBinding

class PostAdapters(
    private val onItemClicked: (Post) -> Unit
) : RecyclerView.Adapter<PostAdapters.PostViewHolder>() {
    class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    var items = mutableListOf<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvTitle.text = "Title: ${item.title}"
            tvId.text = "Id: ${item.id}"
            root.setOnClickListener {
                onItemClicked(item)
            }
        }
    }
}