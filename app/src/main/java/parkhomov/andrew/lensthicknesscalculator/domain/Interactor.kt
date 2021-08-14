package parkhomov.andrew.lensthicknesscalculator.domain

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData

class Interactor {

    var compareList: MutableSet<CalculatedData> = mutableSetOf()

    private val _calculate: MutableSharedFlow<Unit> = MutableSharedFlow()
    val calculate: SharedFlow<Unit> = _calculate.asSharedFlow()

    private val _clear: MutableSharedFlow<Unit> = MutableSharedFlow()
    val clear: SharedFlow<Unit> = _clear.asSharedFlow()

    suspend fun onCalculateClicked() = _calculate.emit(Unit)
    suspend fun onClearClicked() = _clear.emit(Unit)

}