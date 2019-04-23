package parkhomov.andrew.glossary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.glossary_item.view.*
import parkhomov.andrew.base.base.BaseViewHolder
import parkhomov.andrew.base.data.glossary.GlossaryData
import parkhomov.andrew.base.utils.getDrawableFromId
import parkhomov.andrew.glossary.R

class AdapterGlossary(
        private var itemList: List<GlossaryData> = listOf()
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
            ListViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.glossary_item,
                    parent,
                    false
            ))

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.onBind(position)

    override fun getItemCount(): Int = itemList.count()

    inner class ListViewHolder(private val view: View) : BaseViewHolder(view) {

        override fun onBind(position: Int) {
            super.onBind(position)
            val item = itemList[position]
            view.apply {
                text_view_title.text = item.title
                text_view_description.text = item.body
                image_view_picture.setImageDrawable(context.getDrawableFromId(item.imageId))
                text_view_title.setOnClickListener {
                    if (text_view_description.visibility == View.VISIBLE) {
                        text_view_description.visibility = View.GONE
                        scroll_view.visibility = View.GONE
                        image_view_arrow_down.scaleY = 1f
                    } else {
                        text_view_description.visibility = View.VISIBLE
                        scroll_view.visibility = View.VISIBLE
                        image_view_arrow_down.scaleY = -1f
                    }
                }
            }
        }
    }
}
