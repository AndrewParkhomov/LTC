package parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.language

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_language.*
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseDialog

class Language : BaseDialog(),
        LanguageI.View {

    override val presenter: LanguageI.Presenter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_language, container, false)

        presenter.onAttach(this)

        return view
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }


    override fun setUp(view: View) {
        presenter.setRadioButton()

        radio_group.setOnCheckedChangeListener { radioGroup, _ ->
            val radioButton = radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId).id
            when (radioButton) {
                R.id.radio_button_eng -> presenter.saveNewAppLanguage("en")
                R.id.radio_button_rus -> presenter.saveNewAppLanguage("ru")
                R.id.radio_button_ukr -> presenter.saveNewAppLanguage("uk")
            }
            baseActivity!!.recreate()
            dismiss()
        }
    }

    override fun checkRadioButton(radioButtonId: Int) {
        (radio_group.findViewById(radioButtonId) as RadioButton).isChecked = true
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, Language.TAG)

    companion object {
        val TAG: String = Language::class.java.name
        val instance = Language()
    }
}
