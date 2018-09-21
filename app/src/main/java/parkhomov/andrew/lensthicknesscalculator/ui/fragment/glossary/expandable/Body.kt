package parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.expandable

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import parkhomov.andrew.lensthicknesscalculator.R

@Layout(R.layout.expandable_body)
class Body(
        private val itemImage: Drawable,
        private val itemDescription: String
) {

    @View(R.id.image_view)
    lateinit var image: ImageView

    @View(R.id.text_view_description)
    lateinit var description: TextView

    @Resolve
    fun onResolved() {
        image.setImageDrawable(itemImage)
        description.text = itemDescription
    }
}
