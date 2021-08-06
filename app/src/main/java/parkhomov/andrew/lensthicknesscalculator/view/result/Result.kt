package parkhomov.andrew.lensthicknesscalculator.view.result

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.result.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.extencions.argument
import parkhomov.andrew.lensthicknesscalculator.extencions.getDrawableFromId
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.extencions.dip

class Result : DialogFragment(R.layout.result) {

    private val viewModel: ResultViewModel by viewModel()
    private val result by argument<CalculatedData>(RESULT)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        showCylinderViews(result.cylinderPower != null)
        setCalculatedData(result)

        val add = getString(R.string.result_add_to_list)
        val remove = getString(R.string.result_remove_from_list)
        val addImage = getDrawableFromId(R.drawable.ic_add_black)
        val removeImage = getDrawableFromId(R.drawable.ic_remove_black)
        text_view_add_to_compare_list.setOnClickListener {
            if (text_view_add_to_compare_list.text != getString(R.string.result_add_to_list)) {
                val isSuccess= viewModel.removeFromList(result)
                stateChanged(isSuccess, add, remove, addImage, removeImage)
            } else {
                val isSuccess= viewModel.addToList(result)
                stateChanged(!isSuccess, add, remove, addImage, removeImage)
            }
        }
        val isInList = viewModel.checkIsContains(result)

        text_view_add_to_compare_list.compoundDrawablePadding = requireContext().dip(10)
        text_view_add_to_compare_list.text = if (!isInList) add else remove
        image_view_add.setImageDrawable(if (!isInList) addImage else removeImage)
    }

    private fun stateChanged(
            isAdd: Boolean,
            add: String,
            remove: String,
            addImage: Drawable,
            removeImage: Drawable,
    ) {
        val text = if (isAdd) remove else add
        val image = if (isAdd) removeImage else addImage
        text_view_add_to_compare_list.text = text
        image_view_add.setImageDrawable(image)
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
