package parkhomov.andrew.lensthicknesscalculator.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import kotlinx.android.synthetic.main.diameter.*
import parkhomov.andrew.lensthicknesscalculator.R
import kotlin.math.ceil


class Diameter : BaseFragment(R.layout.diameter) {

    private var ed = 0.0 // effective diameter
    private var dbl = 0.0 // distance between lenses
    private var pd = 0.0 // pupil distance

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTextWatcherListeners()
        setClickListeners()
        calculate()
    }

    private fun setClickListeners() {
        val glossaryClickListener = View.OnClickListener {
            val imageId = when (it.id) {
                image_view_info_ed.id -> R.drawable.ed_img
                image_view_info_dbl.id -> R.drawable.dbl_img
                image_view_info_pd.id -> R.drawable.pd_img
                else -> R.drawable.diam_img
            }
            showGlossaryModal(imageId)
        }
        image_view_info_ed.setOnClickListener(glossaryClickListener)
        image_view_info_dbl.setOnClickListener(glossaryClickListener)
        image_view_info_pd.setOnClickListener(glossaryClickListener)
        image_view_info_diameter.setOnClickListener(glossaryClickListener)
    }

    private fun setTextWatcherListeners() {
        input_edit_text_ed.setTextChangeListener()
        input_edit_text_dbl.setTextChangeListener()
        input_edit_text_pd.setTextChangeListener()
    }

    private fun EditText.setTextChangeListener() {
        doAfterTextChanged {
            val value = it?.toString()?.toDoubleOrNull() ?: 0.0
            when (id) {
                R.id.input_edit_text_ed -> ed = value
                R.id.input_edit_text_dbl -> dbl = value
                R.id.input_edit_text_pd -> pd = value
            }
            calculate()
        }
    }

    private fun updateHints() {
        wrapper_ed.hint = getString(R.string.tab_diameter_hint_ed, ed)
        wrapper_dbl.hint = getString(R.string.tab_diameter_hint_dbl, dbl)
        wrapper_pd.hint = getString(R.string.tab_diameter_hint_pd, pd)
    }

    private fun calculate() {
        val diameter = ceil(ed * 2 + dbl - pd)
        text_view_diameter_result.text = getString(R.string.lens_diameter_result, diameter)
        updateHints()
    }
}