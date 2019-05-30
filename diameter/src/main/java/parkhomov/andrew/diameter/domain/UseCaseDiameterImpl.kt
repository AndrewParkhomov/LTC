package parkhomov.andrew.diameter.domain

import parkhomov.andrew.base.domain.BaseUseCase
import parkhomov.andrew.base.helper.PreferencesHelper
import parkhomov.andrew.diameter.R

class UseCaseDiameterImpl(
        private val preferencesHelper: PreferencesHelper
) : BaseUseCase<UseCaseDiameter.Result>(),
        UseCaseDiameter {

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

        liveData.value = UseCaseDiameter.Result.SetValue(value, viewId)
        liveData.value = UseCaseDiameter.Result.CalculatedDiameter(Math.ceil(ed * 2 + dbl - pd))
    }
}
