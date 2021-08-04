package parkhomov.andrew.lensthicknesscalculator

import junit.framework.TestCase.assertEquals
import org.junit.Test
import parkhomov.andrew.lensthicknesscalculator.utils.*
import parkhomov.andrew.lensthicknesscalculator.view.Thickness.Companion.INDEX_1498
import parkhomov.andrew.lensthicknesscalculator.view.Thickness.Companion.INDEX_1530
import parkhomov.andrew.lensthicknesscalculator.view.Thickness.Companion.INDEX_1560
import parkhomov.andrew.lensthicknesscalculator.view.Thickness.Companion.INDEX_1610
import parkhomov.andrew.lensthicknesscalculator.view.Thickness.Companion.INDEX_1670
import parkhomov.andrew.lensthicknesscalculator.view.Thickness.Companion.INDEX_1740
import parkhomov.andrew.lensthicknesscalculator.view.Thickness.Companion.INDEX_X_1498
import parkhomov.andrew.lensthicknesscalculator.view.Thickness.Companion.INDEX_X_1530
import parkhomov.andrew.lensthicknesscalculator.view.Thickness.Companion.INDEX_X_1560
import parkhomov.andrew.lensthicknesscalculator.view.Thickness.Companion.INDEX_X_1610
import parkhomov.andrew.lensthicknesscalculator.view.Thickness.Companion.INDEX_X_1670
import parkhomov.andrew.lensthicknesscalculator.view.Thickness.Companion.INDEX_X_1740
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelThicknessImpl
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelThicknessImpl.Companion.LAB_INDEX
import java.util.*


class ThicknessUnitTest {

    @Test
    fun `check base curve picking`() {
        val sphere_1 = 1.0
        val sphere_4_25 = 4.25
        val sphere_9_75 = 9.75
        val sphere_minus_10_25 = -10.25
        val sphere_minus_3_99 = -3.99
        val sphere_zero = 0.0

        val forCurve_1 = handleNoBaseCurveBehaviour(sphere_1)
        val forCurve_4_25 = handleNoBaseCurveBehaviour(sphere_4_25)
        val forCurve_9_75 = handleNoBaseCurveBehaviour(sphere_9_75)
        val forCurve_minus_10_25 = handleNoBaseCurveBehaviour(sphere_minus_10_25)
        val forCurve_minus_3_99 = handleNoBaseCurveBehaviour(sphere_minus_3_99)
        val forCurve_zero = handleNoBaseCurveBehaviour(sphere_zero)

        assertEquals(4.0, forCurve_1)
        assertEquals(6.0, forCurve_4_25)
        assertEquals(10.0, forCurve_9_75)
        assertEquals(0.001, forCurve_minus_10_25)
        assertEquals(3.0, forCurve_minus_3_99)
        assertEquals(4.0, forCurve_zero)

    }

    @Test
    fun `sphere calculation`() {
        calculationLogicSphere(
                INDEX_1498,
                INDEX_X_1498,
                1.0,
                4.0,
                0.0,
                1.0,
                70.0,
                "2.25"
        )

        calculationLogicSphere(
                INDEX_1498,
                INDEX_X_1498,
                -1.0,
                4.0,
                2.0,
                0.0,
                70.0,
                "3.34"
        )

        calculationLogicSphere(
                INDEX_1670,
                INDEX_X_1670,
                -3.25,
                6.0,
                2.0,
                0.0,
                70.0,
                "5.77"
        )

        calculationLogicSphere(
                INDEX_1740,
                INDEX_X_1740,
                -24.0,
                0.003,
                1.0,
                0.0,
                60.0,
                "27.12"
        )

        calculationLogicSphere(
                INDEX_1670,
                INDEX_X_1670,
                6.0,
                2.0,
                0.0,
                1.0,
                70.0,
                "6.58"
        )

        calculationLogicSphere(
                INDEX_1560,
                INDEX_X_1560,
                12.0,
                10.6,
                0.0,
                1.0,
                65.0,
                "13.24"
        )

    }

