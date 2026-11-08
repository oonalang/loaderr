package astral.loader

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat


internal object ShaiType {

    private var fredoka: Typeface? = null
    private var nunito: Typeface? = null
    private var nunitoSemi: Typeface? = null
    private var grotesk: Typeface? = null
    private var groteskBold: Typeface? = null

    fun display(context: Context): Typeface =
        fredoka ?: font(context, R.font.fredoka_semibold, Typeface.DEFAULT_BOLD).also { fredoka = it }

    fun body(context: Context): Typeface =
        nunito ?: font(context, R.font.nunito_regular, Typeface.DEFAULT).also { nunito = it }

    fun label(context: Context): Typeface =
        nunitoSemi ?: font(context, R.font.nunito_semibold, Typeface.DEFAULT_BOLD).also { nunitoSemi = it }

    fun sheet(context: Context): Typeface =
        grotesk ?: font(context, R.font.space_grotesk_regular, Typeface.DEFAULT).also { grotesk = it }

    fun sheetDisplay(context: Context): Typeface =
        groteskBold ?: font(context, R.font.space_grotesk_bold, Typeface.DEFAULT_BOLD).also { groteskBold = it }

    private fun font(context: Context, id: Int, fallback: Typeface): Typeface =
        ResourcesCompat.getFont(context, id) ?: fallback
}
