package parkhomov.andrew.ltc.base

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Stable
abstract class AppViewModel<State, V> : ViewModel() {
    abstract val initialState: State

    val uiState: MutableStateFlow<State> = MutableStateFlow(initialState)

    abstract fun uiEvent(event: V)

    fun updateState(block: State.() -> State) {
        uiState.value = uiState.value.block()
    }

    protected fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }
}
