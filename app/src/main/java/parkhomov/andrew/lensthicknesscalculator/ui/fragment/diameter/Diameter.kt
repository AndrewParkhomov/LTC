package parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.diameter_fragment.*
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment


class Diameter : BaseFragment(),
        DiameterI.View {

    override val presenter: DiameterI.Presenter  by inject()
    /**
     * ed - effective diameter
     * dbl - distance between lenses
     * pd - pupil distance
     */
    private var ed = 0.0
    private var dbl = 0.0
    private var pd = 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.diameter_fragment, container, false)

        presenter.onAttach(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setHintEd(ed)
        setHintDbl(dbl)
        setHintPd(pd)
        calculate()

        input_edit_text_ed.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ed = try {
                    s?.toString()?.toDouble() ?: 0.0
                } catch (e: NumberFormatException) {
                    0.0
                }
                setHintEd(ed)
                calculate()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        input_edit_text_dbl.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                dbl = try {
                    s?.toString()?.toDouble() ?: 0.0
                } catch (e: NumberFormatException) {
                    0.0
                }
                setHintDbl(dbl)
                calculate()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        input_edit_text_pd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                pd = try {
                    s?.toString()?.toDouble() ?: 0.0
                } catch (e: NumberFormatException) {
                    0.0
                }
                setHintPd(pd)
                calculate()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    private fun calculate() {
        textTest.text = getString(R.string.lens_diameter_result, Math.ceil(ed * 2 + dbl - pd))
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