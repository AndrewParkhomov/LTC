package parkhomov.andrew.lensthicknesscalculator.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.databinding.DiameterBinding
import kotlin.math.ceil


class Diameter : BaseFragment(R.layout.diameter) {

    private val binding by viewBinding(DiameterBinding::bind)

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
                binding.imageViewInfoEd.id -> R.drawable.ed_img
                binding.imageViewInfoDbl.id -> R.drawable.dbl_img
                binding.imageViewInfoPd.id -> R.drawable.pd_img
                else -> R.drawable.diam_img
            }
            showGlossaryModal(imageId)
        }
        binding.imageViewInfoEd.setOnClickListener(glossaryClickListener)
        binding.imageViewInfoDbl.setOnClickListener(glossaryClickListener)
        binding.imageViewInfoPd.setOnClickListener(glossaryClickListener)
        binding.imageViewInfoDiameter.setOnClickListener(glossaryClickListener)
    }

    private fun setTextWatcherListeners() {
        binding.inputEditTextEd.setTextChangeListener()
        binding.inputEditTextDbl.setTextChangeListener()
        binding.inputEditTextPd.setTextChangeListener()
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
        binding.wrapperEd.hint = getString(R.string.tab_diameter_hint_ed, ed)
        binding.wrapperDbl.hint = getString(R.string.tab_diameter_hint_dbl, dbl)
        binding.wrapperPd.hint = getString(R.string.tab_diameter_hint_pd, pd)
    }

    private fun calculate() {
        val diameter = ceil(ed * 2 + dbl - pd)
        binding.textViewDiameterResult.text = getString(R.string.lens_diameter_result, diameter)
        updateHints()
    }
}