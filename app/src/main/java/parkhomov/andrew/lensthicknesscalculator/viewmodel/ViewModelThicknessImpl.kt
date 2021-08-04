package parkhomov.andrew.lensthicknesscalculator.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.interactor.Interactor
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class ViewModelThicknessImpl(
        private val interactor: Interactor
) : ViewModelThickness() {

    override val state: MutableLiveData<State> = MutableLiveData()

    override fun clearEvents() {
        state.value = null
    }

    override fun onCalculateBtnClicked(
            lensIndex: Triple<Double, Double, String>,
            spherePowerString: String,
            cylinderPowerString: String,
            axisString: String,
            curveString: String,
            centerThicknessString: String,
            edgeThicknessString: String,
            diameterString: String
    ) {
        val axisView: String

        var spherePower = try {
            spherePowerString.toDouble()
        } catch (e: NumberFormatException) {
            null
        }

        var cylinderPower = try {
            cylinderPowerString.toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }

        var axis = try {
            val axis = axisString.toInt()
            if (axis in 0..180) axis else 0
        } catch (e: NumberFormatException) {
            0
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

        var edgeThickness = try {
            edgeThicknessString.toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }

        val diameter = try {
            diameterString.toDouble()
        } catch (e: NumberFormatException) {
            null
        }

        var isContinueCalculation = true

        if (spherePower == null) {
            state.value = State.HighlightSpherePower(true)
            isContinueCalculation = false
        } else {
            state.value = State.HighlightSpherePower(false)

            if (spherePower <= 0.0) {
                isContinueCalculation = if (centerThickness == null) {
                    state.value = State.HighlightCenterThickness(true)
                    false
                } else {
                    state.value = State.HighlightCenterThickness(false)
                    true
                }
            }
        }

        if (diameter == null) {
            state.value = State.HighlightDiameter(true)
            isContinueCalculation = false
        } else {
            state.value = State.HighlightDiameter(false)
        }

        if (isContinueCalculation) {
            curve = curve ?: handleNoBaseCurveBehaviour(spherePower!!)
            val realRadiusMm = getReaRadiusInMM(curve)
            Log.i("realRadiusMm", realRadiusMm.toString())

            centerThickness = when {
                spherePower!! <= 0 && cylinderPower == 0.0 -> centerThickness
                spherePower <= 0 && cylinderPower > 0 && spherePower == 0.0 ->
                    (diameter!! / 2).pow(2.0) * cylinderPower / (2000 * (lensIndex.first - 1)) + edgeThickness
                spherePower <= 0 && cylinderPower > 0 && spherePower + cylinderPower > 0 ->
                    (diameter!! / 2).pow(2.0) * (spherePower + cylinderPower) / (2000 * (lensIndex.first - 1)) + edgeThickness
                spherePower > 0 -> {
                    val tempValue = if (cylinderPower > 0) {
                        spherePower + cylinderPower
                    } else
                        spherePower
                    (diameter!! / 2).pow(2.0) * tempValue / (2000 * (lensIndex.first - 1)) + edgeThickness
                }
                else -> centerThickness
            }
            Log.i("tempCenterThickness", centerThickness.toString())

            axis = recalculateAxisInMinusCylinder(
                    cylinderPower,
                    axis
            )

            if (cylinderPower > 0) {
                spherePower += cylinderPower
                cylinderPower = -cylinderPower
            }


            // Find D1
            val recalculatedFrontCurve = getRecalculatedFrontCurve(
                    lensIndex.first,
                    realRadiusMm
            )
            Log.i("recalculatedFrontCurve", recalculatedFrontCurve.toString())

            val recalculatedCylinderCurve = getRecalculatedCylinderCurve(
                    recalculatedFrontCurve,
                    cylinderPower,
                    centerThickness!!,
                    lensIndex,
                    spherePower
            )
            Log.i("recalculatedCylinderCu", recalculatedCylinderCurve.toString())

            val realCylinderBackRadiusInMM = getRealCylinderBackRadiusInMM(recalculatedCylinderCurve)
            Log.i("realCylinderBackRadMM", realCylinderBackRadiusInMM.toString())

            val sag2Sphere = getSag2Sphere(
                    realRadiusMm,
                    diameter!!
            )
            Log.i("sag2Sphere", sag2Sphere.toString())

            // Corrected back curve
            val recalculatedSphereCurve = getRecalculatedSphereCurve(
                    spherePower,
                    recalculatedFrontCurve,
                    centerThickness,
                    lensIndex
            )
            Log.i("recalculatedSphereCurve", recalculatedSphereCurve.toString())

            val realBackRadiusInMM = getRealBackRadiusInMM(recalculatedSphereCurve)
            Log.i("realBackRadiusInMM", realBackRadiusInMM.toString())
            val sag2Cylinder = getSag2Cylinder(
                    realCylinderBackRadiusInMM,
                    diameter
            )
            Log.i("sag2Cylinder", sag2Cylinder.toString())
            val sag1Sphere = getSag1Sphere(realBackRadiusInMM, diameter)
            Log.i("sag1Sphere", sag1Sphere.toString())

            var centerString = centerThickness.toString()
            var edgeString = edgeThickness.toString()

            if (realBackRadiusInMM <= 0) {
                if (spherePower <= 0.0) {
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

            Log.d("check parameter", spherePower.toString())
            Log.d("check parameter", cylinderPower.toString())
            Log.d("check parameter", axisView)
            Log.d("check parameter", curve.toString())
            Log.d("check parameter", centerThickness.toString())
            Log.d("check parameter", edgeThickness.toString())


            if (cylinderPower == 0.0) {
                showResultDialog(
                        lensIndex.third,
                        spherePowerString,
                        null,
                        null,
                        null,
                        centerString,
                        edgeString,
                        null,
                        curve.toString(),
                        diameter.toString()
                )
            } else {

                var maxEdgeThickness = 0.0
                if (spherePower <= curve && realBackRadiusInMM < 0) {
                    maxEdgeThickness = sag2Cylinder - sag1Sphere + edgeThickness
                } else if (spherePower <= curve && realBackRadiusInMM > 0) {
                    maxEdgeThickness = sag2Cylinder + sag1Sphere + edgeThickness
                } else if (spherePower >= curve && realBackRadiusInMM < 0) {
                    maxEdgeThickness = sag2Cylinder - sag1Sphere + edgeThickness
                } else if (spherePower >= curve && realCylinderBackRadiusInMM > 0) {
                    maxEdgeThickness = sag1Sphere - sag2Cylinder + edgeThickness
                } else if (spherePower >= curve && realCylinderBackRadiusInMM < 0) {
                    maxEdgeThickness = sag1Sphere + sag2Cylinder + edgeThickness
                }
                Log.i("maxEdgeThickness", maxEdgeThickness.toString())
                val etOnCertainAxis = (maxEdgeThickness - edgeThickness) / 90 * axis + edgeThickness

                Log.i("etOnCertainAxis", etOnCertainAxis.toString())
                Log.i("spherePower", spherePower.toString())
                Log.i("cylinderPower", cylinderPower.toString())
                Log.i("axisView", axisView)
                Log.i("(1)", ((etOnCertainAxis * 1e2).toLong() / 1e2).toString())
                Log.i("(2)", ((centerThickness * 1e2).toLong() / 1e2).toString())
                Log.i("(3)", ((edgeThickness * 1e2).toLong() / 1e2).toString())
                Log.i("(4)", ((maxEdgeThickness * 1e2).toLong() / 1e2).toString())
                Log.i("realFrontBaseCurveDptr", curve.toString())

                showResultDialog(
                        lensIndex.third,
                        spherePowerString,
                        cylinderPowerString,
                        axisView,
                        ((etOnCertainAxis * 1e2).toLong() / 1e2).toString(),
                        centerString,
                        edgeString,
                        ((maxEdgeThickness * 1e2).toLong() / 1e2).toString(),
                        curve.toString(),
                        diameter.toString()
                )
            }
        }
    }

    private fun showResultDialog(
            refractionIndex: String,
            spherePower: String,
            cylinderPower: String?,
            axis: String?,
            thicknessOnAxis: String?,
            thicknessCenter: String,
            thicknessEdge: String,
            thicknessMax: String?,
            realBaseCurve: String,
            diameter: String
    ) {
        val calculatedData = CalculatedData(
                refractionIndex = refractionIndex,
                spherePower = spherePower,
                cylinderPower = cylinderPower,
                axis = axis,
                thicknessCenter = thicknessCenter,
                thicknessEdge = thicknessEdge,
                thicknessMax = thicknessMax,
                thicknessOnAxis = thicknessOnAxis,
                realBaseCurve = realBaseCurve,
                diameter = diameter
        )
        interactor.calculatedData = calculatedData
        state.value = State.ShowResultDialog(calculatedData)
    }

    private fun handleNoBaseCurveBehaviour(value: Double): Double {
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
        state.value = State.SetCurrentBaseCurve(tempCurveString)
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
                    sqrt(abs(realCylinderBackRadiusInMM).pow(2.0) -
                            (diameter / 2).pow(2.0)) // sag of convex surface;

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
