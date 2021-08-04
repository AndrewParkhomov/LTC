package parkhomov.andrew.lensthicknesscalculator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.interactor.Interactor
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelThickness
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelThicknessImpl

class ThicknessUnitTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val interactor: Interactor = Interactor()
    private val viewModel: ViewModelThickness = ViewModelThicknessImpl(interactor)

    @Test
    fun `calculation 1`() {

        viewModel.onCalculateBtnClicked(
            lensIndex = Triple(1.498, 1.06425, "1.498 CR-39"),
            spherePowerString = "2",
            cylinderPowerString = "",
            axisString = "",
            curveString = "",
            centerThicknessString = "",
            edgeThicknessString = "",
            diameterString = "70"
        )

        val expected = CalculatedData(
            refractionIndex = "1.498 CR-39",
            spherePower = "2",
            cylinderPower = null,
            axis = null,
            thicknessOnAxis = null,
            thicknessCenter = "2.5",
            thicknessEdge = "0.0",
            thicknessMax = null,
            realBaseCurve = "4.0",
            diameter = "70.0"
        )
        val actual = interactor.calculatedData

        assertEquals(expected, actual)
    }

    @Test
    fun `calculation 2`() {

        viewModel.onCalculateBtnClicked(
            lensIndex = Triple(1.498, 1.06425, "1.498 CR-39"),
            spherePowerString = "-2.25",
            cylinderPowerString = "-2",
            axisString = "92",
            curveString = "",
            centerThicknessString = "2",
            edgeThicknessString = "",
            diameterString = "67"
        )

        val expected = CalculatedData(
            refractionIndex = "1.498 CR-39",
            spherePower = "-2.25",
            cylinderPower = "-2",
            axis = "92",
            thicknessOnAxis = "7.22",
            thicknessCenter = "2.0",
            thicknessEdge = "4.69",
            thicknessMax = "7.28",
            realBaseCurve = "3.0",
            diameter = "67.0"
        )
        val actual = interactor.calculatedData

        assertEquals(expected, actual)
    }

    @Test
    fun `calculation 3`() {

        viewModel.onCalculateBtnClicked(
            lensIndex = Triple(1.498, 1.06425, "1.498 CR-39"),
            spherePowerString = "3.5",
            cylinderPowerString = "-1",
            axisString = "",
            curveString = "",
            centerThicknessString = "",
            edgeThicknessString = "1",
            diameterString = "60"
        )

        val expected = CalculatedData(
            refractionIndex = "1.498 CR-39",
            spherePower = "3.5",
            cylinderPower = "-1",
            axis = "0",
            thicknessOnAxis = "1.0",
            thicknessCenter = "4.22",
            thicknessEdge = "1.0",
            thicknessMax = "1.92",
            realBaseCurve = "6.0",
            diameter = "60.0"
        )
        val actual = interactor.calculatedData

        assertEquals(expected, actual)
    }

    @Test
    fun `calculation 4`() {

        viewModel.onCalculateBtnClicked(
            lensIndex = Triple(1.66, 0.803, "1.670"),
            spherePowerString = "-12",
            cylinderPowerString = "",
            axisString = "",
            curveString = "2",
            centerThicknessString = "1.4",
            edgeThicknessString = "",
            diameterString = "65"
        )

        val expected = CalculatedData(
            refractionIndex = "1.670",
            spherePower = "-12",
            cylinderPower = null,
            axis = null,
            thicknessOnAxis = null,
            thicknessCenter = "1.4",
            thicknessEdge = "13.04",
            thicknessMax = null,
            realBaseCurve = "2.0",
            diameter = "65.0"
        )
        val actual = interactor.calculatedData

        assertEquals(expected, actual)
    }

    @Test
    fun `calculation 5`() {

        viewModel.onCalculateBtnClicked(
            lensIndex = Triple(1.66, 0.803, "1.670"),
            spherePowerString = "-12",
            cylinderPowerString = "",
            axisString = "",
            curveString = "",
            centerThicknessString = "1.4",
            edgeThicknessString = "",
            diameterString = "65"
        )

        val expected = CalculatedData(
            refractionIndex = "1.670",
            spherePower = "-12",
            cylinderPower = null,
            axis = null,
            thicknessOnAxis = null,
            thicknessCenter = "1.4",
            thicknessEdge = "12.02",
            thicknessMax = null,
            realBaseCurve = "0.001",
            diameter = "65.0"
        )
        val actual = interactor.calculatedData

        assertEquals(expected, actual)
    }


}