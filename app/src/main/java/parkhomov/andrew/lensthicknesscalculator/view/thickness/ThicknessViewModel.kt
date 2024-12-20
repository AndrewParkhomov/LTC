package parkhomov.andrew.lensthicknesscalculator.view.thickness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class ThicknessViewModel : ViewModel() {

    private val _errorCenter: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val errorCenter: SharedFlow<Boolean> = _errorCenter.asSharedFlow()
    private val _setCurve = MutableStateFlow("")
    val setCurve: StateFlow<String> = _setCurve.asStateFlow()
    private val _showResult: MutableSharedFlow<CalculatedData> = MutableSharedFlow()
    val showResult: SharedFlow<CalculatedData> = _showResult.asSharedFlow()

    fun onCalculateBtnClicked(
        lensIndex: Triple<Double, Double, String>,
        spherePowerString: String,
        cylinderPowerString: String,
        axisString: String,
        curveString: String,
        centerThicknessString: String,
        edgeThicknessString: String,
        diameter: Double
    ) = viewModelScope.launch {

        val axisView: String

        var maybeRacalculatedSphere = try {
            spherePowerString.toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }

        var cylinderPower = try {
            cylinderPowerString.toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }

        var axis = (axisString.toIntOrNull() ?: 0).let { axis ->
            if (axis in 0..180) axis else 0
        }

        axisView = axis.toString()

        var curve = try {
            curveString.toDouble()
        } catch (e: NumberFormatException) {
            null
        }

        var centerThickness = try {
            centerThicknessString.toDouble()
        } catch (e: NumberFormatException) {
            null
        }

        var edgeThickness = edgeThicknessString.toDoubleOrNull() ?: 0.0

        // must ba BEFORE cylinderPower possible recalculation
        axis = recalculateAxisInMinusCylinder(cylinderPower, axis)

        if (cylinderPower > 0) {
            maybeRacalculatedSphere += cylinderPower
            cylinderPower = -cylinderPower
        }

        if (maybeRacalculatedSphere <= 0.0) {
            if (centerThickness == null) {
                _errorCenter.emit(true)
                return@launch
            } else {
                _errorCenter.emit(false)
            }
        }

        curve = curve ?: handleNoBaseCurveBehaviour(maybeRacalculatedSphere)
        val realRadiusMm = getReaRadiusInMM(curve)

        centerThickness = when {
            maybeRacalculatedSphere <= 0 && cylinderPower == 0.0 -> centerThickness
            maybeRacalculatedSphere <= 0 && cylinderPower > 0 && maybeRacalculatedSphere == 0.0 ->
                (diameter / 2).pow(2.0) * cylinderPower / (2000 * (lensIndex.first - 1)) + edgeThickness

            maybeRacalculatedSphere <= 0 && cylinderPower > 0 && maybeRacalculatedSphere + cylinderPower > 0 ->
                (diameter / 2).pow(2.0) * (maybeRacalculatedSphere + cylinderPower) / (2000 * (lensIndex.first - 1)) + edgeThickness

            maybeRacalculatedSphere > 0 -> {
                val tempValue = if (cylinderPower > 0) {
                    maybeRacalculatedSphere + cylinderPower
                } else
                    maybeRacalculatedSphere
                (diameter / 2).pow(2.0) * tempValue / (2000 * (lensIndex.first - 1)) + edgeThickness
            }

            else -> centerThickness
        }

        // Find D1
        val recalculatedFrontCurve = getRecalculatedFrontCurve(
            lensIndex.first,
            realRadiusMm
        )

        val recalculatedCylinderCurve = getRecalculatedCylinderCurve(
            recalculatedFrontCurve,
            cylinderPower,
            centerThickness!!,
            lensIndex,
            maybeRacalculatedSphere
        )

        val realCylinderBackRadiusInMM = getRealCylinderBackRadiusInMM(recalculatedCylinderCurve)

        val sag2Sphere = getSag2Sphere(
            realRadiusMm,
            diameter
        )

        // Corrected back curve
        val recalculatedSphereCurve = getRecalculatedSphereCurve(
            maybeRacalculatedSphere,
            recalculatedFrontCurve,
            centerThickness,
            lensIndex
        )

        val realBackRadiusInMM = getRealBackRadiusInMM(recalculatedSphereCurve)
        val sag2Cylinder = getSag2Cylinder(
            realCylinderBackRadiusInMM,
            diameter
        )
        val sag1Sphere = getSag1Sphere(realBackRadiusInMM, diameter)

        var centerString = centerThickness.toString()
        var edgeString = edgeThickness.toString()

        if (realBackRadiusInMM <= 0) {
            if (maybeRacalculatedSphere <= 0.0) {
                edgeThickness = sag1Sphere - sag2Sphere + centerThickness
                edgeString = ((edgeThickness * 1e2).toLong() / 1e2).toString()
            } else {
                centerThickness = abs(sag1Sphere - sag2Sphere) + edgeThickness
                centerString = ((centerThickness * 1e2).toLong() / 1e2).toString()
            }
        } else {
            centerThickness = abs(sag1Sphere + sag2Sphere) + edgeThickness
            centerString = ((centerThickness * 1e2).toLong() / 1e2).toString()
        }

        val calculatedData = if (cylinderPower == 0.0) {
            CalculatedData(
                refractionIndex = lensIndex.third,
                spherePower = spherePowerString.toDoubleOrNull() ?: 0.0,
                cylinderPower = null,
                axis = null,
                thicknessOnAxis = null,
                thicknessCenter = centerString,
                thicknessEdge = edgeString,
                thicknessMax = null,
                realBaseCurve = curve.toString(),
                diameter = diameter.toString()
            )
        } else {

            var maxEdgeThickness = 0.0
            if (maybeRacalculatedSphere <= curve && realBackRadiusInMM < 0) {
                maxEdgeThickness = sag2Cylinder - sag1Sphere + edgeThickness
            } else if (maybeRacalculatedSphere <= curve && realBackRadiusInMM > 0) {
                maxEdgeThickness = sag2Cylinder + sag1Sphere + edgeThickness
            } else if (maybeRacalculatedSphere >= curve && realBackRadiusInMM < 0) {
                maxEdgeThickness = sag2Cylinder - sag1Sphere + edgeThickness
            } else if (maybeRacalculatedSphere >= curve && realCylinderBackRadiusInMM > 0) {
                maxEdgeThickness = sag1Sphere - sag2Cylinder + edgeThickness
            } else if (maybeRacalculatedSphere >= curve && realCylinderBackRadiusInMM < 0) {
                maxEdgeThickness = sag1Sphere + sag2Cylinder + edgeThickness
            }
            val etOnCertainAxis = (maxEdgeThickness - edgeThickness) / 90 * axis + edgeThickness

            CalculatedData(
                refractionIndex = lensIndex.third,
                spherePower = spherePowerString.toDoubleOrNull() ?: 0.0,
                cylinderPower = cylinderPowerString.toDoubleOrNull(),
                axis = axisView,
                thicknessOnAxis = ((etOnCertainAxis * 1e2).toLong() / 1e2).toString(),
                thicknessCenter = centerString,
                thicknessEdge = edgeString,
                thicknessMax = ((maxEdgeThickness * 1e2).toLong() / 1e2).toString(),
                realBaseCurve = curve.toString(),
                diameter = diameter.toString()
            )
        }
        _showResult.emit(calculatedData)
    }

    private suspend fun handleNoBaseCurveBehaviour(value: Double): Double {
        val (tempCurveDouble, tempCurveString) = when {
            value <= -8.0 -> Pair(BASE_0, BASE_0.toString())
            value in -7.99..-6.0 -> Pair(BASE_1, BASE_1.toString())
            value in -5.99..-4.0 -> Pair(BASE_2, BASE_2.toString())
            value in -3.99..-2.0 -> Pair(BASE_3, BASE_3.toString())
            value in -1.99..2.0 -> Pair(BASE_4, BASE_4.toString())
            value in 2.01..2.99 -> Pair(BASE_5, BASE_5.toString())
            value in 3.0..4.99 -> Pair(BASE_6, BASE_6.toString())
            value in 5.0..5.99 -> Pair(BASE_7, BASE_7.toString())
            value in 6.0..6.99 -> Pair(BASE_8, BASE_8.toString())
            value in 7.0..7.99 -> Pair(BASE_9, BASE_9.toString())
            value in 8.0..9.99 -> Pair(BASE_10, BASE_10.toString())
            else -> Pair(BASE_10_5, BASE_10_5.toString())  // value >= 10.0
        }
        _setCurve.emit(tempCurveString)
        return tempCurveDouble
    }

    private fun getReaRadiusInMM(curveInDptr: Double): Double =
        (LAB_INDEX - 1) / (curveInDptr / 1000)

    private fun getRecalculatedFrontCurve(lensIndex: Double, realRadiusMm: Double): Double =
        (lensIndex - 1) * 1000 / realRadiusMm

    private fun getRecalculatedCylinderCurve(
        recalculatedFrontCurve: Double,
        cylinderPower: Double,
        centerThickness: Double,
        lensIndex: Triple<Double, Double, String>,
        spherePower: Double
    ): Double {
        return (cylinderPower - (recalculatedFrontCurve / (1 - centerThickness / lensIndex.first /
                1000.0 * recalculatedFrontCurve) - spherePower)) * lensIndex.second
    }

    private fun getRealCylinderBackRadiusInMM(recalculatedCylinderCurve: Double): Double =
        (LAB_INDEX - 1) / (recalculatedCylinderCurve / 1000)

    private fun getSag2Cylinder(realCylinderBackRadiusInMM: Double, diameter: Double): Double =
        abs(realCylinderBackRadiusInMM) -
                sqrt(
                    abs(realCylinderBackRadiusInMM).pow(2.0) -
                            (diameter / 2).pow(2.0)
                ) // sag of convex surface;

    private fun getSag2Sphere(realRadiusMm: Double, diameter: Double): Double =
        abs(realRadiusMm - sqrt(realRadiusMm.pow(2.0) - (diameter / 2).pow(2.0)))    // sag of convex surface;

    private fun recalculateAxisInMinusCylinder(
        cylinderPower: Double,
        inputAxis: Int
    ): Int {
        var axis = inputAxis
        if (cylinderPower > 0) {
            when {
                axis + 90 > 180 -> axis = abs(180 - (axis + 90))
                axis > 90 -> axis = 180 - axis
                axis <= 90 -> axis = 180 - (axis + 90)
            }
        } else if (cylinderPower < 0) {
            if (axis > 90) axis = 180 - axis
        }
        return axis
    }

    private fun getRecalculatedSphereCurve(
        spherePower: Double,
        recalculatedFrontCurve: Double,
        centerThickness: Double,
        lensIndex: Triple<Double, Double, String>
    ): Double {
        return (spherePower - recalculatedFrontCurve /
                (1 - centerThickness / lensIndex.first / 1000.0 * recalculatedFrontCurve)) * lensIndex.second
    }

    private fun getRealBackRadiusInMM(recalculatedSphereCurve: Double): Double =
        (LAB_INDEX - 1) / (recalculatedSphereCurve / 1000)

    private fun getSag1Sphere(realBackRadiusInMM: Double, diameter: Double): Double {
        return abs(realBackRadiusInMM) - sqrt(
            abs(realBackRadiusInMM).pow(2.0) - (diameter / 2).pow(
                2.0
            )
        )    // sag of concave surface
    }

    companion object {
        const val LAB_INDEX = 1.53  // Constant index 1.53

        const val BASE_0 = 0.001
        const val BASE_1 = 1.0
        const val BASE_2 = 2.0
        const val BASE_3 = 3.0
        const val BASE_4 = 4.0
        const val BASE_5 = 5.0
        const val BASE_6 = 6.0
        const val BASE_7 = 7.0
        const val BASE_8 = 8.0
        const val BASE_9 = 9.0
        const val BASE_10 = 10.0
        const val BASE_10_5 = 10.5
    }

}
