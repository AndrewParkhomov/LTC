package parkhomov.andrew.comparelist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.holder_empty.view.*
import kotlinx.android.synthetic.main.holder_normal.view.*
import parkhomov.andrew.base.base.BaseViewHolder
import parkhomov.andrew.base.base.BaseViewHolder.Companion.VIEW_TYPE_DESCRIPTION
import parkhomov.andrew.base.base.BaseViewHolder.Companion.VIEW_TYPE_EMPTY
import parkhomov.andrew.base.base.BaseViewHolder.Companion.VIEW_TYPE_NORMAL
import parkhomov.andrew.base.data.result.CalculatedData
import parkhomov.andrew.comparelist.R

class AdapterCompareList(
        private var itemList: MutableList<CalculatedData>
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_DESCRIPTION -> HolderDescription(LayoutInflater.from(parent.context).inflate(
                    R.layout.holder_description,
                    parent,
                    false
            ))
            VIEW_TYPE_NORMAL -> HolderNormal(LayoutInflater.from(parent.context).inflate(
                    R.layout.holder_normal,
                    parent,
                    false
            ))
            else -> HolderEmpty(LayoutInflater.from(parent.context).inflate(
                            R.layout.holder_empty,
                            parent,
                            false
                    )
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.onBind(position)

    override fun getItemCount(): Int = if(itemList.isEmpty()) 1 else itemList.size

    override fun getItemViewType(position: Int): Int {
        return if(itemList.isEmpty()) {
            VIEW_TYPE_EMPTY
        }else{
            if(position == 0){
                VIEW_TYPE_DESCRIPTION
            }else{
                VIEW_TYPE_NORMAL
            }
        }
    }

    inner class HolderDescription(private val view: View) : BaseViewHolder(view) {

        override fun onBind(position: Int) {
            super.onBind(position)
            val item = itemList[position]
            view.apply {

            }
        }
    }

    inner class HolderNormal(private val view: View) : BaseViewHolder(view) {

        override fun onBind(position: Int) {
            super.onBind(position)
            val item = itemList[position]
            view.apply {
                text_view_refraction_index.text = item.refractionIndex
                text_view_sphere_power.text = item.spherePower
                text_view_cylinder_power.text = item.cylinderPower
                text_view_axis.text = item.axis
                text_view_thickness_on_axis.text = item.thicknessOnAxis
                text_view_thickness_center.text = item.thicknessCenter
                text_view_thickness_edge.text = item.thicknessEdge
                text_view_thickness_max.text = item.thicknessMax
                text_view_real_base_curve.text = item.realBaseCurve
                text_view_diameter.text = item.diameter
            }
        }
    }

    inner class HolderEmpty(private var view: View) : BaseViewHolder(view) {

        override fun onBind(position: Int) {
            super.onBind(position)
            view.apply {
                text_view_title.text = "List is empty"
            }
        }
    }
}
