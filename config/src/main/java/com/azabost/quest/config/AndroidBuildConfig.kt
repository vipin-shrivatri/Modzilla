package com.azabost.quest.config

import javax.inject.Inject
import javax.inject.Named

class AndroidBuildConfig @Inject constructor(
    @param:Named("isDebug") private val debug: Boolean

) : Config {
    override val isDebug: Boolean = debug
}
