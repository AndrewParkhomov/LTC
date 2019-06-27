package parkhomov.andrew.language.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.language.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.base.base.BaseDialog
import parkhomov.andrew.base.extension.observe
import parkhomov.andrew.language.R
import parkhomov.andrew.language.viewmodel.ViewModelLanguage

class Language : BaseDialog() {

    private val viewModel: ViewModelLanguage by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.language, container, false)
    }

    override fun setUp(view: View) {
        observe(viewModel.getState()) { onStateChanged(it) }
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

    private fun checkRadioButton(radioButtonId: Int) {
        (radio_group.findViewById(radioButtonId) as RadioButton).isChecked = true
    }

    private fun setButtonClickListener() {
        radio_group.setOnCheckedChangeListener { radioGroup, _ ->
            val radioButton = radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId).id
            when (radioButton) {
                R.id.radio_button_eng -> viewModel.setNewLanguage("en")
                R.id.radio_button_rus -> viewModel.setNewLanguage("ru")
                R.id.radio_button_ukr -> viewModel.setNewLanguage("uk")
            }
            baseActivity!!.recreate()
            dismiss()
        }
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, TAG)

    companion object {
        val TAG: String = Language::class.java.name
        val instance = Language()
    }
}
