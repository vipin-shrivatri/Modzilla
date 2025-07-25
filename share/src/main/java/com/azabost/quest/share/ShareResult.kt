package com.azabost.quest.share

sealed interface ShareResult {
    object Success : ShareResult
    class Failure(val reason: Throwable) : ShareResult
}