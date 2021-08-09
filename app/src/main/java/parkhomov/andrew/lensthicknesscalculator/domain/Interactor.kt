package parkhomov.andrew.lensthicknesscalculator.domain

import kotlinx.coroutines.channels.*
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.*
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData

class Interactor {

    private val _calculate: Channel<Unit> = Channel()
    val calculate: Flow<Unit> = _calculate.receiveAsFlow()

    private val _clear: Channel<Unit> = Channel()
    val clear: Flow<Unit> = _clear.receiveAsFlow()

    fun onCalculateClicked() = _calculate.trySend(Unit)
    fun onClearClicked() = _clear.trySend(Unit)

    var calculatedData: CalculatedData? = null
    var compareList: MutableSet<CalculatedData> = mutableSetOf()

}