    @Test
    fun `cylinder calculation`() {
        calculationLogicCylinder(
                INDEX_1498,
                INDEX_X_1498,
                1.5,
                -5.0,
                12,
                4.0,
                0.0,
                1.0,
                70.0,
                "2.87",
                "7.81",
                "1.9"
        )

        calculationLogicCylinder(
                INDEX_1498,
                INDEX_X_1498,
                -1.5,
                -5.0,
                45,
                1.75,
                2.0,
                0.0,
                70.0,
                "3.89",
                "10.98",
                "7.43"
        )

        calculationLogicCylinder(
                INDEX_1560,
                INDEX_X_1560,
                0.0,
                5.0,
                95,
                5.55,
                0.0,
                1.0,
                70.0,
                "6.79",
                "6.97",
                "1.33"
        )

        calculationLogicCylinder(
                INDEX_1610,
                INDEX_X_1610,
                -0.25,
                3.5,
                175,
                2.0,
                0.0,
                1.0,
                70.0,
                "4.37",
                "4.64",
                "4.44"
        )
        calculationLogicCylinder(
                INDEX_1530,
                INDEX_X_1530,
                -5.0,
                2.0,
                108,
                2.0,
                2.0,
                0.0,
                70.0,
                "5.61",
                "8.24",
                "6.14"
        )

        calculationLogicCylinder(
                INDEX_1670,
                INDEX_X_1670,
                2.5,
                -4.75,
                178,
                3.0,
                0.0,
                1.0,
                75.0,
                "3.67",
                "6.25",
                "1.11"
        )

        calculationLogicCylinder(
                INDEX_1498,
                INDEX_X_1498,
                -2.0,
                -7.0,
                15,
                2.0,
                2.0,
                0.0,
                70.0,
                "4.54",
                "15.96",
                "6.45"
        )

        calculationLogicCylinder(
                INDEX_1498,
                INDEX_X_1498,
                8.0,
                -4.0,
                36,
                10.4,
                0.0,
                1.0,
                70.0,
                "11.8",
                "6.36",
                "3.14"
        )

        calculationLogicCylinder(
                INDEX_1610,
                INDEX_X_1610,
                5.0,
                -8.0,
                68,
                2.0,
                0.0,
                1.0,
                70.0,
                "6.19",
                "9.46",
                "7.39"
        )

        calculationLogicCylinder(
                INDEX_1610,
                INDEX_X_1610,
                -5.0,
                -4.0,
                92,
                2.0,
                2.0,
                10.0,
                70.0,
                "7.56",
                "13.03",
                "12.91"
        )
    }

    private fun calculationLogicCylinder(
            index: Double,
            indexX: Double,
            sphere: Double,
            cylinder: Double,
            axis: Int,
            curve: Double,
            center: Double,
            edge: Double,
            diameter: Double,
            expectedMinOrCenter: String,
            expectedMax: String,
            expectedOnAxis: String
    ) {

        var spherePower = sphere
        var cylinderPower = cylinder
        var edgeThickness  = edge
        var centerThickness  = center
        var centerThicknessString = ""
        var edgeThicknessString = ""

        val realRadiusMm = getReaRadiusInMM(curve)
        val recalculatedFrontCurve = getRecalculatedFrontCurve(
                index,
                realRadiusMm
        )
        val sag2Sphere = getSag2Sphere(
                realRadiusMm,
                diameter
        )
        val centerThicknessTemp: Double = when {
            spherePower <= 0 && cylinderPower == 0.0 -> centerThickness
            spherePower <= 0 && cylinderPower > 0 && spherePower == 0.0 ->
                Math.pow(diameter / 2, 2.0) * cylinderPower / (2000 * (index - 1)) + edgeThickness
            spherePower <= 0 && cylinderPower > 0 && spherePower + cylinderPower > 0 ->
                Math.pow(diameter / 2, 2.0) * (spherePower + cylinderPower) / (2000 * (index - 1)) + edgeThickness
            spherePower > 0 -> {
                val tempValue = if (cylinderPower > 0) {
                    spherePower + cylinderPower
                } else
                    spherePower
                Math.pow(diameter / 2, 2.0) * tempValue / (2000 * (index - 1)) + edgeThickness
            }
            else -> centerThickness
        }

        val axis = recalculateAxisInMinusCylinder(
                cylinderPower,
                axis
        )

        if (cylinderPower > 0) {
            spherePower += cylinderPower
            cylinderPower = -cylinderPower
        }

        val recalculatedSphereCurve = getRecalculatedSphereCurve(
                spherePower,
                recalculatedFrontCurve,
                centerThicknessTemp,
                Triple(index, indexX, "fake")
        )
        val realBackRadiusInMM = getRealBackRadiusInMM(recalculatedSphereCurve)
        val recalculatedCylinderCurve = getRecalculatedCylinderCurve(
                recalculatedFrontCurve,
                cylinderPower,
                centerThicknessTemp,
                Triple(index, indexX, "fake"),
                spherePower
        )
        val realCylinderBackRadiusInMM = getRealCylinderBackRadiusInMM(recalculatedCylinderCurve)

        val sag1Sphere = getSag1Sphere(realBackRadiusInMM, diameter)
        val sag2Cylinder = getSag2Cylinder(
                realCylinderBackRadiusInMM,
                diameter
        )

        if (realBackRadiusInMM <= 0) {
            if (spherePower <= 0.0) {
                edgeThickness = sag1Sphere - sag2Sphere + centerThicknessTemp
                edgeThicknessString = ((edgeThickness * 1e2).toLong() / 1e2).toString()
            } else {
                centerThickness = Math.abs(sag1Sphere - sag2Sphere) + edgeThickness
                centerThicknessString = ((centerThickness * 1e2).toLong() / 1e2).toString()
            }
        } else {
            centerThickness = Math.abs(sag1Sphere + sag2Sphere) + edgeThickness
            centerThicknessString = ((centerThickness * 1e2).toLong() / 1e2).toString()
        }

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

        val etOoAxis = ((((maxEdgeThickness - edgeThickness) / 90 * axis + edgeThickness) * 1e2).toLong() / 1e2).toString()
        val maxEt = ((maxEdgeThickness * 1e2).toLong() / 1e2).toString()


        if (spherePower <= 0) { // minus lens
            assertEquals(expectedMinOrCenter, edgeThicknessString)
            assertEquals(expectedMax, maxEt)
            assertEquals(expectedOnAxis, etOoAxis)
        } else { // plus lens
            assertEquals(expectedMinOrCenter, centerThicknessString)
            assertEquals(expectedMax, maxEt)
            assertEquals(expectedOnAxis, etOoAxis)
        }
    }

