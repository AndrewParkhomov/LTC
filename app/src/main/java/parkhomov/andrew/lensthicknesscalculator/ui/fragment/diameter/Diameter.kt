package parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.diameter_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.base.base.BaseFragment
import parkhomov.andrew.lensthicknesscalculator.utils.CalculatedDiameter
import parkhomov.andrew.lensthicknesscalculator.utils.SetData


class Diameter : BaseFragment() {

    private val viewModelDiameter: DiameterViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.diameter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setDefaultValues()
        viewModelDiameter.events.observe(this, Observer { event ->
            when (event) {
                is SetData -> {
                    when (event.viewId) {
                        R.id.input_edit_text_ed -> setHintEd(event.value)
                        R.id.input_edit_text_dbl -> setHintDbl(event.value)
                        R.id.input_edit_text_pd -> setHintPd(event.value)
                    }
                }
                is CalculatedDiameter -> calculate(event.diameter)
            }
        })

        input_edit_text_ed.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModelDiameter.convertDistanceToDouble(s?.toString(), R.id.input_edit_text_ed)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        input_edit_text_dbl.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModelDiameter.convertDistanceToDouble(s?.toString(), R.id.input_edit_text_dbl)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        input_edit_text_pd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModelDiameter.convertDistanceToDouble(s?.toString(), R.id.input_edit_text_pd)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setDefaultValues() {
        setHintEd(0.0)
        setHintDbl(0.0)
        setHintPd(0.0)
        calculate(0.0)
    }

    private fun calculate(diameter: Double) {
        text_view_diameter_result.text = getString(R.string.lens_diameter_result, diameter)
    }

    private fun setHintEd(value: Double) {
        wrapper_ed.hint = getString(R.string.tab_diameter_hint_ed, value)
    }

    private fun setHintDbl(value: Double) {
        wrapper_dbl.hint = getString(R.string.tab_diameter_hint_dbl, value)
    }

    private fun setHintPd(value: Double) {
        wrapper_pd.hint = getString(R.string.tab_diameter_hint_pd, value)
    }

    companion object {
        val TAG: String = Diameter::class.java.name
        val instance = Diameter()
    }
}