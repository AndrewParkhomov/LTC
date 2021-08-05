package parkhomov.andrew.lensthicknesscalculator.view

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.compare_list.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.extension.observe
import parkhomov.andrew.lensthicknesscalculator.utils.dip
import parkhomov.andrew.lensthicknesscalculator.utils.getColorFromId
import parkhomov.andrew.lensthicknesscalculator.utils.shortCollect
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelCompareList


class CompareList : Fragment(R.layout.compare_list) {

    private val viewModel: ViewModelCompareList by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observe(viewModel.state) { onStateChanged(it) }
        setFlowListeners()
        viewModel.getListForCompare()
        viewModel.setMainFabIcon(R.drawable.ic_outline_delete_24)
    }

    private fun setFlowListeners() {
        viewModel.onFabClicked.onEach { onClearButtonClicked() }.shortCollect(lifecycleScope)
    }

    private fun onClearButtonClicked() {
        viewModel.clearCompareList()
        container_description.removeAllViews()
        container_list_empty.removeAllViews()
        linear_scroll_container.removeAllViews()
        setEmptyContainerVisibility(true)
        createEmptyListView()
    }

    private fun setEmptyContainerVisibility(isVisible: Boolean) {
        container_list_empty.isVisible = isVisible
    }

    private fun onStateChanged(event: ViewModelCompareList.State) {
        when (event) {
            is ViewModelCompareList.State.ListToCompare -> setUpAdapter(event.compareList)
        }
    }

    override fun onDestroyView() {
        viewModel.clearEvents()
        super.onDestroyView()
    }

    private fun setUpAdapter(compareSet: MutableSet<CalculatedData>) {
        setEmptyContainerVisibility(compareSet.isEmpty())
        if (compareSet.isEmpty()) {
            createEmptyListView()
        }else {
            val textColorBlack = requireContext().getColorFromId(R.color.black)
            val dividerColor = requireContext().getColorFromId(R.color.gray_500)

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
            val descriptionLayout = requireContext()
                .createVerticalLayout(item, textColorBlack, dividerColor, 20f)

            container_description.addView(descriptionLayout)
            compareSet.forEach { calculatedData ->
                val divider = requireContext().getDividerVertical(dividerColor)
                val verticalLayout = requireContext()
                    .createVerticalLayout(calculatedData, textColorBlack, dividerColor)

                linear_scroll_container.addView(verticalLayout)
                linear_scroll_container.addView(divider)
            }
        }
    }

    private fun createEmptyListView() {
        val title = TextView(requireContext()).apply {
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    setMargins(0, dip(60), 0, 0)
                    gravity = Gravity.CENTER_HORIZONTAL
                }
            setText(R.string.compare_list_is_empty_title)
            textSize = 26f
            setTypeface(typeface, Typeface.BOLD)
            setTextColor(getColorFromId(R.color.main_text_color))
            gravity = Gravity.CENTER_HORIZONTAL
        }
        val description = TextView(requireContext()).apply {
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    setMargins(dip(20), dip(14), dip(20), 0)
                    gravity = Gravity.CENTER_HORIZONTAL
                }
            setText(R.string.compare_list_is_empty_description)
            textSize = 20f
            setTypeface(typeface, Typeface.NORMAL)
            setTextColor(getColorFromId(R.color.main_text_color))
            gravity = Gravity.CENTER_HORIZONTAL
        }

        container_list_empty.addView(title)
        container_list_empty.addView(description)
    }

    private fun Context.createVerticalLayout(
        item: CalculatedData,
        textColorBlack: Int,
        dividerColor: Int,
        mTextSize: Float = 24f
    ): LinearLayout {
        return LinearLayout(this).apply {
            orientation = VERTICAL
            addView(getTextView(item.refractionIndex, textColorBlack, mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(item.spherePower, textColorBlack, mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(item.cylinderPower, textColorBlack, mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(item.axis, textColorBlack, mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(item.thicknessOnAxis, textColorBlack, mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(item.thicknessCenter, textColorBlack, mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(item.thicknessEdge, textColorBlack, mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(item.thicknessMax, textColorBlack, mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(item.realBaseCurve, textColorBlack, mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(item.diameter, textColorBlack, mTextSize))
            addView(getDividerHorizontal(dividerColor))
        }
    }

    private fun Context.getTextView(text: String?, color: Int, mTextSize: Float = 24f): TextView {
        return TextView(this).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, dip(44))
            textSize = mTextSize
            setTextColor(color)
            setLines(1)
            setText(text)
            ellipsize = TextUtils.TruncateAt.END
            gravity = Gravity.START or Gravity.CENTER
            setPadding(dip(4), 0, dip(4), 0)
        }
    }

    private fun Context.getDividerHorizontal(color: Int): View {
        return View(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, dip(1) / 2)
            setBackgroundColor(color)
        }
    }

    private fun Context.getDividerVertical(color: Int): View {
        return View(this).apply {
            layoutParams = LayoutParams(dip(1) / 2, LayoutParams.MATCH_PARENT)
            setBackgroundColor(color)
        }
    }
}