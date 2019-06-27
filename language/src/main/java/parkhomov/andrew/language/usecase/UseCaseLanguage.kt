package parkhomov.andrew.language.usecase

import parkhomov.andrew.base.usecase.UseCase
import parkhomov.andrew.language.usecase.UseCaseLanguage.Result

interface UseCaseLanguage : UseCase<Result> {

    sealed class Result {
        data class RadioButtonId(val radioButtonId: Int) : Result()
    }

    fun getRadioButtonId()

    fun setNewLanguage(language: String)
}
