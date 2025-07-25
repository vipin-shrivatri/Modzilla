package com.azabost.quest.share

import android.content.Intent
import javax.inject.Inject

class ShareSenderImpl @Inject constructor(
    private val sharingActivityHolder: SharingActivityHolder,
) : ShareSender {

    override fun share(text: String): ShareResult {
        val activity = sharingActivityHolder.getSharingActivity()
            ?: return ShareResult.Failure(IllegalStateException("No sharing activity"))

        return try {
            val intent = createShareTextIntent(text)
            activity.startActivity(intent)
            ShareResult.Success
        } catch (e: Exception) {
            ShareResult.Failure(e)
        }
    }

    private fun createShareTextIntent(text: String): Intent =
        Intent.createChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            },
            null,
        )
}