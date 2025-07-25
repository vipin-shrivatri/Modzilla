package com.azabost.quest.posts.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azabost.quest.analytics.Analytics
import com.azabost.quest.analytics.AnalyticsEvent
import com.azabost.quest.logging.Logger
import com.azabost.quest.logging.create
import com.azabost.quest.logging.event
import com.azabost.quest.posts.PostsRepository
import com.azabost.quest.posts.model.Post
import com.azabost.quest.share.SharePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    private val analytics: Analytics,
    private val sharePostUseCase: SharePostUseCase,
    loggerFactory: Logger.Factory,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val logger = loggerFactory.create(this::class)
    val postId: Int = savedStateHandle.get(PostDetailsActivity.EXTRA_POST_ID)!!

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    sealed class UiState {
        object Loading : UiState()
        data class PostDetails(val post: Post) : UiState()
        data class Error(val postId: Int) : UiState()
    }

    fun fetchPost() {
        viewModelScope.launch {
            try {
                val post = postsRepository.getPost(postId)
                if (post != null) {
                    showPostDetails(post)
                } else {
                    logger.error("Couldn't find post with ID: $postId")
                    showError()
                }
            } catch (e: Exception) {
                ensureActive()
                logger.error("Failed to fetch post with ID: $postId", e)
                showError()
            }
        }
    }

    fun sharePost(post: Post) {
        sharePostUseCase.execute(post)
    }

    private fun showPostDetails(post: Post) {
        _uiState.value = UiState.PostDetails(post)
        analytics.logEvent(
            AnalyticsEvent.POST_VIEWED,
            mapOf("postId" to postId),
        )
        logger.event(AnalyticsEvent.POST_VIEWED)
    }

    private fun showError() {
        _uiState.value = UiState.Error(postId)
    }

}
