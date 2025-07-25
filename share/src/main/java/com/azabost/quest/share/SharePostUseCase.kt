package com.azabost.quest.share

import com.azabost.quest.analytics.Analytics
import com.azabost.quest.analytics.AnalyticsEvent
import com.azabost.quest.logging.Logger
import com.azabost.quest.logging.create
import com.azabost.quest.posts.model.Post
import javax.inject.Inject

class SharePostUseCase @Inject constructor(
    private val shareSender: ShareSender,
    private val analytics: Analytics,
    loggerFactory: Logger.Factory,
) {
    private val logger by lazy { loggerFactory.create(this::class) }

    fun execute(post: Post) {
        val text = "${post.title}\n\n${post.body}"
        val shareResult = shareSender.share(text)
        when (shareResult) {
            ShareResult.Success -> analytics.logEvent(AnalyticsEvent.POST_SHARED)
            is ShareResult.Failure -> logger.error("Failed to share post", shareResult.reason)
        }
    }
}