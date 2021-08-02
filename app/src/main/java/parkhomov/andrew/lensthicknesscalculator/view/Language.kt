package parkhomov.andrew.lensthicknesscalculator.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.language.*
import parkhomov.andrew.lensthicknesscalculator.base.BaseDialog
import parkhomov.andrew.lensthicknesscalculator.extension.observe
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelLanguage

class Language : BaseDialog() {

    private val viewModel: ViewModelLanguage by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.language, container, false)
    }

    override fun setUp(view: View) {
        observe(viewModel.state) { onStateChanged(it) }
        viewModel.setRadioButtons()
    }

    private fun onStateChanged(state: ViewModelLanguage.State) {
        when (state) {
            is ViewModelLanguage.State.LanguageReceived -> {
                checkRadioButton(state.radioButtonId)
                setButtonClickListener()
            }
        }
    }

    override fun onDestroyView() {
        viewModel.clearEvents()
        super.onDestroyView()
    }

    private fun checkRadioButton(radioButtonId: Int) {
        (radio_group.findViewById(radioButtonId) as RadioButton).isChecked = true
    }

    private fun setButtonClickListener() {
        radio_group.setOnCheckedChangeListener { radioGroup, _ ->
            val radioButton = radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId).id
            when (radioButton) {
                R.id.radio_button_eng -> viewModel.setNewLanguage("en")
                R.id.radio_button_portuguese -> viewModel.setNewLanguage("po")
                R.id.radio_button_rus -> viewModel.setNewLanguage("ru")
                R.id.radio_button_ukr -> viewModel.setNewLanguage("uk")
            }
            baseActivity?.recreate()
            dismiss()
        }
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, TAG)

    companion object {
        val TAG: String = Language::class.java.name
        val instance = Language()
    }
}
