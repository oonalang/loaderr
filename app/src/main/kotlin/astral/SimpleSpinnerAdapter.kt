package astral.loader

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * Spinner cell colors follow the sheet → court transition, while the popup
 * stays dark so the option list is readable on paper and on violet alike.
 */
class SimpleSpinnerAdapter(
    context: Context,
    private val values: Array<String>
) : ArrayAdapter<String>(context, R.layout.spinner_item_simple, values) {

    private var phase: Float = 0f

    fun applyPhase(progress: Float) {
        phase = progress.coerceIn(0f, 1f)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.spinner_item_simple, parent, false)
        view.findViewById<TextView>(R.id.spinnerText).apply {
            text = values[position]
            setTextColor(ShaiPalette.blend(ShaiPalette.ink, ShaiPalette.clayInk, phase))
            typeface = if (phase >= .5f) {
                ShaiType.label(context)
            } else {
                ShaiType.sheetDisplay(context)
            }
            // The chevron is drawn in clay ink so it belongs to the same object.
            compoundDrawableTintList = ColorStateList.valueOf(
                ShaiPalette.blend(ShaiPalette.pencil, ShaiPalette.clayMuted, phase)
            )
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.spinner_dropdown_item, parent, false)
        view.findViewById<TextView>(R.id.spinnerDropDownText).apply {
            text = values[position]
            typeface = ShaiType.body(context)
        }
        return view
    }
}
