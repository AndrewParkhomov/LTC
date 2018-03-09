package parkhomov.andrew.lensthicknesscalculator.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.diameter_fragment.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment
import parkhomov.andrew.lensthicknesscalculator.ui.glossary.GlossaryDetails
import parkhomov.andrew.lensthicknesscalculator.utils.Utils
import parkhomov.andrew.lensthicknesscalculator.utils.const
import timber.log.Timber
import java.text.DecimalFormat

/**
 * Created by MyPC on 29.07.2017.
 */

class Diameter : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.diameter_fragment, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        diameterCalculateButton.text = Utils.spacing(getString(R.string.button_text_calculate), const.spacing8)
        diameterCalculateButton.setOnClickListener { onButtonClicked() }
        edImgB.setOnClickListener(onQueryClicked)
        dblImgB.setOnClickListener(onQueryClicked)
        pdImgB.setOnClickListener(onQueryClicked)
    }

    private fun onButtonClicked() {
        setUpViewsBehaviourBefore()
        var edValue = -1.0
        var dblValue = -1.0
        var pdValue = -1.0
        try {
            edValue = java.lang.Double.valueOf(edET.text.toString())!!
        } catch (e: NumberFormatException) {
            Utils.highlightEditText(edET, edTxtInptL)
        }

        try {
            dblValue = java.lang.Double.valueOf(dblET.text.toString())!!
        } catch (e: NumberFormatException) {
            Utils.highlightEditText(dblET, dblTxtInptL)
        }

        try {
            pdValue = java.lang.Double.valueOf(pdET.text.toString())!!
        } catch (e: NumberFormatException) {
            Utils.highlightEditText(pdET, pdTxtInptL)
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
        setUpViewsBehaviourAfter()
    }

    private fun setUpViewsBehaviourAfter() {
        Utils.enableWrapper(edTxtInptL)
        Utils.enableWrapper(dblTxtInptL)
        Utils.enableWrapper(pdTxtInptL)
    }

    private fun setUpViewsBehaviourBefore() {
        Utils.makeNormalEditText(edET, edTxtInptL)
        Utils.makeNormalEditText(dblET, dblTxtInptL)
        Utils.makeNormalEditText(pdET, pdTxtInptL)
        Utils.disableWrapper(edTxtInptL)
        Utils.disableWrapper(dblTxtInptL)
        Utils.disableWrapper(pdTxtInptL)
    }

    private val onQueryClicked = View.OnClickListener { view ->
        var position = -1
        when (view.id) {
            R.id.edImgB -> position = 8
            R.id.dblImgB -> position = 9
            R.id.pdImgB -> position = 10
        }
        if (position != -1) {
            try {
                fragmentManager!!
                        .beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.mainContainerConstr, GlossaryDetails.getInstance(
                                headers!![position],
                                description!![position],
                                images!![position]
                        ), GlossaryDetails.TAG)
                        .commit()
            } catch (e: IllegalStateException) {
                Timber.i(e.toString())
            }
            hideKeyboard()
        }
    }

    companion object {
        val TAG: String = Diameter::class.java.simpleName
        val instance = Diameter()
    }
}