package astral.loader
import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
class BoundedLibraryScroll @JvmOverloads constructor(context:Context,attrs:AttributeSet?=null):ScrollView(context,attrs) {
    override fun onMeasure(w:Int,h:Int) {
        val max=(resources.displayMetrics.heightPixels*.38f).toInt()
        super.onMeasure(w,MeasureSpec.makeMeasureSpec(minOf(MeasureSpec.getSize(h).takeIf { it>0 } ?: max,max),MeasureSpec.AT_MOST))
    }
}
