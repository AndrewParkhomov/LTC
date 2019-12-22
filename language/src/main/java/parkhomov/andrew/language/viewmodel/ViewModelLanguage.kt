package parkhomov.andrew.language.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class ViewModelLanguage : ViewModel() {

    abstract val state: LiveData<State>
    abstract fun clearEvents()

    sealed class State {
        data class LanguageReceived(val radioButtonId: Int) : State()
    }

    abstract fun setRadioButtons()

    abstract fun setNewLanguage(language: String)
}
