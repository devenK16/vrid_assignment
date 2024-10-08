package com.example.vrid_assignment.ui.screen.bloglist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vrid_assignment.data.model.BlogPost

@Composable
fun BlogListScreen(
    viewModel: BlogListViewModel = hiltViewModel(),
    onBlogSelected: (String) -> Unit
) {
    val blogs by viewModel.blogs.collectAsState()

    LazyColumn {
        items(blogs) { blog ->
            BlogItem(blog = blog, onBlogSelected = onBlogSelected)
        }
    }
}

@Composable
fun BlogItem(blog: BlogPost, onBlogSelected: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onBlogSelected(blog.link) }
    ) {
        Text(
            text = blog.title.rendered,
            modifier = Modifier.padding(16.dp)
        )
    }
}