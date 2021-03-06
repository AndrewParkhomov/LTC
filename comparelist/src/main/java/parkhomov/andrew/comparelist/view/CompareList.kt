package parkhomov.andrew.comparelist.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
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
        container_main_compare.linearLayout {
            lparams(matchParent, matchParent)
            textView(R.string.compare_list_is_empty) {
                textSize = 26f
                setTypeface(typeface, Typeface.BOLD)
                textColor = context.getColorFromId(R.color.main_text_color)
                gravity = Gravity.CENTER_HORIZONTAL
                setPadding(0, dip(60), 0, 0)
            }.lparams(matchParent, matchParent) {
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

    companion object {
        val TAG: String = CompareList::class.java.name
        val instance = CompareList()
    }
}