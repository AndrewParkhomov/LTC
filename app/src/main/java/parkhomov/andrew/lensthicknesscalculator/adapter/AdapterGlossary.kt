package parkhomov.andrew.lensthicknesscalculator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.GlossaryItem
import parkhomov.andrew.lensthicknesscalculator.utils.getDrawableFromId

class AdapterGlossary(
    private var list: List<GlossaryItem.Data>
) : RecyclerView.Adapter<AdapterGlossary.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView = view.findViewById(R.id.text_view_title)
        val textViewDescription: TextView = view.findViewById(R.id.text_view_description)
        val imageViewPicture: ImageView = view.findViewById(R.id.image_view_picture)
        val imageViewArrow: ImageView = view.findViewById(R.id.image_view_arrow_down)
        val scrollView: HorizontalScrollView = view.findViewById(R.id.scroll_view)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.glossary_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = list[position]
        with(viewHolder) {

            fun setViewState() {
                textViewDescription.isVisible =  item.isContentShown
                scrollView.isVisible =  item.isContentShown
                imageViewArrow.scaleY = if (item.isContentShown) -1f else 1f
            }

            val context = itemView.context
            textViewTitle.text = item.title
            textViewDescription.text = item.body
            imageViewPicture.setImageDrawable(context.getDrawableFromId(item.imageId))
            setViewState()
            textViewTitle.setOnClickListener {
                item.isContentShown = !item.isContentShown
                setViewState()
            }
        }
    }

    override fun getItemCount() = list.size
}