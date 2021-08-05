package parkhomov.andrew.lensthicknesscalculator.view.transposition

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.transposition.*
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.utils.shortCollect


class Transposition : Fragment(R.layout.transposition) {

    private val viewModel: TranspositionViewModel by viewModel()

    private var spherePower: Double = 0.0
    private var cylinderPower: Double = 0.0
    private var axis: Double = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.setMainFabIcon(R.drawable.calculate)
        setTextWatcherListeners()
        setClickListeners()
        setFlowListeners()

        convertToValue(spherePower, R.id.input_edit_text_sphere)
        convertToValue(cylinderPower, R.id.input_edit_text_cylinder)
        convertToValue(axis, R.id.input_edit_text_axis)
    }

    private fun setClickListeners() {
        image_view_info_transposition.setOnClickListener {
            viewModel.onGlossaryItemClicked(R.drawable.transposition_img)
        }
    }

    private fun setFlowListeners() {
        viewModel.onFabClicked.onEach { generateRandomExample() }.shortCollect(lifecycleScope)
    }

    private fun generateRandomExample() {

    }

    private fun setTextWatcherListeners() {
        input_edit_text_sphere.setTextChangeListener()
        input_edit_text_cylinder.setTextChangeListener()
        input_edit_text_axis.setTextChangeListener()
    }

    private fun EditText.setTextChangeListener() {
        doAfterTextChanged {
            val value = it?.toString()?.toDoubleOrNull() ?: 0.0
            when (id) {
                R.id.input_edit_text_sphere -> spherePower = value
                R.id.input_edit_text_cylinder -> cylinderPower = value
                R.id.input_edit_text_axis -> axis = value
            }
            convertToValue(value, id)
        }
    }

    private fun convertToValue(originalValue: Double, viewId: Int) {
        var isAxisCorrect = true
        var value = originalValue

        if (viewId == R.id.input_edit_text_axis && value > 180) {
            input_edit_text_axis.text?.clear()
            value = 0.0
            isAxisCorrect = false
        }

        when (viewId) {
            R.id.input_edit_text_sphere -> spherePower = value
            R.id.input_edit_text_cylinder -> cylinderPower = value
            R.id.input_edit_text_axis -> axis = value
        }

        val sphere = try {
            val sphereOriginal = (spherePower + cylinderPower)
            // prevent 0.000000000000002
            // todo replace with string res cut
            if (sphereOriginal.toString().length >= 5) {
                sphereOriginal.toString().substring(0, 5).toDouble()
            } else {
                sphereOriginal
            }
        } catch (e: NumberFormatException) {
            0.0
        }
        val cylinder = try {
            if (cylinderPower == 0.0) {
                0.0
            } else {
                -cylinderPower
            }
        } catch (e: NumberFormatException) {
            0.0
        }
        val axis = try {
            if (axis > 90) {
                kotlin.math.abs(180 - (axis + 90))
            } else {
                axis + 90
            }
        } catch (e: NumberFormatException) {
            0.0
        }

        when (viewId) {
            R.id.input_edit_text_sphere -> setHintSphere(sphere)
            R.id.input_edit_text_cylinder -> setHintCylinder(cylinder)
            R.id.input_edit_text_axis -> setHintAxis(if (isAxisCorrect) value else 0.0)
        }

        text_view_result.text = getString(
            R.string.transposition_result,
            sphere.toString(),
            cylinder.toString(),
            axis.toInt()
        )
    }


    private fun setHintSphere(value: Double) {
        wrapper_sphere.hint = getString(R.string.transposition_power_sphere, value.toString())
    }

    private fun setHintCylinder(value: Double) {
        wrapper_cylinder.hint = getString(R.string.transposition_power_cylinder, value.toString())
    }

    private fun setHintAxis(value: Double) {
        wrapper_axis.hint = getString(R.string.transposition_axis, value.toInt())
    }
}