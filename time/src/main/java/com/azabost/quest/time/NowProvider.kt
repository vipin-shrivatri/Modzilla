package com.azabost.quest.time

import javax.inject.Inject
import javax.inject.Singleton

interface NowProvider {
    fun now(): Long
}

@Singleton
class DefaultNowProvider @Inject constructor() : NowProvider {
    override fun now(): Long = System.currentTimeMillis()
}