package parkhomov.andrew.result.view

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.transition.TransitionManager
import com.transitionseverywhere.ChangeText
import kotlinx.android.synthetic.main.dialog_result.*
import parkhomov.andrew.base.base.BaseDialog
import parkhomov.andrew.base.data.result.CalculatedData
import parkhomov.andrew.base.utils.getDrawableFromId
import parkhomov.andrew.result.R

class Result : BaseDialog() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_result, container, false)
    }

    private fun dpToPx(res: Resources, dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.displayMetrics)
    }

    override fun setUp(view: View) {
        val result: CalculatedData = arguments!!.getParcelable(TAG)!!
        showCylinderViews(result.cylinderPower != null)
        setAddToListListener(false)

        setCalculatedData(result)
    }

    private fun setAddToListListener(isAdded: Boolean) {
        val add = getString(R.string.result_add_to_list)
        val remove = getString(R.string.result_remove_from_list)
        val addImage = context?.getDrawableFromId(R.drawable.ic_add_black)
        val removeImage = context?.getDrawableFromId(R.drawable.ic_remove_black)

        text_view_add_to_compare_list.compoundDrawablePadding = dpToPx(resources, 10f).toInt()
        text_view_add_to_compare_list.text = if(!isAdded) add else remove
        image_view_add.setImageDrawable(if (!isAdded) addImage else removeImage)
        text_view_add_to_compare_list.setOnClickListener {
            TransitionManager.beginDelayedTransition(
                    container_constraint,
                    ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_OUT_IN)
            )
            val isAdd = text_view_add_to_compare_list.text == add
            val text = if (isAdd) remove else add
            val image = if (isAdd) removeImage else addImage
            text_view_add_to_compare_list.text = text
            image_view_add.setImageDrawable(image)
        }
    }

    private fun showCylinderViews(isShow: Boolean) {
        view_cylinder.visibility = if (isShow) View.VISIBLE else View.GONE
        text_view_cylinder_power.visibility = if (isShow) View.VISIBLE else View.GONE
        view_axis.visibility = if (isShow) View.VISIBLE else View.GONE
        text_view_axis.visibility = if (isShow) View.VISIBLE else View.GONE
        view_thickness_on_axis.visibility = if (isShow) View.VISIBLE else View.GONE
        text_view_thickness_on_axis.visibility = if (isShow) View.VISIBLE else View.GONE
        view_max_thickness.visibility = if (isShow) View.VISIBLE else View.GONE
        text_view_max_thickness.visibility = if (isShow) View.VISIBLE else View.GONE
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

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, TAG)

    companion object {
        val TAG: String = Result::class.java.name
        fun getInstance(calculatedData: CalculatedData) = Result().apply {
            arguments = Bundle(1).apply {
                putParcelable(TAG, calculatedData)
            }
        }
    }
}
