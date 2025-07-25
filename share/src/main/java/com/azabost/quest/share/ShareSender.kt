package com.azabost.quest.share

interface ShareSender {
    fun share(text: String): ShareResult
}