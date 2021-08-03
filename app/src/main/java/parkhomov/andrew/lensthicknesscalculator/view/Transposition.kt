package parkhomov.andrew.lensthicknesscalculator.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.transposition.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.extension.observe
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelTransposition
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelTransposition.*


class Transposition : Fragment(R.layout.transposition) {

    private val viewModel: ViewModelTransposition by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observe(viewModel.state) { onStateChanged(it) }
        setTextWatcherListeners()
        setDefaultValues()
    }

    private fun onStateChanged(event: State) {
        when (event) {
            is State.SetValue -> {
                when (event.viewId) {
                    R.id.input_edit_text_sphere -> setHintSphere(event.value)
                    R.id.input_edit_text_cylinder -> setHintCylinder(event.value)
                    R.id.input_edit_text_axis -> setHintAxis(event.value)
                }
            }
            is State.CalculatedTransposition -> calculate(event.sphere, event.cylinder, event.axis)
            is State.ClearEditText -> input_edit_text_axis.text?.clear()
        }
    }

    override fun onDestroyView() {
        viewModel.clearEvents()
        super.onDestroyView()
    }

    private fun setTextWatcherListeners() {
        input_edit_text_sphere.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.convertToValue(s?.toString(), R.id.input_edit_text_sphere)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        input_edit_text_cylinder.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.convertToValue(s?.toString(), R.id.input_edit_text_cylinder)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        input_edit_text_axis.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.convertToValue(s?.toString(), R.id.input_edit_text_axis)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setDefaultValues() {
        input_edit_text_sphere?.post { viewModel.convertToValue(
            input_edit_text_sphere?.text.toString(),
            R.id.input_edit_text_sphere
        ) }
        input_edit_text_cylinder?.post { viewModel.convertToValue(
            input_edit_text_cylinder?.text.toString(),
            R.id.input_edit_text_cylinder
        ) }
        input_edit_text_axis?.post { viewModel.convertToValue(
            input_edit_text_axis.text?.toString(),
            R.id.input_edit_text_axis
        ) }
    }

    private fun calculate(sphere: Double, cylinder: Double, axis: Double) {
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