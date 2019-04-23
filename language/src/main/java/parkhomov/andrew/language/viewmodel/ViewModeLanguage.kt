package parkhomov.andrew.language.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class ViewModeLanguage : ViewModel() {

    sealed class State {
        data class LanguageReceived(val radioButtonId: Int) : State()
    }

    abstract fun getState(): LiveData<State>

    abstract fun setRadioButtons()

    abstract fun setNewLanguage(language: String)
}
