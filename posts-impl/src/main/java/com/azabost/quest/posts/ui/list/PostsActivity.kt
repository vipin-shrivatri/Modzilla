package com.azabost.quest.posts.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azabost.quest.analytics.Analytics
import com.azabost.quest.analytics.AnalyticsEvent
import com.azabost.quest.posts.model.Post
import com.azabost.quest.posts.ui.details.PostDetailsActivity
import com.azabost.quest.theme.QuestTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostsActivity : ComponentActivity() {

    private val viewModel: PostsViewModel by viewModels()

    @Inject
    lateinit var analytics: Analytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.getPosts()

        setContent {
            QuestTheme {
                val uiState = viewModel.uiState.collectAsState()

                PostsScreen(
                    uiState = uiState.value,
                    onPostClick = this::onPostClick,
                    onErrorClick = viewModel::getPosts
                )
            }
        }
    }

    private fun onPostClick(postId: Int) {
        analytics.logEvent(AnalyticsEvent.POST_CLICKED, mapOf("postId" to postId))

        val intent = Intent(this@PostsActivity, PostDetailsActivity::class.java).apply {
            putExtra(PostDetailsActivity.EXTRA_POST_ID, postId)
        }
        startActivity(intent)
    }
}

@Composable
fun PostsScreen(
    uiState: PostsViewModel.UiState,
    modifier: Modifier = Modifier,
    onPostClick: (Int) -> Unit = {},
    onErrorClick: () -> Unit = {},
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is PostsViewModel.UiState.Loading -> {
                    Text("Loading...")
                }

                is PostsViewModel.UiState.Success -> {
                    val posts = uiState.posts
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        posts.forEach { post ->
                            item {
                                Post(
                                    post = post,
                                    onPostClick = onPostClick,
                                    modifier = Modifier.padding(16.dp)
                                )
                                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                            }
                        }
                    }
                }

                is PostsViewModel.UiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Failed to load posts")
                        Button(
                            onClick = onErrorClick
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Post(
    post: Post,
    modifier: Modifier = Modifier,
    onPostClick: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier.clickable { onPostClick(post.id) }
    ) {
        Text(text = post.userName)
        Text(text = post.id.toString())
        Text(text = post.title)
        Text(text = post.body)
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Success State")
@Composable
fun PostsSuccessPreview() {
    QuestTheme {
        val previewPosts = listOf(
            Post(1, "User 1", "Title 1", "Body 1"),
            Post(2, "User 2", "Title 2", "Body 2")
        )

        // Create a preview that shows the Success state
        PostsScreen(uiState = PostsViewModel.UiState.Success(previewPosts))
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Loading State")
@Composable
fun PostsLoadingPreview() {
    QuestTheme {
        // Create a preview that shows the Loading state
        PostsScreen(uiState = PostsViewModel.UiState.Loading)
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Error State")
@Composable
fun PostsErrorPreview() {
    QuestTheme {
        // Create a preview that shows the Error state
        PostsScreen(uiState = PostsViewModel.UiState.Error)
    }
}
