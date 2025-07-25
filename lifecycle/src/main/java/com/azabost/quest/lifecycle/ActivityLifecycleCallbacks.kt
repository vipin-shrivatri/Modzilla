package com.azabost.quest.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.azabost.quest.logging.Logger
import com.azabost.quest.share.SharingActivityHolder
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityLifecycleCallbacks @Inject constructor(
    loggerFactory: Logger.Factory
) : Application.ActivityLifecycleCallbacks, SharingActivityHolder {

    private var lastResumedActivity: WeakReference<Activity> = WeakReference(null)
    private val logger = loggerFactory.create("ActivityLifecycle")

    override fun getSharingActivity(): Activity? =
        lastResumedActivity.get()
            ?.takeIf { !it.isFinishing }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        logger.debug("onActivityCreated: $p0, bundle: $p1")
    }

    override fun onActivityStarted(p0: Activity) {
        logger.debug("onActivityStarted: $p0")
    }

    override fun onActivityResumed(p0: Activity) {
        logger.debug("onActivityResumed: $p0")
        lastResumedActivity = WeakReference(p0)
    }

    override fun onActivityPaused(p0: Activity) {
        logger.debug("onActivityPaused: $p0")
        lastResumedActivity = WeakReference(null)
    }

    override fun onActivityStopped(p0: Activity) {
        logger.debug("onActivityStopped: $p0")
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        logger.debug("onActivitySaveInstanceState: $p0, bundle: $p1")
    }

    override fun onActivityDestroyed(p0: Activity) {
        logger.debug("onActivityDestroyed: $p0")
    }
}