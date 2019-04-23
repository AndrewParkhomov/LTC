package parkhomov.andrew.base.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun onBind(position: Int) {}

    companion object {

        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_NORMAL = 1
        const val VIEW_TYPE_DISCOUNT = 2
    }
}
