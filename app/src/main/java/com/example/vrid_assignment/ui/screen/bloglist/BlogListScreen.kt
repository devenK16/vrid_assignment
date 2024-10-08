package com.example.vrid_assignment.ui.screen.bloglist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vrid_assignment.data.model.BlogPost
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BlogListScreen(
    viewModel: BlogListViewModel = hiltViewModel(),
    onBlogSelected: (String) -> Unit
) {
    val blogs by viewModel.blogs.collectAsState()
    val listState = rememberLazyListState()
    var isLoading by remember { mutableStateOf(false) }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(blogs) { blog ->
            BlogItem(
                blog = blog,
                onBlogSelected = {
                    isLoading = true  // Start loading
                    onBlogSelected(blog.link)
                },
                isLoading = isLoading
            )
        }
    }

    LaunchedEffect(listState) {
        if (isLoading) {
            // Simulate a delay for the loading effect
            delay(1000)  // Adjust time as needed
            isLoading = false
        }
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                if (visibleItems.isNotEmpty() && visibleItems.lastOrNull()?.index == blogs.size - 1) {
                    viewModel.loadMoreBlogs()
                }
            }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BlogItem(
    blog: BlogPost, onBlogSelected: (String) -> Unit,
    isLoading: Boolean
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val date = LocalDateTime.parse(blog.date).format(dateFormatter)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onBlogSelected(blog.link) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = blog.title.rendered,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Row {
                Text(
                    text = "Read more...",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Blue),
                    modifier = Modifier.clickable { onBlogSelected(blog.link) }
                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }
        }
    }
}