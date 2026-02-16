package com.example.lr_11_jetcom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.lr_11_jetcom.data.PostPagingProvider


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListPagingScreen() {
    val posts = PostPagingProvider.getPostsFlow().collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Посты (Paging)") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Обработка состояния "обновление при открытии" (refresh)
            when (val refresh = posts.loadState.refresh) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        ErrorItem(
                            message = refresh.error.message ?: "Не удалось загрузить посты",
                            onRetry = { posts.retry() }
                        )
                    }
                }

                else -> {
                    // Ничего не делаем — данные уже есть или загружаются дальше
                }
            }

            // Основные элементы списка
            items(
                count = posts.itemCount,
                key = { index ->
                    // Используем id поста как ключ, если он есть; иначе — индекс
                    posts[index]?.id ?: index
                }
            ) { index ->
                val post = posts[index]
                if (post != null) {
                    PostItem(post = post)
                } else {
                    // Placeholder для ещё не загруженных элементов (если enablePlaceholders = true)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            // Обработка состояния "подгрузка при прокрутке вниз" (append)
            if (posts.loadState.append is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            if (posts.loadState.append is LoadState.Error) {
                item {
                    ErrorItem(
                        message = (posts.loadState.append as LoadState.Error).error.message
                            ?: "Ошибка подгрузки",
                        onRetry = { posts.retry() }
                    )
                }
            }
        }
    }
}

// Вспомогательный компонент для отображения ошибки с кнопкой "Повторить"
@Composable
private fun ErrorItem(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Повторить")
        }
    }
}