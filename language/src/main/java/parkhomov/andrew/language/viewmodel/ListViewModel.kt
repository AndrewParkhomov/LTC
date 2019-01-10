package parkhomov.andrew.language.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class ListViewModel : ViewModel() {

    sealed class State {
        data class LanguageReceived(val radioButtonId: Int) : State()
        object ShowLoading : State()
        object ShowContent : State()
        object ShowError : State()
    }

    abstract fun getState(): LiveData<State>

    abstract fun setRadioButtons()

    abstract fun setNewLanguage(language: String)
}
