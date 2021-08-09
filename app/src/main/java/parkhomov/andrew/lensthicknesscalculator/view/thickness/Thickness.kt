package parkhomov.andrew.lensthicknesscalculator.view.thickness

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity.*
import kotlinx.android.synthetic.main.thickness.*
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.extencions.*
import parkhomov.andrew.lensthicknesscalculator.view.BaseFragment
import parkhomov.andrew.lensthicknesscalculator.view.result.Result


class Thickness : BaseFragment(R.layout.thickness) {

    private val viewModel: ThicknessViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setFlowListeners()
        setClickListeners()

        sphere_.addTextChangedListener(GenericTextWatcher())
        cylinder_.addTextChangedListener(GenericTextWatcher())
        text_view_spinner.setOnClickListener { spinner.performClick() }

        curve_.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (wrapper_center_thickness.isEnabled) {
                        wrapper_center_thickness.requestFocus()
                    } else if (wrapper_edge_thickness.isEnabled) {
                        wrapper_edge_thickness.requestFocus()
                    }
                    return@OnEditorActionListener true
                }
                false
            })

        center_thickness.setOnEditorActionListener(
                TextView.OnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (wrapper_edge_thickness.isEnabled) {
                            wrapper_edge_thickness.requestFocus()
                        } else {
                            wrapper_diameter.requestFocus()
                        }
                        return@OnEditorActionListener true
                    }
                    false
                })

        diameter_.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onCalculateButtonClicked()
                    return@OnEditorActionListener true
                }
                false
            })
    }

    private fun setClickListeners() {
        val glossaryClickListener = View.OnClickListener {
            val imageId = when(it.id){
                image_view_info_sphere.id -> R.drawable.sphere_img
                image_view_info_cylinder.id -> R.drawable.cylinder_img
                image_view_info_axis.id -> R.drawable.axis_img
                image_view_info_base_curve.id -> R.drawable.front_curve_img
                image_view_info_center_thickness.id -> R.drawable.thickness_gauge_img
                image_view_info_edge_thickness.id -> R.drawable.edge_thickness_img
                image_view_info_diameter.id -> R.drawable.diam_img
                else -> R.drawable.index_of_refraction_img
            }
            showGlossaryModal(imageId)
        }
        image_view_info_refraction.setOnClickListener(glossaryClickListener)
        image_view_info_sphere.setOnClickListener(glossaryClickListener)
        image_view_info_cylinder.setOnClickListener(glossaryClickListener)
        image_view_info_axis.setOnClickListener(glossaryClickListener)
        image_view_info_base_curve.setOnClickListener(glossaryClickListener)
        image_view_info_center_thickness.setOnClickListener(glossaryClickListener)
        image_view_info_edge_thickness.setOnClickListener(glossaryClickListener)
        image_view_info_diameter.setOnClickListener(glossaryClickListener)

        val adapter: ArrayAdapter<*> =
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.index_of_refraction,
                R.layout.spinner_item
            )
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinner.adapter = adapter
    }

    private fun setFlowListeners() {
        viewModel.showResult.onEach { calculatedData ->
            Result.getInstance(calculatedData).show(childFragmentManager)
        }.shortCollect(lifecycleScope)
        viewModel.onCalculateClicked.onEach {
            onCalculateButtonClicked()
        }.shortCollect(lifecycleScope)
        viewModel.errorSphere.onEach(::highlightSpherePower).shortCollect(lifecycleScope)
        viewModel.errorCenter.onEach(::highlightCenterThickness).shortCollect(lifecycleScope)
        viewModel.errorDiameter.onEach(::highlightDiameter).shortCollect(lifecycleScope)
        viewModel.setCurve.onEach(::setCurrentBaseCurve).shortCollect(lifecycleScope)
    }

    private fun onCalculateButtonClicked() {
        viewModel.onCalculateBtnClicked(
            getLensIndex(),
            sphere_.text.toString(),
            cylinder_.text.toString(),
            axis_.text.toString(),
            curve_.text.toString(),
            center_thickness.text.toString(),
            edge_thickness.text.toString(),
            diameter_.text.toString()
        )
    }

    private fun getLensIndex(): Triple<Double, Double, String> {
        val spinnerText = spinner.selectedItem.toString()
        return when (spinner.selectedItemPosition) {
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

    private fun highlightSpherePower(isShowError: Boolean) {
        if (isShowError) {
            wrapper_sphere.error = getString(R.string.tab_thkns_provide_sphere)
        } else {
            wrapper_sphere.error = null
        }
    }

    private fun highlightCenterThickness(isShowError: Boolean) {
        if (isShowError) {
            wrapper_center_thickness.error = getString(R.string.tab_thkns_provide_center_thickness)
        } else {
            wrapper_center_thickness.error = null
        }
    }

    private fun highlightDiameter(isShowError: Boolean) {
        if (isShowError) {
            wrapper_diameter.error = getString(R.string.tab_thkns_provide_diameter)
        } else {
            wrapper_diameter.error = null
        }
    }

    private fun setCurrentBaseCurve(curveValue: String) {
        curve_.setText(curveValue)
    }

    private inner class GenericTextWatcher : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            val spherePower = try {
                sphere_.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                null
            }

            val cylinderPower = try {
                cylinder_.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                null
            }

            if (spherePower != null) {
                val value = if (cylinderPower != null && cylinderPower > 0)
                    spherePower + cylinderPower
                else
                    spherePower
                wrapper_center_thickness.isEnabled = value <= 0
                wrapper_edge_thickness.isEnabled = value > 0
                highlightCenterThickness(false)
            } else {
                wrapper_center_thickness.isEnabled = true
                wrapper_edge_thickness.isEnabled = true
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
