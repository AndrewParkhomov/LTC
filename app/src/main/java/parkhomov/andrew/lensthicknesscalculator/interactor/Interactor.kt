package parkhomov.andrew.lensthicknesscalculator.interactor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData

class Interactor {

    private val _fabIcon: MutableSharedFlow<Int> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val fabIcon: SharedFlow<Int> = _fabIcon.asSharedFlow()

    private val _showGlossary: MutableSharedFlow<Int> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val showGlossary: SharedFlow<Int> = _showGlossary.asSharedFlow()

    private val _onFabClicked: MutableSharedFlow<Unit> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val onFabClicked = _onFabClicked.asSharedFlow()

    fun setMainFabIcon(imageId: Int) {
        _fabIcon.tryEmit(imageId)
        _onFabClicked.resetReplayCache()
    }
    fun setGlossaryItemClicked(imageId: Int) = _showGlossary.tryEmit(imageId)
    fun onFabClicked() = _onFabClicked.tryEmit(Unit)

    var calculatedData: CalculatedData? = null
    var compareList: MutableSet<CalculatedData> = mutableSetOf()

}