package astral.loader

import android.animation.TimeInterpolator
import android.view.animation.PathInterpolator


internal object ShaiMotion {

    const val PRESS_IN_MS = 100L
    const val PRESS_OUT_MS = 160L
    const val ENTRANCE_MS = 320L


    const val PHASE_IN_MS = 640L
    const val PHASE_OUT_MS = 420L

    const val DRAWER_OPEN_MS = 300L
    const val DRAWER_CLOSE_MS = 220L
    const val SCRIM_OPEN_MS = 210L
    const val SCRIM_CLOSE_MS = 170L
    const val ROW_MS = 240L
    const val ROW_STAGGER_MS = 35L
    const val CARD_CONFIRM_MS = 220L


    const val ILLUSTRATION_MS = 720L
    const val HIGHLIGHT_MS = 300L


    const val HEADLINE_HOLD_MS = 4000L
    const val HEADLINE_FADE_OUT_MS = 180L
    const val HEADLINE_FADE_IN_MS = 220L


    val easeOut: TimeInterpolator = PathInterpolator(0.23f, 1f, 0.32f, 1f)


    val easeInOut: TimeInterpolator = PathInterpolator(0.77f, 0f, 0.175f, 1f)


    val easeDrawer: TimeInterpolator = PathInterpolator(0.32f, 0.72f, 0f, 1f)
}
