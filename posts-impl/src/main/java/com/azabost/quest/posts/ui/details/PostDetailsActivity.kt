package com.azabost.quest.posts.ui.details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.azabost.quest.posts.model.Post
import com.azabost.quest.theme.QuestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsActivity : ComponentActivity() {

    companion object {
        const val EXTRA_POST_ID = "extra_post_id"
    }

    private val viewModel: PostDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.fetchPost()

        setContent {
            QuestTheme {
                val uiState = viewModel.uiState.collectAsState()
                PostDetailsScreen(
                    uiState = uiState.value,
                    onRetry = viewModel::fetchPost,
                    onShare = viewModel::sharePost,
                )
            }
        }
    }
}

@Composable
fun PostDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: PostDetailsViewModel.UiState,
    onRetry: () -> Unit = {},
    onShare: (Post) -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            if (uiState is PostDetailsViewModel.UiState.PostDetails) {
                FloatingActionButton(
                    onClick = { onShare(uiState.post) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share"
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is PostDetailsViewModel.UiState.Loading -> {
                    Text("Loading...")
                }

                is PostDetailsViewModel.UiState.PostDetails -> {
                    val post = uiState.post
                    Text("Post #${post.id}: ${post.title}\n\n${post.body}")
                }

                is PostDetailsViewModel.UiState.Error -> {
                    androidx.compose.foundation.layout.Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Text("Failed to load post #${uiState.postId}")
                        androidx.compose.material3.Button(
                            onClick = onRetry
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PostDetailsScreenLoadingPreview() {
    QuestTheme {
        PostDetailsScreen(
            uiState = PostDetailsViewModel.UiState.Loading
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PostDetailsScreenDetailsPreview() {
    QuestTheme {
        PostDetailsScreen(
            uiState = PostDetailsViewModel.UiState.PostDetails(
                post = Post(
                    id = 1,
                    userName = "John Doe",
                    title = "Sample Post Title",
                    body = "This is a sample post body with some content to display in the preview."
                )
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PostDetailsScreenErrorPreview() {
    QuestTheme {
        PostDetailsScreen(
            uiState = PostDetailsViewModel.UiState.Error(postId = 42),
            onRetry = {}
        )
    }
}
