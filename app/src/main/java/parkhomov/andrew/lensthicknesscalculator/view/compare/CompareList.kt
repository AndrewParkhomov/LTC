package parkhomov.andrew.lensthicknesscalculator.view.compare

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.databinding.CompareBinding
import parkhomov.andrew.lensthicknesscalculator.extencions.collectData
import parkhomov.andrew.lensthicknesscalculator.extencions.dip
import parkhomov.andrew.lensthicknesscalculator.extencions.getColorFromId


class CompareList : Fragment(R.layout.compare) {

    private val viewModel: CompareListViewModel by viewModel()
    private val binding by viewBinding(CompareBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFlowListeners()
    }

    private fun setFlowListeners() {
        viewModel.getCompareList.onEach(::setUpAdapter).collectData(lifecycleScope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonClearList.setOnClickListener {
            viewModel.onClearClicked()
            clearList()
        }
    }

    private fun clearList() {
        binding.containerDescription.removeAllViews()
        binding.containerListEmpty.removeAllViews()
        binding.linearScrollContainer.removeAllViews()
        setEmptyContainerVisibility(true)
        createEmptyListView()
    }

    private fun setEmptyContainerVisibility(isVisible: Boolean) {
        binding.containerListEmpty.isVisible = isVisible
        binding.buttonClearList.isVisible = !isVisible
    }

    private fun setUpAdapter(compareSet: Set<CalculatedData>) {
        setEmptyContainerVisibility(compareSet.isEmpty())
        if (compareSet.isEmpty()) {
            createEmptyListView()
        } else {
            val dividerColor = getColorFromId(R.color.vertical_divider)

            val descriptionLayout = requireContext().createNames(dividerColor)

            binding.containerDescription.addView(descriptionLayout)
            compareSet.forEach { calculatedData ->
                val divider = requireContext().getDividerVertical(dividerColor)
                val verticalLayout = requireContext()
                    .createLensDataLayout(calculatedData, dividerColor)

                binding.linearScrollContainer.addView(verticalLayout)
                binding.linearScrollContainer.addView(divider)
            }
        }
    }

    private fun createEmptyListView() {
        val mContext = ContextThemeWrapper(requireContext(), R.style.SimpleText)
        val title = TextView(mContext).apply {
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    setMargins(0, dip(60), 0, 0)
                    gravity = Gravity.CENTER_HORIZONTAL
                }
            setText(R.string.compare_list_is_empty_title)
            textSize = 26f
            setTypeface(typeface, Typeface.BOLD)
            gravity = Gravity.CENTER_HORIZONTAL
        }
        val description = TextView(mContext).apply {
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    setMargins(dip(20), dip(14), dip(20), 0)
                    gravity = Gravity.CENTER_HORIZONTAL
                }
            setText(R.string.compare_list_is_empty_description)
            textSize = 20f
            setTypeface(typeface, Typeface.NORMAL)
            gravity = Gravity.CENTER_HORIZONTAL
        }

        binding.containerListEmpty.addView(title)
        binding.containerListEmpty.addView(description)
    }

    private fun Context.createLensDataLayout(
        data: CalculatedData,
        dividerColor: Int,
    ): LinearLayout {
        return LinearLayout(this).apply {
            orientation = VERTICAL
            addView(
                getTextView(
                    data.refractionIndex.replace(" ", "\n"),
                    18f
                )
            )
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(data.spherePower.toString()))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView((data.cylinderPower ?: 0.0).toString()))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(data.axis))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(data.thicknessOnAxis))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(data.thicknessCenter))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(data.thicknessEdge))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(data.thicknessMax))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(data.realBaseCurve))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(data.diameter))
            addView(getDividerHorizontal(dividerColor))
        }
    }

    private fun Context.createNames(
        dividerColor: Int,
        mTextSize: Float = 20f
    ): LinearLayout {
        return LinearLayout(this).apply {
            orientation = VERTICAL
            addView(getTextView(getString(R.string.compare_list_index_of_refraction), mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(getString(R.string.compare_list_sphere_power), mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(getString(R.string.compare_list_cylinder_power), mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(getString(R.string.compare_list_axis), mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(getString(R.string.compare_list_on_axis_thickness), mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(getString(R.string.compare_list_center_thickness), mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(getString(R.string.compare_list_min_edge_thickness), mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(getString(R.string.compare_list_max_edge_thickness), mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(getString(R.string.compare_list_base_curve), mTextSize))
            addView(getDividerHorizontal(dividerColor))
            addView(getTextView(getString(R.string.compare_list_diameter), mTextSize))
            addView(getDividerHorizontal(dividerColor))
        }
    }

    private fun Context.getTextView(text: String?, mTextSize: Float = 24f): TextView {
        return TextView(this, null, R.style.SimpleText).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, dip(44)).apply {
                gravity = Gravity.CENTER
            }
            textSize = mTextSize
            setText(text)
            gravity = Gravity.CENTER
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