package com.example.lr_11_jetcom.data

import androidx.paging.*
import com.example.lr_11_jetcom.data.model.Post
import com.example.lr_11_jetcom.data.remote.PostPagingSource
import com.example.lr_11_jetcom.data.remote.RetrofitClient
import kotlinx.coroutines.flow.Flow

object PostPagingProvider {
    private val api = RetrofitClient.postApi

    fun getPostsFlow(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PostPagingSource(api) }
        ).flow
    }
}