package astral.loader

import android.animation.TimeInterpolator
import android.view.animation.PathInterpolator

/**
 * One motion vocabulary for the sheet and the court.
 *
 * Feedback stays short, the phase change is the single rare, longer moment, and
 * every curve is a named token instead of an ad-hoc DecelerateInterpolator.
 */
internal object ShaiMotion {

    const val PRESS_IN_MS = 100L
    const val PRESS_OUT_MS = 160L
    const val ENTRANCE_MS = 320L
    const val RULE_REVEAL_MS = 360L
    const val PHASE_IN_MS = 520L
    const val PHASE_OUT_MS = 420L
    const val CREST_MS = 320L

    const val DRAWER_OPEN_MS = 300L
    const val DRAWER_CLOSE_MS = 220L
    const val SCRIM_OPEN_MS = 210L
    const val SCRIM_CLOSE_MS = 170L
    const val ROW_MS = 240L
    const val ROW_STAGGER_MS = 35L
    const val CARD_CONFIRM_MS = 220L

    /** The illustration inks itself once; no stroke loops afterwards. */
    const val ILLUSTRATION_MS = 720L
    const val HIGHLIGHT_MS = 300L

    const val SUCCESS_SCROLL_DELAY_MS = 560L
    const val DRAWER_REVEAL_DELAY_MS = 760L
    const val REDUCED_REVEAL_DELAY_MS = 80L
    const val REDUCED_DRAWER_DELAY_MS = 260L

    /** Strong ease-out for entrances, replies and press feedback. */
    val easeOut: TimeInterpolator = PathInterpolator(0.23f, 1f, 0.32f, 1f)

    /** Strong ease-in-out for the staged paper → court transformation. */
    val easeInOut: TimeInterpolator = PathInterpolator(0.77f, 0f, 0.175f, 1f)

    /** iOS-like drawer curve, not the default Android decelerate. */
    val easeDrawer: TimeInterpolator = PathInterpolator(0.32f, 0.72f, 0f, 1f)
}
