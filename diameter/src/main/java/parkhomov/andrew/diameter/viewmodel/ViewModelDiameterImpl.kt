package parkhomov.andrew.diameter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import parkhomov.andrew.diameter.R
import kotlin.math.ceil

class ViewModelDiameterImpl(
        private val state: MediatorLiveData<State>
) : ViewModelDiameter() {

    override fun getState(): LiveData<State> = state

    /**
     * ed - effective diameter
     * dbl - distance between lenses
     * pd - pupil distance
     */
    private var ed = 0.0
    private var dbl = 0.0
    private var pd = 0.0

    override fun setSize(size: String?, viewId: Int) {
        val value = try {
            size?.toDouble() ?: 0.0
        } catch (e: NumberFormatException) {
            0.0
        }

        when (viewId) {
            R.id.input_edit_text_ed -> ed = value
            R.id.input_edit_text_dbl -> dbl = value
            R.id.input_edit_text_pd -> pd = value
        }

        state.value = State.SetValue(value, viewId)
        state.value = State.ShowDiameterResult(ceil(ed * 2 + dbl - pd))
    }
}


