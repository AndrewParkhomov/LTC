package parkhomov.andrew.language.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import parkhomov.andrew.language.domain.FetchMemesUseCase

class ListViewModelImpl(
        private val state: MediatorLiveData<State>,
        private val fetchMemesUseCase: FetchMemesUseCase
) : ListViewModel() {

    init {
        state.addSource(fetchMemesUseCase.getLiveData(), ::onFetchMemesResult)
    }

    override fun onCleared() {
        fetchMemesUseCase.cleanUp()
    }

    override fun getState(): LiveData<State> = state

    override fun setRadioButtons() {
//        state.value = State.ShowLoading
        fetchMemesUseCase.setRadioButtons()
    }

    override fun setNewLanguage(language: String) {
        fetchMemesUseCase.setNewLanguage(language)
    }

    private fun onFetchMemesResult(result: FetchMemesUseCase.Result?) {
        when (result) {
            is FetchMemesUseCase.Result.CheckLanguageRadioButton -> {
                state.value = State.LanguageReceived(result.radioButtonId)
//                state.value = State.ShowContent
            }
//            is FetchMemesUseCase.Result.OnError -> state.value = State.ShowError
        }
    }
}
