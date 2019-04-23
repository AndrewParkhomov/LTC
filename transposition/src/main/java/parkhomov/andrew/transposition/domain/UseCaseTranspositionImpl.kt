package parkhomov.andrew.transposition.domain

import parkhomov.andrew.base.domain.BaseUseCase
import parkhomov.andrew.base.helper.PreferencesHelper
import parkhomov.andrew.transposition.R

class UseCaseTranspositionImpl(
        private val preferencesHelper: PreferencesHelper
) : BaseUseCase<UseCaseTransposition.Result>(),
        UseCaseTransposition {

    private var spherePower: Double = 0.0
    private var cylinderPower: Double = 0.0
    private var axis: Double = 0.0

    /**
     * @param dptrValue - dptr value that must be converted to double if possible
     * @param viewId - id one of three edit texts, need to set required value in property and in
     * view after calculation
     */
    override fun convertToValue(dptrValue: String?, viewId: Int) {
        var isAxisCorrect = true
        var value = try {
            dptrValue?.toDouble() ?: 0.0
        } catch (e: NumberFormatException) {
            0.0
        }

        if (viewId == R.id.input_edit_text_axis && value > 180) {
            liveData.value = UseCaseTransposition.Result.ClearEditText
            value = 0.0
            isAxisCorrect = false
        }

        when (viewId) {
            R.id.input_edit_text_sphere -> spherePower = value
            R.id.input_edit_text_cylinder -> cylinderPower = value
            R.id.input_edit_text_axis -> axis = value
        }

        val sphere = try {
            val sphereOriginal = (spherePower + cylinderPower)
            // prevent 0.000000000000002
            if (sphereOriginal.toString().length >= 5) {
                sphereOriginal.toString().substring(0, 5).toDouble()
            } else {
                sphereOriginal
            }
        } catch (e: NumberFormatException) {
            0.0
        }
        val cylinder = try {
            if (cylinderPower == 0.0) {
                0.0
            } else {
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
            0.0
        }

        val valueTo = when (viewId) {
            R.id.input_edit_text_sphere -> sphere
            R.id.input_edit_text_cylinder -> cylinder
            R.id.input_edit_text_axis -> if (isAxisCorrect) axis else 0.0
            else -> throw RuntimeException("No valid view id provided")
        }

        liveData.value = UseCaseTransposition.Result.SetValue(valueTo, viewId)
        liveData.value = UseCaseTransposition.Result.CalculatedTransposition(sphere, cylinder, axis)
    }

}
