package dev.sohair.paginationrv.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.sohair.paginationrv.Injection
import dev.sohair.paginationrv.databinding.ActivityMainBinding
import dev.sohair.paginationrv.ui.adapter.PostAdapters
import dev.sohair.paginationrv.utils.PostCaching
import dev.sohair.paginationrv.utils.ViewUtils.gone
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>(
        factoryProducer = { Injection.provideViewModelFactory(owner = this) }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val items = viewModel.posts
        val adapter = PostAdapters {
            //start PostDetails activity by passing the Post object
            PostCaching.selectedPost = it
            val intent = Intent(this, PostDetailsActivity::class.java)
            startActivity(intent)
        }

       /*
        * Collect from the PagingData Flow in the ViewModel, and submit it to the
        * PagingDataAdapter.
        * */

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collect {
                    adapter.submitData(it)
                }
            }
        }

        /*
        * Display progress bar while new data is being loaded.
        * */
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect {
                    binding.prependProgress.isVisible = it.source.prepend is LoadState.Loading
                    binding.appendProgress.isVisible = it.source.append is LoadState.Loading
                }
            }
        }
        setupRecyclerView(adapter)
    }

    /**
     * Helper method to set up the RecyclerView.
     * Also adds a divider item decoration.
     * */
    private fun setupRecyclerView(adapter: PostAdapters) {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.apply {
            val decoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(decoration)
        }
        binding.progressBar.gone()
    }
}