package com.azabost.quest.posts.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azabost.quest.logging.Logger
import com.azabost.quest.logging.create
import com.azabost.quest.posts.PostsRepository
import com.azabost.quest.posts.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: PostsRepository,
    loggerFactory: Logger.Factory,
) : ViewModel() {
    private val logger = loggerFactory.create(this::class)
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    sealed class UiState {
        object Loading : UiState()
        data class Success(val posts: List<Post>) : UiState()
        object Error : UiState()
    }

    fun getPosts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val posts = repository.getPosts()
                logger.debug("Fetched ${posts.size} posts")
                _uiState.value = UiState.Success(posts)
            } catch (e: Exception) {
                ensureActive()
                logger.error("Failed to fetch posts", e)
                _uiState.value = UiState.Error
            }
        }
    }
}
