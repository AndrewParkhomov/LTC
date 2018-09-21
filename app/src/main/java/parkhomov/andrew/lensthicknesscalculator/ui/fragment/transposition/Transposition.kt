package parkhomov.andrew.lensthicknesscalculator.ui.fragment.transposition

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.transposition.*
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment


class Transposition : BaseFragment(),
        TranspositionI.View {

    override val presenter: TranspositionI.Presenter  by inject()
    private var spherePower: Double = 0.0
    private var cylinderPower: Double = 0.0
    private var axis: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.transposition, container, false)

        presenter.onAttach(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        wrapper_sphere.hint = getString(R.string.transposition_power_sphere, spherePower)
        wrapper_cylinder.hint = getString(R.string.transposition_power_cylinder, spherePower)
        wrapper_axis.hint = getString(R.string.transposition_axis, spherePower)

        input_edit_text_sphere.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                spherePower = try {
                    s?.toString()?.toDouble() ?: 0.0
                } catch (e: NumberFormatException) {
                    0.0
                }
                wrapper_sphere.hint = getString(R.string.transposition_power_sphere, spherePower)
                calculate()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        input_edit_text_cylinder.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                cylinderPower = try {
                    s?.toString()?.toDouble() ?: 0.0
                } catch (e: NumberFormatException) {
                    0.0
                }
                wrapper_cylinder.hint = getString(R.string.transposition_power_cylinder, cylinderPower)
                calculate()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        input_edit_text_axis.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                axis = try {
                    s?.toString()?.toInt() ?: 0
                } catch (e: NumberFormatException) {
                    0
                }
                if (axis > 180) {
                    input_edit_text_axis.text?.clear()
                    axis = 0
                }
                wrapper_axis.hint = getString(R.string.transposition_axis, axis)
                calculate()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        calculate()
    }

    private fun calculate() {
        val sphere = try {
            spherePower + cylinderPower
        } catch (e: NumberFormatException) {
            0.0
        }
        val cylinder = try {
            if(cylinderPower == 0.0){
                0.0
            }else{
                -cylinderPower
            }
        } catch (e: NumberFormatException) {
            0.0
        }

        val axis = try {
            if (axis > 90) {
                Math.abs(180 - (axis + 90))
            } else {
                axis + 90
            }
        } catch (e: NumberFormatException) {
            0
        }

        text_view_result.text = getString(R.string.transposition_result, sphere, cylinder, axis)
    }


    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }


    companion object {
        val TAG: String = Transposition::class.java.simpleName
        val instance = Transposition()
    }
}