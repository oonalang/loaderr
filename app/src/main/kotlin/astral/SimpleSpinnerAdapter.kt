package astral.loader

import android.content.Context
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
            setTextColor(ShaiPalette.blend(ShaiPalette.ink, ShaiPalette.orchid, phase))
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.spinner_dropdown_item, parent, false)
        view.findViewById<TextView>(R.id.spinnerDropDownText).text = values[position]
        return view
    }
}
