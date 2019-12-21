package parkhomov.andrew.comparelist.view

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.compare_list_fragment.*
import org.jetbrains.anko.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.base.base.BaseFragment
import parkhomov.andrew.base.data.result.CalculatedData
import parkhomov.andrew.base.extension.observe
import parkhomov.andrew.base.utils.getColorFromId
import parkhomov.andrew.comparelist.R
import parkhomov.andrew.comparelist.viewmodel.ViewModelCompareList


class CompareList : BaseFragment() {

    private val viewModel: ViewModelCompareList by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.compare_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observe(viewModel.getState) { onStateChanged(it) }
        viewModel.getListForCompare()
    }

    private fun onStateChanged(event: ViewModelCompareList.State) {
        when (event) {
            is ViewModelCompareList.State.ListToCompare -> {
                setUpAdapter(event.compareList)
            }
        }
    }

    private fun setUpAdapter(compareSet: MutableSet<CalculatedData>) {
        val compareList: MutableList<CalculatedData> = compareSet.toMutableList()

        val textColorBlack = requireContext().getColorFromId(R.color.black)
        val dividerColor = requireContext().getColorFromId(R.color.gray_500)

        if (compareList.isEmpty()) {
            container_main_compare.linearLayout {
                lparams(matchParent, matchParent)
                textView("List is empty") {
                    textSize = 30f
                    textColor = dividerColor
                    gravity = Gravity.CENTER_HORIZONTAL
                    setPadding(0, dip(20), 0, 0)
                }.lparams(matchParent, matchParent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
            }
            container_description.visibility = View.GONE
            return
        }

        val item = CalculatedData(
                refractionIndex = getString(R.string.compare_list_index_of_refraction),
                spherePower = getString(R.string.compare_list_sphere_power),
                cylinderPower = getString(R.string.compare_list_cylinder_power),
                axis = getString(R.string.compare_list_axis),
                thicknessOnAxis = getString(R.string.compare_list_on_axis_thickness),
                thicknessCenter = getString(R.string.compare_list_center_thickness),
                thicknessEdge = getString(R.string.compare_list_min_edge_thickness),
                thicknessMax = getString(R.string.compare_list_max_edge_thickness),
                realBaseCurve = getString(R.string.compare_list_base_curve),
                diameter = getString(R.string.compare_list_diameter)
        )

        container_description.createVerticalLayout(item, textColorBlack, dividerColor)
        container_scroll_view.linearLayout {
            compareList.forEach { item ->
                createVerticalLayout(item, textColorBlack, dividerColor)
                view {
                    setBackgroundColor(dividerColor)
                }.lparams(dip(0.5f), matchParent)
            }
        }
    }

    private fun ViewManager.createVerticalLayout(item: CalculatedData, textColorBlack: Int, dividerColor: Int): LinearLayout {
        return verticalLayout {
            getTextView(item.refractionIndex, textColorBlack)
            getDivider(dividerColor)
            getTextView(item.spherePower, textColorBlack)
            getDivider(dividerColor)
            getTextView(item.cylinderPower, textColorBlack)
            getDivider(dividerColor)
            getTextView(item.axis, textColorBlack)
            getDivider(dividerColor)
            getTextView(item.thicknessOnAxis, textColorBlack)
            getDivider(dividerColor)
            getTextView(item.thicknessCenter, textColorBlack)
            getDivider(dividerColor)
            getTextView(item.thicknessEdge, textColorBlack)
            getDivider(dividerColor)
            getTextView(item.thicknessMax, textColorBlack)
            getDivider(dividerColor)
            getTextView(item.realBaseCurve, textColorBlack)
            getDivider(dividerColor)
            getTextView(item.diameter, textColorBlack)
            getDivider(dividerColor)
        }
    }

    private fun ViewManager.getTextView(text: String?, color: Int): TextView {
        return textView(text) {
            layoutParams = ViewGroup.LayoutParams(wrapContent, dip(50))
            textSize = 24f
            textColor = color
            gravity = Gravity.CENTER
            setPadding(dip(4), 0, dip(4), 0)
        }
    }

    private fun ViewManager.getDivider(color: Int): View {
        return view {
            layoutParams = ViewGroup.LayoutParams(matchParent, dip(0.5f))
            setBackgroundColor(color)
        }
    }

    companion object {
        val TAG: String = CompareList::class.java.name
        val instance = CompareList()
    }
}