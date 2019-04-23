package parkhomov.andrew.language.domain

import parkhomov.andrew.base.domain.UseCase
import parkhomov.andrew.language.domain.UseCaseLanguage.Result

interface UseCaseLanguage : UseCase<Result> {

    sealed class Result {
        data class RadioButtonId(val radioButtonId: Int) : Result()
    }

    fun getRadioButtonId()

    fun setNewLanguage(language: String)
}
