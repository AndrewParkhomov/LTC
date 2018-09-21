package parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.diameter_fragment.*
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment
import parkhomov.andrew.lensthicknesscalculator.utils.Utils
import parkhomov.andrew.lensthicknesscalculator.utils.spacing8
import java.text.DecimalFormat


class Diameter : BaseFragment(),
        DiameterI.View {

    override val presenter: DiameterI.Presenter  by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.diameter_fragment, container, false)

        presenter.onAttach(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        diameterCalculateButton.text = Utils.spacing(getString(R.string.button_text_calculate), spacing8)
        diameterCalculateButton.setOnClickListener { presenter.onCalculateButtonClicked() }

    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun onCalculateButtonClicked() {
        var edValue = -1.0
        var dblValue = -1.0
        var pdValue = -1.0
        try {
            edValue = java.lang.Double.valueOf(edET.text.toString())
        } catch (e: NumberFormatException) {
            Utils.highlightEditText(baseActivity!!, edET, edTxtInptL)
        }

        try {
            dblValue = java.lang.Double.valueOf(dblET.text.toString())
        } catch (e: NumberFormatException) {
            Utils.highlightEditText(baseActivity!!, dblET, dblTxtInptL)
        }

        try {
            pdValue = java.lang.Double.valueOf(pdET.text.toString())
        } catch (e: NumberFormatException) {
            Utils.highlightEditText(baseActivity!!, pdET, pdTxtInptL)
        }

        if (edValue != -1.0 && dblValue != -1.0 && pdValue != -1.0) {
            val diam = Math.ceil(edValue * 2 + dblValue - pdValue)
            val df = DecimalFormat("#")
            val result = String.format("%s%s %s",
                    resources.getString(R.string.diam_activ_textView_result_formula),
                    df.format(diam).toString(),
                    resources.getString(R.string.result_mm))

            textTest.text = result
        }
    }

    companion object {
        val TAG: String = Diameter::class.java.simpleName
        val instance = Diameter()
    }
}