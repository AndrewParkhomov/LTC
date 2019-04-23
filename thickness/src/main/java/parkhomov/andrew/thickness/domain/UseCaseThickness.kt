package parkhomov.andrew.thickness.domain

import parkhomov.andrew.base.domain.UseCase

interface UseCaseThickness : UseCase<parkhomov.andrew.thickness.domain.UseCaseThickness.Result> {

    sealed class Result {
        data class RadioButtonId(val radioButtonId: Int) : Result()
    }

}
