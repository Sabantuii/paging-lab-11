package com.example.lr_11_jetcom.data.remote

import android.util.Log
import androidx.paging.*
import com.example.lr_11_jetcom.data.model.Post

class PostPagingSource(
    private val api: PostApi
) : PagingSource<Int, Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val page = params.key ?: 1

            Log.d("PostPaging", "Загрузка страницы $page (по ${params.loadSize} элементов)")

            val response = api.getPosts(page = page, limit = params.loadSize)

            Log.d("PostPaging", "Страница $page загружена: ${response.size} постов")

            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.size == params.loadSize) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}