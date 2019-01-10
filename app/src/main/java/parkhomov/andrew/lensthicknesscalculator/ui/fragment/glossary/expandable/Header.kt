package parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.expandable

import android.graphics.drawable.Drawable
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.ImageView
import android.widget.TextView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.expand.*
import parkhomov.andrew.lensthicknesscalculator.R

@Parent
@SingleTop
@Layout(R.layout.expandable_title)
class Header(
        private val title: String
) {
    internal var arrowUp: Drawable? = null
    internal var arrowDown: Drawable? = null

    @View(R.id.expandable_title)
    lateinit var workingHours: TextView

    @View(R.id.expandable_arrow_down)
    lateinit var toggleIcon: ImageView

    @Toggle(R.id.container_expandable_header)
    lateinit var toggleView: androidx.constraintlayout.widget.ConstraintLayout

    @Resolve
    fun onResolved() {
        toggleIcon.setImageDrawable(arrowUp)
        workingHours.text = title
    }

    @Expand
    fun onExpand() {
        toggleIcon.setImageDrawable(arrowDown)
    }

    @Collapse
    fun onCollapse() {
        toggleIcon.setImageDrawable(arrowUp)
    }
}
