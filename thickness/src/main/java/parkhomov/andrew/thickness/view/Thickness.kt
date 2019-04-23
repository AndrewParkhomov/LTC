package parkhomov.andrew.thickness.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.thickness_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.base.base.BaseFragment
import parkhomov.andrew.base.data.result.CalculatedData
import parkhomov.andrew.base.utils.*
import parkhomov.andrew.thickness.R
import parkhomov.andrew.thickness.viewmodel.ViewModelThickness

class Thickness : BaseFragment() {

    private val viewModel: ViewModelThickness by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity!!.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return inflater.inflate(R.layout.thickness_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        viewModel.events.observe(this, Observer { event ->
//            when (event) {
//                is ShowResultDialog -> {
//                    event.data?.let { resultData ->
//                        showResultDialog(resultData)
//                    }
//                }
//                is HighlightSpherePower -> highlightSpherePower(event.isHighlight)
//                is HighlightCenterThickness -> highlightCenterThickness(event.isHighlight)
//                is HighlightDiameter -> highlightDiameter(event.isHighlight)
//                is BaseCurve -> setCurrentBaseCurve(event.baseCurve)
//            }
//        })

        // add textwatcher
        sphere_.addTextChangedListener(GenericTextWatcher())
        cylinder_.addTextChangedListener(GenericTextWatcher())

        button_calculate.setOnClickListener {
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
                        button_calculate.performClick()
                        return@OnEditorActionListener true
                    }
                    false
                })
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
            wrapper_sphere.isErrorEnabled = true
            wrapper_sphere.error = getString(R.string.tab_thkns_provide_sphere)
        } else {
            wrapper_sphere.error = null
            wrapper_sphere.isErrorEnabled = false
        }
    }

    private fun highlightCenterThickness(isShowError: Boolean) {
        if (isShowError) {
            wrapper_center_thickness.isErrorEnabled = true
            wrapper_center_thickness.error = getString(R.string.tab_thkns_provide_center_thickness)
        } else {
            wrapper_center_thickness.error = null
            wrapper_center_thickness.isErrorEnabled = false
        }
    }

    private fun highlightDiameter(isShowError: Boolean) {
        if (isShowError) {
            wrapper_diameter.isErrorEnabled = true
            wrapper_diameter.error = getString(R.string.tab_thkns_provide_diameter)
        } else {
            wrapper_diameter.error = null
            wrapper_diameter.isErrorEnabled = false
        }
    }

    private fun setCurrentBaseCurve(curveValue: String) {
        curve_.setText(curveValue)
    }

    private inner class GenericTextWatcher : TextWatcher {

        val enabled = R.style.HintTextAppearanceEnable
        val disabled = R.style.HintTextAppearanceDisable
        val colorTextEnable = activity!!.getColorFromId(R.color.main_text_color)
        val colorEnable = activity!!.getColorFromId(R.color.accent)
        val colorDisable = activity!!.getColorFromId(R.color.gray_400)

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
                center_thickness.setTextColor(if (value <= 0) colorTextEnable else colorDisable)
                edge_thickness.setTextColor(if (value > 0) colorTextEnable else colorDisable)
                wrapper_center_thickness.boxStrokeColor = if (value <= 0) colorEnable else colorDisable
                wrapper_edge_thickness.boxStrokeColor = if (value > 0) colorEnable else colorDisable
                wrapper_center_thickness.setHintTextAppearance(if (value <= 0) enabled else disabled)
                wrapper_edge_thickness.setHintTextAppearance(if (value > 0) enabled else disabled)
            } else {
                wrapper_center_thickness.isEnabled = true
                wrapper_edge_thickness.isEnabled = true
                highlightCenterThickness(false)
                wrapper_center_thickness.boxStrokeColor = colorEnable
                wrapper_edge_thickness.boxStrokeColor = colorEnable
                wrapper_center_thickness.setHintTextAppearance(enabled)
                wrapper_edge_thickness.setHintTextAppearance(enabled)
            }
        }

        override fun afterTextChanged(editable: Editable) {

        }
    }

    private fun showResultDialog(calculatedData: CalculatedData) {
        parkhomov.andrew.result.view.Result.getInstance(calculatedData).show(childFragmentManager)
    }

    fun Context.getColorFromId(resId: Int): Int = ContextCompat.getColor(this, resId)

    companion object {
        val TAG: String = Thickness::class.java.name
        val instance = Thickness()
    }
}