    private fun calculationLogicSphere(
            index: Double,
            indexX: Double,
            sphere: Double,
            curve: Double,
            center: Double,
            edge: Double,
            diameter: Double,
            expected: String
    ) {
        val cylinder = 0.0
        var centerThickness = ""
        var edgeThickness = ""

        val realRadiusMm = getReaRadiusInMM(curve)
        val recalculatedFrontCurve = getRecalculatedFrontCurve(
                index,
                realRadiusMm
        )
        val sag2Sphere = getSag2Sphere(
                realRadiusMm,
                diameter
        )
        val centerThicknessTemp: Double = when {
            sphere <= 0 && cylinder == 0.0 -> center
            sphere <= 0 && cylinder > 0 && sphere == 0.0 ->
                Math.pow(diameter / 2, 2.0) * cylinder / (2000 * (index - 1)) + edge
            sphere <= 0 && cylinder > 0 && sphere + cylinder > 0 ->
                Math.pow(diameter / 2, 2.0) * (sphere + cylinder) / (2000 * (index - 1)) + edge
            sphere > 0 -> {
                val tempValue = if (cylinder > 0) {
                    sphere + cylinder
                } else
                    sphere
                Math.pow(diameter / 2, 2.0) * tempValue / (2000 * (index - 1)) + edge
            }
            else -> center
        }
        val recalculatedSphereCurve = getRecalculatedSphereCurve(
                sphere,
                recalculatedFrontCurve,
                centerThicknessTemp,
                Triple(index, indexX, "fake")
        )
        val realBackRadiusInMM = getRealBackRadiusInMM(recalculatedSphereCurve)
        val sag1Sphere = getSag1Sphere(realBackRadiusInMM, diameter)

        if (realBackRadiusInMM <= 0) {
            if (sphere <= 0.0) {
                val et = sag1Sphere - sag2Sphere + centerThicknessTemp
                edgeThickness = ((et * 1e2).toLong() / 1e2).toString()
            } else {
                val ct = Math.abs(sag1Sphere - sag2Sphere) + edge
                centerThickness = ((ct * 1e2).toLong() / 1e2).toString()
            }
        } else {
            val ct = Math.abs(sag1Sphere + sag2Sphere) + edge
            centerThickness = ((ct * 1e2).toLong() / 1e2).toString()
        }
        if (sphere <= 0) { // minus lens
            assertEquals(expected, edgeThickness)
        } else { // plus lens
            assertEquals(expected, centerThickness)
        }
    }

