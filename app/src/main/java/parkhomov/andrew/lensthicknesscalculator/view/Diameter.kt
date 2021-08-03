package parkhomov.andrew.lensthicknesscalculator.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.diameter_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.extension.observe
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelDiameter


class Diameter : Fragment(R.layout.diameter_fragment) {

    private val viewModel: ViewModelDiameter by viewModel()

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
        input_edit_text_ed?.post {
            viewModel.setSize(
                input_edit_text_ed?.text.toString(),
                R.id.input_edit_text_ed
            )
        }
        input_edit_text_dbl?.post {
            viewModel.setSize(
                input_edit_text_dbl?.text.toString(),
                R.id.input_edit_text_dbl
            )
        }
        input_edit_text_pd?.post {
            viewModel.setSize(
                input_edit_text_pd?.text.toString(),
                R.id.input_edit_text_pd
            )
        }
    }

    override fun onDestroyView() {
        viewModel.clearEvents()
        super.onDestroyView()
    }

    private fun setTextWatcherListeners() {
        input_edit_text_ed.doAfterTextChanged {
            viewModel.setSize(it?.toString(), R.id.input_edit_text_ed)
        }
        input_edit_text_dbl.doAfterTextChanged {
            viewModel.setSize(it?.toString(), R.id.input_edit_text_dbl)
        }
        input_edit_text_pd.doAfterTextChanged {
            viewModel.setSize(it?.toString(), R.id.input_edit_text_pd)
        }
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