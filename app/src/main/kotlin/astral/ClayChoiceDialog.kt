package astral.loader

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

/** Shared clay selection sheet, also used by the persistent theme picker. */
internal class ClayChoiceDialog(context: Context, title: String, choices: List<String>, selected: Int,
    private val phase: Float = 1f, private val choose: (Int) -> Unit) : Dialog(context) {
    private val body = LinearLayout(context)
    private var closing = false
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val d = context.resources.displayMetrics.density
        body.orientation = LinearLayout.VERTICAL
        body.setPadding((18*d).toInt(),(20*d).toInt(),(18*d).toInt(),(20*d).toInt())
        body.background = PhaseSurfaceDrawable(context, PhaseSurfaceDrawable.Kind.CARD).apply { this.phase = this@ClayChoiceDialog.phase }
        body.addView(TextView(context).apply {
            text = title; textSize = 22f; typeface = if (phase > .5f) ShaiType.display(context) else ShaiType.sheetDisplay(context)
            setTextColor(if (phase > .5f) ShaiPalette.clayInk else ShaiPalette.ink)
            setPadding((12*d).toInt(),0,(12*d).toInt(),(12*d).toInt())
            isAccessibilityHeading = true
        })
        val rows = LinearLayout(context).apply { orientation = LinearLayout.VERTICAL }
        choices.forEachIndexed { i, label ->
            rows.addView(TextView(context).apply {
                text = if (i == selected) "✓  $label" else label
                textSize = 16f; typeface = ShaiType.label(context)
                setTextColor(if (phase > .5f) ShaiPalette.clayInk else ShaiPalette.ink)
                minHeight = (52*d).toInt();gravity=Gravity.CENTER_VERTICAL
                setPadding((16*d).toInt(),(12*d).toInt(),(16*d).toInt(),(12*d).toInt())
                background=PhaseSurfaceDrawable(context,PhaseSurfaceDrawable.Kind.ROW).apply { this.phase=this@ClayChoiceDialog.phase;this.selected=i==selected }
                isSelected=i==selected;isFocusable=true;isClickable=true
                contentDescription=if(i==selected) "$label, selected" else label
                setOnClickListener { if(!closing) { choose(i); dismiss() } }
                InteractionMotion.press(this)
            },LinearLayout.LayoutParams(-1,-2))
        }
        body.addView(ScrollView(context).apply { addView(rows) }, LinearLayout.LayoutParams(-1,-2))
        setContentView(body)
        setCanceledOnTouchOutside(true)
    }
    override fun onStart() {
        super.onStart();closing=false
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));setDimAmount(.24f)
            setLayout((context.resources.displayMetrics.widthPixels*.9f).toInt(),ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        // Keep every choice reachable in landscape/large text via the scroll viewport.
        body.post {
            val scroll=body.getChildAt(1)
            val max=(context.resources.displayMetrics.heightPixels*.65f).toInt()
            if(scroll.height>max) scroll.layoutParams=LinearLayout.LayoutParams(-1,max)
        }
        InteractionMotion.reveal(body)
    }
    override fun dismiss() {
        if(closing) return
        closing=true;body.animate().cancel()
        if(!android.animation.ValueAnimator.areAnimatorsEnabled()) { super.dismiss();return }
        body.animate().alpha(0f).translationY(6*context.resources.displayMetrics.density)
            .setDuration(ShaiMotion.PRESS_OUT_MS).setStartDelay(0).withEndAction { super.dismiss() }.start()
    }
    fun closeImmediately() { closing=true;InteractionMotion.cancelTree(body);super.dismiss() }
    override fun onStop() { InteractionMotion.cancelTree(body);super.onStop() }
}
