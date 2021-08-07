package parkhomov.andrew.lensthicknesscalculator.domain

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData

class Interactor {

    private val _fabIcon: Channel<Int> = Channel()
    val fabIcon: Flow<Int> = _fabIcon.receiveAsFlow()

    private val _showGlossary: Channel<Int> = Channel()
    val showGlossary: Flow<Int> = _showGlossary.receiveAsFlow()

    private val _onFabClicked: Channel<Unit> = Channel()
    val onFabClicked: Flow<Unit> = _onFabClicked.receiveAsFlow()

    // -1 hide fab else show fab and update image
    fun setMainFabIcon(imageId: Int) = _fabIcon.trySend(imageId)
    fun setGlossaryItemClicked(imageId: Int) = _showGlossary.trySend(imageId)
    fun onFabClicked() = _onFabClicked.trySend(Unit)

    var calculatedData: CalculatedData? = null
    var compareList: MutableSet<CalculatedData> = mutableSetOf()

}