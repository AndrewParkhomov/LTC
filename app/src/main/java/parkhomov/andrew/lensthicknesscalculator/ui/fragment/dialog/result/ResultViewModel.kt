package parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import parkhomov.andrew.base.data.result.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.mvvm.CoroutinesViewModel
import parkhomov.andrew.lensthicknesscalculator.utils.LensCalculatedData
import parkhomov.andrew.lensthicknesscalculator.utils.ViewModelState

class ResultViewModel : CoroutinesViewModel() {

    private val _events = MutableLiveData<ViewModelState>()
    val events: LiveData<ViewModelState>
        get() = _events

    fun setResult(data: CalculatedData) {
        val refractionIndex = data.refractionIndex
        val spherePower = data.spherePower
        val cylinderPower = data.cylinderPower
        val axis = data.axis
        val thicknessOnAxis = data.thicknessOnAxis
        val thicknessCenter = data.thicknessCenter
        val thicknessEdge = data.thicknessEdge
        val thicknessMax = data.thicknessMax
        val realBaseCurve = data.realBaseCurve
        val diameter = data.diameter

        _events.value = LensCalculatedData(
                refractionIndex = refractionIndex,
                spherePower = spherePower,
                cylinderPower = cylinderPower,
                axis = axis,
                thicknessOnAxis = Pair(axis, thicknessOnAxis),
                thicknessMax = thicknessMax,
                thicknessCenter = thicknessCenter,
                thicknessEdge = thicknessEdge,
                realBaseCurve = realBaseCurve,
                diameter = diameter
        )
    }
}