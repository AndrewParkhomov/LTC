package parkhomov.andrew.lensthicknesscalculator.view.result

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.result.*
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.extencions.argument
import parkhomov.andrew.lensthicknesscalculator.extencions.shortCollect

class Result : DialogFragment(R.layout.result) {

    private val viewModel: ResultViewModel by viewModel()
    private val result by argument<CalculatedData>(RESULT)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showCylinderViews(result.cylinderPower != null)
        setCalculatedData(result)
        viewModel.getCompareList.onEach(::compareSetLoaded).shortCollect(lifecycleScope)
    }

    private fun compareSetLoaded(set: MutableSet<CalculatedData>) {
        setButtonState(set.contains(result))
        setClickListeners(set)
    }

    private fun setClickListeners(set: MutableSet<CalculatedData>) {
        button_add_to_list.setOnClickListener {
            if (set.contains(result)) {
                val isRemovedSuccessfully = set.remove(result)
                setButtonState(!isRemovedSuccessfully) // already not in list
            } else {
                setButtonState(set.add(result))
            }
        }
        button_share.setOnClickListener {
            shareResult(result)
        }
    }

    private fun shareResult(calculatedData: CalculatedData) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, "")
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_result_subject))
            putExtra(Intent.EXTRA_TEXT, createTextForSharing(calculatedData))
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share_with)))
    }

    private fun createTextForSharing(calculatedData: CalculatedData): String {
        val url = "https://play.google.com/store/apps/details?id=${context?.packageName}"
        return if (calculatedData.cylinderPower == null) {
            getString(
                R.string.share_text_only_sphere,
                getString(R.string.app_name),
                url,
                calculatedData.refractionIndex,
                calculatedData.spherePower,
                calculatedData.thicknessCenter,
                calculatedData.thicknessEdge,
                calculatedData.realBaseCurve,
                calculatedData.diameter
            )
        } else {
            getString(
                R.string.share_text_full,
                getString(R.string.app_name),
                url,
                calculatedData.refractionIndex,
                calculatedData.spherePower,
                calculatedData.cylinderPower,
                calculatedData.axis,
                calculatedData.axis,
                calculatedData.thicknessOnAxis,
                calculatedData.thicknessCenter,
                calculatedData.thicknessEdge,
                calculatedData.thicknessMax,
                calculatedData.realBaseCurve,
                calculatedData.diameter
            )
        }
    }

    private fun setButtonState(isInList: Boolean) {
        val textId = if (isInList)
            R.string.result_remove_from_list
        else
            R.string.result_add_to_list
        button_add_to_list.setText(textId)
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
        text_view_index_of_refraction.text =
            getString(R.string.result_index_of_refraction, data.refractionIndex)
        text_view_sphere_power.text = getString(R.string.result_sphere_power, data.spherePower)
        text_view_cylinder_power.text =
            getString(R.string.result_cylinder_power, data.cylinderPower)
        text_view_axis.text = getString(R.string.result_axis, data.axis)
        text_view_thickness_on_axis.text = getString(
            R.string.result_on_axis_thickness,
            data.axis,
            data.thicknessOnAxis
        )
        text_view_center_thickness.text =
            getString(R.string.result_center_thickness, data.thicknessCenter)
        text_view_min_thickness.text =
            getString(R.string.result_min_edge_thickness, data.thicknessEdge)
        text_view_max_thickness.text =
            getString(R.string.result_max_edge_thickness, data.thicknessMax)
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
