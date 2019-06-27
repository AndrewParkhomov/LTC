package parkhomov.andrew.transposition.usecase

import parkhomov.andrew.base.usecase.UseCase

interface UseCaseTransposition : UseCase<UseCaseTransposition.Result> {

    fun convertToValue(dptrValue: String?, viewId: Int)

    sealed class Result {
        data class SetValue(val value: Double, val viewId: Int) : Result()
        data class CalculatedTransposition(val sphere: Double, val cylinder: Double, val axis: Double) : Result()
        object ClearEditText : Result()
    }

}
