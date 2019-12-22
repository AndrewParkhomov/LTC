package parkhomov.andrew.transposition.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.transposition.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.base.base.BaseFragment
import parkhomov.andrew.base.extension.observe
import parkhomov.andrew.transposition.R
import parkhomov.andrew.transposition.viewmodel.ViewModelTransposition


class Transposition : BaseFragment() {

    private val viewModel: ViewModelTransposition by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.transposition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observe(viewModel.state) { onStateChanged(it) }
        setTextWatcherListeners()
        setDefaultValues()
    }

    private fun onStateChanged(event: ViewModelTransposition.State) {
        when (event) {
            is ViewModelTransposition.State.SetValue -> {
                when (event.viewId) {
                    R.id.input_edit_text_sphere -> setHintSphere(event.value)
                    R.id.input_edit_text_cylinder -> setHintCylinder(event.value)
                    R.id.input_edit_text_axis -> setHintAxis(event.value)
                }
            }
            is ViewModelTransposition.State.CalculatedTransposition -> calculate(event.sphere, event.cylinder, event.axis)
            is ViewModelTransposition.State.ClearEditText -> input_edit_text_axis.text?.clear()
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
        input_edit_text_sphere?.post { viewModel.convertToValue(input_edit_text_sphere?.text.toString(), R.id.input_edit_text_sphere) }
        input_edit_text_cylinder?.post { viewModel.convertToValue(input_edit_text_cylinder?.text.toString(), R.id.input_edit_text_cylinder) }
        input_edit_text_axis?.post { viewModel.convertToValue(input_edit_text_axis.text?.toString(), R.id.input_edit_text_axis) }
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

    companion object {
        val TAG: String = Transposition::class.java.name
        val instance = Transposition()
    }
}