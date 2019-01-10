package parkhomov.andrew.lensthicknesscalculator.ui.fragment.transposition

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.transposition.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.base.base.BaseFragment
import parkhomov.andrew.lensthicknesscalculator.utils.CalculatedTransposition
import parkhomov.andrew.lensthicknesscalculator.utils.ClearEditText
import parkhomov.andrew.lensthicknesscalculator.utils.SetData


class Transposition : BaseFragment() {

    private val viewModelTransposition: TranspositionViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.transposition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setDefaultValues()
        viewModelTransposition.events.observe(this, Observer { event ->
            when (event) {
                is SetData -> {
                    when (event.viewId) {
                        R.id.input_edit_text_sphere -> setHintSphere(event.value)
                        R.id.input_edit_text_cylinder -> setHintCylinder(event.value)
                        R.id.input_edit_text_axis -> setHintAxis(event.value)
                    }
                }
                is CalculatedTransposition -> calculate(event.sphere, event.cylinder, event.axis)
                is ClearEditText -> input_edit_text_axis.text?.clear()
            }
        })

        input_edit_text_sphere.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModelTransposition.convertDistanceToDouble(s?.toString(), R.id.input_edit_text_sphere)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        input_edit_text_cylinder.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModelTransposition.convertDistanceToDouble(s?.toString(), R.id.input_edit_text_cylinder)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        input_edit_text_axis.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModelTransposition.convertDistanceToDouble(s?.toString(), R.id.input_edit_text_axis)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setDefaultValues() {
        setHintSphere(0.0)
        setHintCylinder(0.0)
        setHintAxis(0.0)
        calculate(0.0, 0.0, 0.0)
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