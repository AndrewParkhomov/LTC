package parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.utils.CalculatedDiameter
import parkhomov.andrew.lensthicknesscalculator.mvvm.CoroutinesViewModel
import parkhomov.andrew.lensthicknesscalculator.utils.SetData
import parkhomov.andrew.lensthicknesscalculator.utils.ViewModelState

class DiameterViewModel : CoroutinesViewModel() {

    private val _events = MutableLiveData<ViewModelState>()
    val events: LiveData<ViewModelState>
        get() = _events

    /**
     * ed - effective diameter
     * dbl - distance between lenses
     * pd - pupil distance
     */
    private var ed = 0.0
    private var dbl = 0.0
    private var pd = 0.0

    /**
     * @param originalValue - some frame value like "56.2" that must be converted to double if possible
     * @param viewId - id one of three edit texts, need to set required value in property and in
     * view after calculation
     */
    fun convertDistanceToDouble(originalValue: String?, viewId: Int) {
        val value = try {
            originalValue?.toDouble() ?: 0.0
        } catch (e: NumberFormatException) {
            0.0
        }

        when (viewId) {
            R.id.input_edit_text_ed -> ed = value
            R.id.input_edit_text_dbl -> dbl = value
            R.id.input_edit_text_pd -> pd = value
        }

        _events.value = SetData(value, viewId)
        _events.value = CalculatedDiameter(Math.ceil(ed * 2 + dbl - pd))
    }
}