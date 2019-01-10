package parkhomov.andrew.language.domain

import parkhomov.andrew.base.domain.UseCase
import parkhomov.andrew.language.domain.FetchMemesUseCase.Result

interface FetchMemesUseCase : UseCase<Result> {

    sealed class Result {
        data class CheckLanguageRadioButton(val radioButtonId: Int) : Result()
        object OnError : Result()
    }

    fun setRadioButtons()

    fun setNewLanguage(language: String)
}
