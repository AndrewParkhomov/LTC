package parkhomov.andrew.lensthicknesscalculator.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.diameter_fragment.*
import parkhomov.andrew.lensthicknesscalculator.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment
import parkhomov.andrew.lensthicknesscalculator.extension.observe
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelDiameter


class Diameter : BaseFragment() {

    private val viewModel: ViewModelDiameter by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.diameter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observe(viewModel.state) { onStateChanged(it) }
        setTextWatcherListeners()
        setDefaultValues()
    }

    private fun onStateChanged(event: ViewModelDiameter.State) {
        when (event) {
            is ViewModelDiameter.State.SetValue -> {
                when (event.viewId) {
                    R.id.input_edit_text_ed -> setHintEd(event.value)
                    R.id.input_edit_text_dbl -> setHintDbl(event.value)
                    R.id.input_edit_text_pd -> setHintPd(event.value)
                }
            }
            is ViewModelDiameter.State.ShowDiameterResult -> calculate(event.targetValue)
        }
    }

    private fun setDefaultValues() {
        input_edit_text_ed?.post { viewModel.setSize(input_edit_text_ed?.text.toString(), R.id.input_edit_text_ed) }
        input_edit_text_dbl?.post { viewModel.setSize(input_edit_text_dbl?.text.toString(), R.id.input_edit_text_dbl) }
        input_edit_text_pd?.post { viewModel.setSize(input_edit_text_pd?.text.toString(), R.id.input_edit_text_pd) }
    }

    override fun onDestroyView() {
        viewModel.clearEvents()
        super.onDestroyView()
    }

    private fun setTextWatcherListeners() {
        input_edit_text_ed.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setSize(s?.toString(), R.id.input_edit_text_ed)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        input_edit_text_dbl.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setSize(s?.toString(), R.id.input_edit_text_dbl)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        input_edit_text_pd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setSize(s?.toString(), R.id.input_edit_text_pd)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
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
}