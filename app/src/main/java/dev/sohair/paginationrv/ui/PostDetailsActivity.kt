package dev.sohair.paginationrv.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.sohair.paginationrv.databinding.ActivityPostDetailsBinding
import dev.sohair.paginationrv.utils.PostCaching

class PostDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PostCaching.selectedPost?.let {
            binding.apply {
                tvPostId.text = "ID: ${it.id}"
                tvPostDate.text = "UserId: ${it.userId}"
                tvPostContent.text = it.body
                tvPostTitle.text = it.title
            }
        }

    }
}