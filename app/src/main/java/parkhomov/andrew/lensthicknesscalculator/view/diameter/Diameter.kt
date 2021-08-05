package parkhomov.andrew.lensthicknesscalculator.view.diameter

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.diameter.*
import kotlinx.android.synthetic.main.diameter.image_view_info_diameter
import kotlinx.android.synthetic.main.thickness.*
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.utils.shortCollect
import kotlin.math.ceil
import kotlin.random.Random


class Diameter : Fragment(R.layout.diameter) {

    private val viewModel: DiameterViewModel by viewModel()

    private var ed = 0.0 // effective diameter
    private var dbl = 0.0 // distance between lenses
    private var pd = 0.0 // pupil distance

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTextWatcherListeners()
        setClickListeners()
        setFlowListeners()
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
            viewModel.onGlossaryItemClicked(imageId)
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

    private fun setFlowListeners() {
        viewModel.setMainFabIcon(R.drawable.calculate)
        viewModel.onFabClicked.onEach { generateRandomExample() }.shortCollect(lifecycleScope)
    }

    private fun generateRandomExample() {
        ed = Random.nextInt(44, 60).toDouble()
        dbl = Random.nextInt(18, 24).toDouble()
        pd = Random.nextInt(64, 70).toDouble()
        updateEtValues()
    }

    private fun updateEtValues() {
        input_edit_text_ed.setText(getString(R.string.tab_diameter_random, ed))
        input_edit_text_dbl.setText(getString(R.string.tab_diameter_random, dbl))
        input_edit_text_pd.setText(getString(R.string.tab_diameter_random, pd))
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