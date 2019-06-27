package parkhomov.andrew.language.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import parkhomov.andrew.language.usecase.UseCaseLanguage

class ViewModelLanguageImpl(
        private val state: MediatorLiveData<State>,
        private val useCaseLanguage: UseCaseLanguage
) : ViewModelLanguage() {

    init {
        state.addSource(useCaseLanguage.getLiveData(), ::onGetRadioButtonIdResult)
    }

    override fun onCleared() {
        useCaseLanguage.cleanUp()
    }

    override fun getState(): LiveData<State> = state

    override fun setRadioButtons() {
        useCaseLanguage.getRadioButtonId()
    }

    override fun setNewLanguage(language: String) {
        useCaseLanguage.setNewLanguage(language)
    }

    private fun onGetRadioButtonIdResult(result: UseCaseLanguage.Result?) {
        when (result) {
            is UseCaseLanguage.Result.RadioButtonId -> {
                state.value = State.LanguageReceived(result.radioButtonId)
            }
        }
    }
}
