package parkhomov.andrew.lensthicknesscalculator.view.thickness

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.databinding.ThicknessBinding
import parkhomov.andrew.lensthicknesscalculator.extencions.*
import parkhomov.andrew.lensthicknesscalculator.view.BaseFragment
import parkhomov.andrew.lensthicknesscalculator.view.result.Result


class Thickness : BaseFragment(R.layout.thickness) {

    private val viewModel: ThicknessViewModel by viewModel()
    private val binding by viewBinding(ThicknessBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFlowListeners()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() {
        binding.sphere.addTextChangedListener(GenericTextWatcher())
        binding.cylinder.addTextChangedListener(GenericTextWatcher())
        val glossaryClickListener = View.OnClickListener {
            val imageId = when (it.id) {
                binding.imageViewInfoSphere.id -> R.drawable.sphere_img
                binding.imageViewInfoCylinder.id -> R.drawable.cylinder_img
                binding.imageViewInfoAxis.id -> R.drawable.axis_img
                binding.imageViewInfoBaseCurve.id -> R.drawable.front_curve_img
                binding.imageViewInfoCenterThickness.id -> R.drawable.thickness_gauge_img
                binding.imageViewInfoEdgeThickness.id -> R.drawable.edge_thickness_img
                binding.imageViewInfoDiameter.id -> R.drawable.diam_img
                else -> R.drawable.index_of_refraction_img
            }
            showGlossaryModal(imageId)
        }
        binding.imageViewInfoRefraction.setOnClickListener(glossaryClickListener)
        binding.imageViewInfoSphere.setOnClickListener(glossaryClickListener)
        binding.imageViewInfoCylinder.setOnClickListener(glossaryClickListener)
        binding.imageViewInfoAxis.setOnClickListener(glossaryClickListener)
        binding.imageViewInfoBaseCurve.setOnClickListener(glossaryClickListener)
        binding.imageViewInfoCenterThickness.setOnClickListener(glossaryClickListener)
        binding.imageViewInfoEdgeThickness.setOnClickListener(glossaryClickListener)
        binding.imageViewInfoDiameter.setOnClickListener(glossaryClickListener)

        val adapter: ArrayAdapter<*> =
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.index_of_refraction,
                R.layout.spinner_item
            )
        adapter.setDropDownViewResource(R.layout.spinner_item)
        binding.spinner.adapter = adapter

        binding.textViewSpinner.setOnClickListener { binding.spinner.performClick() }
        binding.curve.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (binding.wrapperCenterThickness.isEnabled) {
                        binding.wrapperCenterThickness.requestFocus()
                    } else if (binding.wrapperEdgeThickness.isEnabled) {
                        binding.wrapperEdgeThickness.requestFocus()
                    }
                    return@OnEditorActionListener true
                }
                false
            })

        binding.centerThickness.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (binding.wrapperEdgeThickness.isEnabled) {
                        binding.wrapperEdgeThickness.requestFocus()
                    } else {
                        binding.wrapperDiameter.requestFocus()
                    }
                    return@OnEditorActionListener true
                }
                false
            })

        binding.diameter.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onCalculateButtonClicked()
                    return@OnEditorActionListener true
                }
                false
            })
    }

    private fun setFlowListeners() {
        viewModel.showResult.onEach { calculatedData ->
            Result.getInstance(calculatedData).show(childFragmentManager)
        }.collectData(lifecycleScope)
        viewModel.onCalculateClicked.onEach {
            onCalculateButtonClicked()
        }.collectData(lifecycleScope)
        viewModel.errorCenter.onEach(::highlightCenterThickness).collectData(lifecycleScope)
        viewModel.errorDiameter.onEach(::highlightDiameter).collectData(lifecycleScope)
        viewModel.setCurve.onEach(::setCurrentBaseCurve).collectData(lifecycleScope)
    }

    private fun onCalculateButtonClicked() {
        viewModel.onCalculateBtnClicked(
            getLensIndex(),
            binding.sphere.text.toString(),
            binding.cylinder.text.toString(),
            binding.axis.text.toString(),
            binding.curve.text.toString(),
            binding.centerThickness.text.toString(),
            binding.edgeThickness.text.toString(),
            binding.diameter.text.toString()
        )
    }

    private fun getLensIndex(): Triple<Double, Double, String> {
        val spinnerText = binding.spinner.selectedItem.toString()
        return when (binding.spinner.selectedItemPosition) {
            0 -> Triple(INDEX_1498, INDEX_X_1498, spinnerText)
            1 -> Triple(INDEX_1560, INDEX_X_1560, spinnerText)
            2 -> Triple(INDEX_1530, INDEX_X_1530, spinnerText)
            3 -> Triple(INDEX_1590, INDEX_X_1590, spinnerText)
            4 -> Triple(INDEX_1610, INDEX_X_1610, spinnerText)
            5 -> Triple(INDEX_1670, INDEX_X_1670, spinnerText)
            6 -> Triple(INDEX_1740, INDEX_X_1740, spinnerText)
            else -> throw NoSuchElementException("No valid lens index of refraction provided")
        }
    }

    private fun highlightCenterThickness(isShowError: Boolean) {
        if (isShowError) {
            binding.wrapperCenterThickness.error =
                getString(R.string.tab_thkns_provide_center_thickness)
        } else {
            binding.wrapperCenterThickness.error = null
        }
    }

    private fun highlightDiameter(isShowError: Boolean) {
        if (isShowError) {
            binding.wrapperDiameter.error = getString(R.string.tab_thkns_provide_diameter)
        } else {
            binding.wrapperDiameter.error = null
        }
    }

    private fun setCurrentBaseCurve(curveValue: String) {
        binding.curve.setText(curveValue)
    }

    private inner class GenericTextWatcher : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            val spherePower = try {
                binding.sphere.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                null
            }

            val cylinderPower = try {
                binding.cylinder.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                null
            }

            if (spherePower != null) {
                val value = if (cylinderPower != null && cylinderPower > 0)
                    spherePower + cylinderPower
                else
                    spherePower
                binding.wrapperCenterThickness.isEnabled = value <= 0
                binding.centerThickness.isEnabled = value <= 0
                binding.wrapperEdgeThickness.isEnabled = value > 0
                highlightCenterThickness(false)
            } else {
                binding.wrapperCenterThickness.isEnabled = true
                binding.centerThickness.isEnabled = true
                binding.wrapperEdgeThickness.isEnabled = true
                highlightCenterThickness(false)
            }
        }

        override fun afterTextChanged(editable: Editable) {

        }
    }

    companion object {
        // index of material
        const val INDEX_1498 = 1.498
        const val INDEX_1560 = 1.535
        const val INDEX_1530 = 1.53
        const val INDEX_1590 = 1.586
        const val INDEX_1610 = 1.59
        const val INDEX_1670 = 1.66
        const val INDEX_1740 = 1.727

        // index x
        const val INDEX_X_1498 = 1.06425
        const val INDEX_X_1560 = 0.9909
        const val INDEX_X_1530 = 0.998
        const val INDEX_X_1590 = 0.9044
        const val INDEX_X_1610 = 0.8983
        const val INDEX_X_1670 = 0.803
        const val INDEX_X_1740 = 0.729
    }
}
