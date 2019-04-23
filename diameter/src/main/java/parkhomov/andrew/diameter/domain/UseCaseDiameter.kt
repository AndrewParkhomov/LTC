package parkhomov.andrew.diameter.domain

import parkhomov.andrew.base.domain.UseCase

interface UseCaseDiameter : UseCase<parkhomov.andrew.diameter.domain.UseCaseDiameter.Result> {

    sealed class Result {
        data class SetValue(val value: Double, val viewId: Int) : Result()
        data class CalculatedDiameter(val targetValue: Double) : Result()
    }

    /**
     * @param size - some frame value like "56.2" that must be converted to double if possible
     * @param viewId - id one of three edit texts, need to set required value in property and in
     * view after calculation
     */
    fun setSize(size: String?, viewId: Int)

}
