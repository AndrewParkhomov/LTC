package parkhomov.andrew.lensthicknesscalculator.view.result

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout.*
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.databinding.ResultBinding
import parkhomov.andrew.lensthicknesscalculator.extencions.argument
import parkhomov.andrew.lensthicknesscalculator.extencions.collectData

class Result : DialogFragment(R.layout.result) {

    private val result by argument<CalculatedData>(RESULT)
    private val viewModel: ResultViewModel by viewModel()
    private val binding by viewBinding(ResultBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        showCylinderViews(result.cylinderPower != null)
        setCalculatedData(result)
        viewModel.getCompareList.onEach(::compareSetLoaded).collectData(lifecycleScope)
    }

    private fun compareSetLoaded(set: MutableSet<CalculatedData>) {
        setButtonState(set.contains(result))
        setClickListeners(set)
    }

    private fun setClickListeners(set: MutableSet<CalculatedData>) {
        binding.buttonAddToList.setOnClickListener {
            if (set.contains(result)) {
                val isRemovedSuccessfully = set.remove(result)
                setButtonState(!isRemovedSuccessfully) // already not in list
            } else {
                setButtonState(set.add(result))
            }
        }
        binding.buttonShare.setOnClickListener {
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

    private fun createTextForSharing(data: CalculatedData): String {
        val url = "https://play.google.com/store/apps/details?id=${context?.packageName}"
        return if (data.cylinderPower == null) {
            getString(
                R.string.share_text_only_sphere,
                getString(R.string.app_name),
                url,
                data.refractionIndex,
                data.spherePower.toString(),
                data.thicknessCenter,
                data.thicknessEdge,
                data.realBaseCurve,
                data.diameter
            )
        } else {
            getString(
                R.string.share_text_full,
                getString(R.string.app_name),
                url,
                data.refractionIndex,
                data.spherePower.toString(),
                data.cylinderPower.toString(),
                data.axis,
                data.axis,
                data.thicknessOnAxis,
                data.thicknessCenter,
                data.thicknessEdge,
                data.thicknessMax,
                data.realBaseCurve,
                data.diameter
            )
        }
    }

    private fun setButtonState(isInList: Boolean) {
        val textId = if (isInList)
            R.string.result_remove_from_list
        else
            R.string.result_add_to_list
        binding.buttonAddToList.setText(textId)
    }

    private fun showCylinderViews(isShow: Boolean) {
        binding.viewCylinder.isVisible = isShow
        binding.textViewCylinderPower.isVisible = isShow
        binding.viewAxis.isVisible = isShow
        binding.textViewAxis.isVisible = isShow
        binding.viewThicknessOnAxis.isVisible = isShow
        binding.textViewThicknessOnAxis.isVisible = isShow
        binding.viewMaxThickness.isVisible = isShow
        binding.textViewMaxThickness.isVisible = isShow
    }

    private fun setCalculatedData(data: CalculatedData) {
        binding.textViewIndexOfRefraction.text =
            getString(R.string.result_index_of_refraction, data.refractionIndex)
        binding.textViewSpherePower.text =
            getString(R.string.result_sphere_power, data.spherePower)
        binding.textViewCylinderPower.text =
            getString(R.string.result_cylinder_power, data.cylinderPower)
        binding.textViewAxis.text = getString(R.string.result_axis, data.axis)
        binding.textViewThicknessOnAxis.text = getString(
            R.string.result_on_axis_thickness,
            data.axis,
            data.thicknessOnAxis
        )
        binding.textViewCenterThickness.text =
            getString(R.string.result_center_thickness, data.thicknessCenter)
        binding.textViewMinThickness.text =
            getString(R.string.result_min_edge_thickness, data.thicknessEdge)
        binding.textViewMaxThickness.text =
            getString(R.string.result_max_edge_thickness, data.thicknessMax)
        binding.textViewRealBaseCurve.text =
            getString(R.string.result_base_curve, data.realBaseCurve)
        binding.textViewDiameter.text = getString(R.string.result_diameter, data.diameter)
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