    private fun handleNoBaseCurveBehaviour(value: Double): Double {
        val (tempCurveDouble, tempCurveString) = when {
            value <= -8.0 -> Pair(ViewModelThicknessImpl.BASE_0, ViewModelThicknessImpl.BASE_0.toString())
            value in -7.99..-6.0 -> Pair(ViewModelThicknessImpl.BASE_1, ViewModelThicknessImpl.BASE_1.toString())
            value in -5.99..-4.0 -> Pair(ViewModelThicknessImpl.BASE_2, ViewModelThicknessImpl.BASE_2.toString())
            value in -3.99..-2.0 -> Pair(ViewModelThicknessImpl.BASE_3, ViewModelThicknessImpl.BASE_3.toString())
            value in -1.99..2.0 -> Pair(ViewModelThicknessImpl.BASE_4, ViewModelThicknessImpl.BASE_4.toString())
            value in 2.01..2.99 -> Pair(ViewModelThicknessImpl.BASE_5, ViewModelThicknessImpl.BASE_5.toString())
            value in 3.0..4.99 -> Pair(ViewModelThicknessImpl.BASE_6, ViewModelThicknessImpl.BASE_6.toString())
            value in 5.0..5.99 -> Pair(ViewModelThicknessImpl.BASE_7, ViewModelThicknessImpl.BASE_7.toString())
            value in 6.0..6.99 -> Pair(ViewModelThicknessImpl.BASE_8, ViewModelThicknessImpl.BASE_8.toString())
            value in 7.0..7.99 -> Pair(ViewModelThicknessImpl.BASE_9, ViewModelThicknessImpl.BASE_9.toString())
            value in 8.0..9.99 -> Pair(ViewModelThicknessImpl.BASE_10, ViewModelThicknessImpl.BASE_10.toString())
            else -> Pair(ViewModelThicknessImpl.BASE_10_5, ViewModelThicknessImpl.BASE_10_5.toString())  // value >= 10.0
        }
        return tempCurveDouble
    }

    private fun getReaRadiusInMM(curveInDptr: Double): Double =
            (LAB_INDEX - 1) / (curveInDptr / 1000)

    private fun getRecalculatedFrontCurve(lensIndex: Double, realRadiusMm: Double): Double =
            (lensIndex - 1) * 1000 / realRadiusMm

    private fun getRecalculatedCylinderCurve(
            recalculatedFrontCurve: Double,
            cylinder: Double,
            centerThickness: Double,
            lensIndex: Triple<Double, Double, String>,
            sphere: Double
    ): Double {
        return (cylinder - (recalculatedFrontCurve / (1 - centerThickness / lensIndex.first /
                1000.0 * recalculatedFrontCurve) - sphere)) * lensIndex.second
    }

    private fun getRealCylinderBackRadiusInMM(recalculatedCylinderCurve: Double): Double =
            (LAB_INDEX - 1) / (recalculatedCylinderCurve / 1000)

    private fun getSag2Cylinder(realCylinderBackRadiusInMM: Double, diameter: Double): Double =
            Math.abs(realCylinderBackRadiusInMM) -
                    Math.sqrt(Math.pow(Math.abs(realCylinderBackRadiusInMM), 2.0) -
                            Math.pow(diameter / 2, 2.0)) // sag of convex surface;

    private fun getSag2Sphere(realRadiusMm: Double, diameter: Double): Double =
            Math.abs(realRadiusMm - Math.sqrt(Math.pow(realRadiusMm, 2.0) - Math.pow(diameter / 2, 2.0)))    // sag of convex surface;

    private fun recalculateAxisInMinusCylinder(
            cylinder: Double,
            inputAxis: Int
    ): Int {
        var axis = inputAxis
        if (cylinder > 0) {
            when {
                axis + 90 > 180 -> axis = Math.abs(180 - (axis + 90))
                axis > 90 -> axis = 180 - axis
                axis <= 90 -> axis = 180 - (axis + 90)
            }
        } else if (cylinder < 0) {
            if (axis > 90) axis = 180 - axis
        }
        return axis
    }

    private fun getRecalculatedSphereCurve(
            sphere: Double,
            recalculatedFrontCurve: Double,
            centerThickness: Double,
            lensIndex: Triple<Double, Double, String>
    ): Double {
        return (sphere - recalculatedFrontCurve /
                (1 - centerThickness / lensIndex.first / 1000.0 * recalculatedFrontCurve)) * lensIndex.second
    }

    private fun getRealBackRadiusInMM(recalculatedSphereCurve: Double): Double =
            (LAB_INDEX - 1) / (recalculatedSphereCurve / 1000)

    private fun getSag1Sphere(realBackRadiusInMM: Double, diameter: Double): Double =
            Math.abs(realBackRadiusInMM) -
                    Math.sqrt(Math.pow(Math.abs(realBackRadiusInMM), 2.0) - Math.pow(diameter / 2, 2.0))    // sag of concave surface;

}