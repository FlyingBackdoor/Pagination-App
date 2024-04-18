package dev.sohair.paginationrv.utils

import dev.sohair.paginationrv.data.Post

object PostCaching {
    //For simplicity we are using a global variable to store the selected post
    //Since this is a singleton it can store the value as long as app in the memory
    var selectedPost: Post? = null
}