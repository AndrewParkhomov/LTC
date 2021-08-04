package parkhomov.andrew.lensthicknesscalculator.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.*
import parkhomov.andrew.lensthicknesscalculator.R
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.compare_list.*
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.extension.observe
import parkhomov.andrew.lensthicknesscalculator.utils.getColorFromId
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelCompareList
import org.koin.androidx.viewmodel.ext.android.viewModel


class CompareList : Fragment(R.layout.compare_list) {

    private val viewModel: ViewModelCompareList by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observe(viewModel.state) { onStateChanged(it) }
        viewModel.getListForCompare()
        text_view_clear_list.setOnClickListener {
            viewModel.clearCompareList()
            container_description.removeAllViews()
            container_scroll_view.removeAllViews()
            text_view_clear_list.visibility = View.GONE
            createEmptyListView()
        }
    }

    private fun onStateChanged(event: ViewModelCompareList.State) {
        when (event) {
            is ViewModelCompareList.State.ListToCompare -> {
                setUpAdapter(event.compareList)
            }
        }
    }

    override fun onDestroyView() {
        viewModel.clearEvents()
        super.onDestroyView()
    }

    private fun setUpAdapter(compareSet: MutableSet<CalculatedData>) {
        val compareList: MutableList<CalculatedData> = compareSet.toMutableList()

        val textColorBlack = requireContext().getColorFromId(R.color.black)
        val dividerColor = requireContext().getColorFromId(R.color.gray_500)

        text_view_clear_list.visibility = if (compareList.isEmpty()) View.GONE else View.VISIBLE
        container_description.visibility = if (compareList.isEmpty()) View.GONE else View.VISIBLE
        if (compareList.isEmpty()) {
            createEmptyListView()
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

        container_description.createVerticalLayout(item, textColorBlack, dividerColor, 20f)
        container_scroll_view.linearLayout {
            compareList.forEach { item ->
                createVerticalLayout(item, textColorBlack, dividerColor)
                view {
                    setBackgroundColor(dividerColor)
                }.lparams(dip(0.5f), matchParent)
            }
        }
    }

    private fun createEmptyListView() {
        container_main_compare.verticalLayout {
            lparams(matchParent, matchParent)
            textView(R.string.compare_list_is_empty_title) {
                textSize = 26f
                setTypeface(typeface, Typeface.BOLD)
                textColor = context.getColorFromId(R.color.main_text_color)
                gravity = Gravity.CENTER_HORIZONTAL
            }.lparams(wrapContent, wrapContent) {
                setMargins(0, dip(60), 0, 0)
                gravity = Gravity.CENTER_HORIZONTAL
            }
            textView(R.string.compare_list_is_empty_description) {
                textSize = 20f
                setTypeface(typeface, Typeface.NORMAL)
                textColor = context.getColorFromId(R.color.main_text_color)
                gravity = Gravity.CENTER_HORIZONTAL
            }.lparams(wrapContent, wrapContent) {
                setMargins(dip(20), dip(14), dip(20), 0)
                gravity = Gravity.CENTER_HORIZONTAL
            }
        }
    }

    private fun ViewManager.createVerticalLayout(
        item: CalculatedData,
        textColorBlack: Int,
        dividerColor: Int,
        mTextSize: Float = 24f
    ): LinearLayout {
        return verticalLayout {
            getTextView(item.refractionIndex, textColorBlack, mTextSize)
            getDivider(dividerColor)
            getTextView(item.spherePower, textColorBlack, mTextSize)
            getDivider(dividerColor)
            getTextView(item.cylinderPower, textColorBlack, mTextSize)
            getDivider(dividerColor)
            getTextView(item.axis, textColorBlack, mTextSize)
            getDivider(dividerColor)
            getTextView(item.thicknessOnAxis, textColorBlack, mTextSize)
            getDivider(dividerColor)
            getTextView(item.thicknessCenter, textColorBlack, mTextSize)
            getDivider(dividerColor)
            getTextView(item.thicknessEdge, textColorBlack, mTextSize)
            getDivider(dividerColor)
            getTextView(item.thicknessMax, textColorBlack, mTextSize)
            getDivider(dividerColor)
            getTextView(item.realBaseCurve, textColorBlack, mTextSize)
            getDivider(dividerColor)
            getTextView(item.diameter, textColorBlack, mTextSize)
            getDivider(dividerColor)
        }
    }

    private fun ViewManager.getTextView(text: String?, color: Int, mTextSize: Float = 24f): TextView {
        return textView(text) {
            layoutParams = ViewGroup.LayoutParams(wrapContent, dip(44))
            textSize = mTextSize
            textColor = color
            lines = 1
            ellipsize = TextUtils.TruncateAt.END
            gravity = Gravity.START or Gravity.CENTER
            setPadding(dip(4), 0, dip(4), 0)
        }
    }

    private fun ViewManager.getDivider(color: Int): View {
        return view {
            layoutParams = ViewGroup.LayoutParams(matchParent, dip(0.5f))
            setBackgroundColor(color)
        }
    }
}