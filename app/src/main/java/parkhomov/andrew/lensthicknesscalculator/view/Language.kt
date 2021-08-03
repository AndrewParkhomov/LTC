package parkhomov.andrew.lensthicknesscalculator.view

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.language.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.extension.observe
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelLanguage

class Language : DialogFragment(R.layout.language) {

    private val viewModel: ViewModelLanguage by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.background_rounded_corners_white)
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
            when (radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId).id) {
                R.id.radio_button_eng -> viewModel.setNewLanguage("en")
                R.id.radio_button_portuguese -> viewModel.setNewLanguage("po")
                R.id.radio_button_rus -> viewModel.setNewLanguage("ru")
                R.id.radio_button_ukr -> viewModel.setNewLanguage("uk")
            }
            activity?.recreate()
            dismiss()
        }
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, TAG)

    companion object {
        val TAG: String = Language::class.java.name
        val instance = Language()
    }
}
