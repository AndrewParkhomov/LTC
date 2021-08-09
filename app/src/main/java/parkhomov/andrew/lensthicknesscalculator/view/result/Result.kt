package parkhomov.andrew.lensthicknesscalculator.view.result

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.result.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.extencions.argument
import parkhomov.andrew.lensthicknesscalculator.extencions.getDrawableFromId

class Result : DialogFragment(R.layout.result) {

    private val viewModel: ResultViewModel by viewModel()
    private val result by argument<CalculatedData>(RESULT)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        showCylinderViews(result.cylinderPower != null)
        setCalculatedData(result)

        text_view_add_to_compare_list.setOnClickListener {
            if (viewModel.checkIsContains(result)) {
                setButtonState(!viewModel.removeFromList(result))
            } else {
                setButtonState(viewModel.addToList(result))
            }
        }
        setButtonState(viewModel.checkIsContains(result))
    }

    private fun setButtonState(inList: Boolean) {
        val textId = if (inList) R.string.result_remove_from_list else R.string.result_add_to_list
        val imageId = if (inList) R.drawable.ic_remove_black else R.drawable.ic_add_black
        stateChanged(textId, imageId)
    }

    private fun stateChanged(
        @StringRes textId: Int,
        @DrawableRes imageId: Int
    ) {
        text_view_add_to_compare_list.setText(textId)
        text_view_add_to_compare_list.setImageStart(imageId)
    }

    private fun TextView.setImageStart(@DrawableRes imageId: Int) {
        setCompoundDrawablesWithIntrinsicBounds(
            getDrawableFromId(imageId),
            null,
            null,
            null
        )
    }

    private fun showCylinderViews(isShow: Boolean) {
        view_cylinder.isVisible = isShow
        text_view_cylinder_power.isVisible = isShow
        view_axis.isVisible = isShow
        text_view_axis.isVisible = isShow
        view_thickness_on_axis.isVisible = isShow
        text_view_thickness_on_axis.isVisible = isShow
        view_max_thickness.isVisible = isShow
        text_view_max_thickness.isVisible = isShow
    }

    private fun setCalculatedData(data: CalculatedData) {
        text_view_index_of_refraction.text = getString(R.string.result_index_of_refraction, data.refractionIndex)
        text_view_sphere_power.text = getString(R.string.result_sphere_power, data.spherePower)
        text_view_cylinder_power.text = getString(R.string.result_cylinder_power, data.cylinderPower)
        text_view_axis.text = getString(R.string.result_axis, data.axis)
        text_view_thickness_on_axis.text = getString(
                R.string.result_on_axis_thickness,
                data.axis,
                data.thicknessOnAxis
        )
        text_view_center_thickness.text = getString(R.string.result_center_thickness, data.thicknessCenter)
        text_view_min_thickness.text = getString(R.string.result_min_edge_thickness, data.thicknessEdge)
        text_view_max_thickness.text = getString(R.string.result_max_edge_thickness, data.thicknessMax)
        text_view_real_base_curve.text = getString(R.string.result_base_curve, data.realBaseCurve)
        text_view_diameter.text = getString(R.string.result_diameter, data.diameter)
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, RESULT)

    companion object {
        private const val RESULT = "RESULT"
        fun getInstance(calculatedData: CalculatedData) = Result().apply {
            arguments = Bundle(1).apply {
                putParcelable(RESULT, calculatedData)
            }
        }
    }
}
