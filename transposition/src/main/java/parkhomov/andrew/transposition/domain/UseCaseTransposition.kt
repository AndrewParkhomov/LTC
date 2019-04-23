package parkhomov.andrew.transposition.domain

import parkhomov.andrew.base.domain.UseCase

interface UseCaseTransposition : UseCase<UseCaseTransposition.Result> {

    fun convertToValue(dptrValue: String?, viewId: Int)

    sealed class Result {
        data class SetValue(val value: Double, val viewId: Int) : Result()
        data class CalculatedTransposition(val sphere: Double, val cylinder: Double, val axis: Double) : Result()
        object ClearEditText : Result()
    }

}
