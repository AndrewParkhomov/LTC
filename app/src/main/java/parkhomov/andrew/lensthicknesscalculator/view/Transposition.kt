package parkhomov.andrew.lensthicknesscalculator.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.databinding.TranspositionBinding
import kotlin.math.abs


class Transposition : BaseFragment(R.layout.transposition) {

    private val binding by viewBinding(TranspositionBinding::bind)

    private var spherePower: Double = 0.0
    private var cylinderPower: Double = 0.0
    private var axis: Double = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTextWatcherListeners()
        setClickListeners()

        convertToValue(spherePower, R.id.input_edit_text_sphere)
        convertToValue(cylinderPower, R.id.input_edit_text_cylinder)
        convertToValue(axis, R.id.input_edit_text_axis)
    }

    private fun setClickListeners() {
        binding.imageViewInfoTransposition.setOnClickListener {
            showGlossaryModal(R.drawable.transposition_img)
        }
    }

    private fun setTextWatcherListeners() {
        binding.inputEditTextSphere.setTextChangeListener()
        binding.inputEditTextCylinder.setTextChangeListener()
        binding.inputEditTextAxis.setTextChangeListener()
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
        var value = originalValue

        if (viewId == R.id.input_edit_text_axis && value > 180) {
            binding.inputEditTextAxis.text?.clear()
            value = 0.0
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
                abs(180 - (axis + 90))
            } else {
                axis + 90
            }
        } catch (e: NumberFormatException) {
            0.0
        }

        binding.textViewResult.text = getString(
            R.string.transposition_result,
            sphere.toString(),
            cylinder.toString(),
            axis.toInt()
        )
    }
}