package com.example.vrid_assignment.ui.screen.bloglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vrid_assignment.data.model.BlogPost
import com.example.vrid_assignment.data.repository.BlogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogListViewModel @Inject constructor(
    private val repository: BlogRepository
) : ViewModel() {

    private val _blogs = MutableStateFlow<List<BlogPost>>(emptyList())
    val blogs: StateFlow<List<BlogPost>> = _blogs

    private var currentPage = 1
    private val perPage = 10

    init {
        loadBlogs()
    }

    fun loadMoreBlogs() {
        currentPage++
        loadBlogs()
    }

    private fun loadBlogs() {
        viewModelScope.launch {
            val newBlogs = repository.getBlogPosts(currentPage, perPage)
            _blogs.value += newBlogs
        }
    }
}