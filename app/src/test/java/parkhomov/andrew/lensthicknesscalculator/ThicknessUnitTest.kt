package parkhomov.andrew.lensthicknesscalculator

import app.cash.turbine.test
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.view.thickness.Thickness
import parkhomov.andrew.lensthicknesscalculator.view.thickness.ThicknessViewModel
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class ThicknessUnitTest {

    private val viewModel = ThicknessViewModel(mockk(relaxed = true))

    @Test
    fun `calculation 1`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.498 CR-39",
            spherePower = 2.0,
            cylinderPower = null,
            axis = null,
            thicknessOnAxis = null,
            thicknessCenter = "2.5",
            thicknessEdge = "0.0",
            thicknessMax = null,
            realBaseCurve = "4.0",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1498, Thickness.INDEX_X_1498, "1.498 CR-39"),
                spherePowerString = "2",
                cylinderPowerString = "",
                axisString = "",
                curveString = "",
                centerThicknessString = "",
                edgeThicknessString = "",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 2`() = runBlockingTest {

        val expected = CalculatedData(
            refractionIndex = "1.498 CR-39",
            spherePower = -2.25,
            cylinderPower = -2.0,
            axis = "92",
            thicknessOnAxis = "7.22",
            thicknessCenter = "2.0",
            thicknessEdge = "4.69",
            thicknessMax = "7.28",
            realBaseCurve = "3.0",
            diameter = "67.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1498, Thickness.INDEX_X_1498, "1.498 CR-39"),
                spherePowerString = "-2.25",
                cylinderPowerString = "-2",
                axisString = "92",
                curveString = "",
                centerThicknessString = "2",
                edgeThicknessString = "",
                diameter = 67.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 3`() = runBlockingTest {

        val expected = CalculatedData(
            refractionIndex = "1.498 CR-39",
            spherePower = 3.5,
            cylinderPower = -1.0,
            axis = "0",
            thicknessOnAxis = "1.0",
            thicknessCenter = "4.22",
            thicknessEdge = "1.0",
            thicknessMax = "1.92",
            realBaseCurve = "6.0",
            diameter = "60.0"
        )
        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1498, Thickness.INDEX_X_1498, "1.498 CR-39"),
                spherePowerString = "3.5",
                cylinderPowerString = "-1",
                axisString = "",
                curveString = "",
                centerThicknessString = "",
                edgeThicknessString = "1",
                diameter = 60.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 4`() = runBlockingTest {

        val expected = CalculatedData(
            refractionIndex = "1.670",
            spherePower = -12.0,
            cylinderPower = null,
            axis = null,
            thicknessOnAxis = null,
            thicknessCenter = "1.4",
            thicknessEdge = "13.04",
            thicknessMax = null,
            realBaseCurve = "2.0",
            diameter = "65.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1670, Thickness.INDEX_X_1670, "1.670"),
                spherePowerString = "-12",
                cylinderPowerString = "",
                axisString = "",
                curveString = "2",
                centerThicknessString = "1.4",
                edgeThicknessString = "",
                diameter = 65.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 5`() = runBlockingTest {

        val expected = CalculatedData(
            refractionIndex = "1.670",
            spherePower = -12.0,
            cylinderPower = null,
            axis = null,
            thicknessOnAxis = null,
            thicknessCenter = "1.4",
            thicknessEdge = "12.02",
            thicknessMax = null,
            realBaseCurve = "0.001",
            diameter = "65.0"
        )
        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1670, Thickness.INDEX_X_1670, "1.670"),
                spherePowerString = "-12",
                cylinderPowerString = "",
                axisString = "",
                curveString = "",
                centerThicknessString = "1.4",
                edgeThicknessString = "",
                diameter = 65.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 6`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.498 CR-39",
            spherePower = 1.0,
            cylinderPower = 5.0,
            axis = "93",
            thicknessOnAxis = "1.22",
            thicknessCenter = "8.71",
            thicknessEdge = "1.0",
            thicknessMax = "7.69",
            realBaseCurve = "8.0",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1498, Thickness.INDEX_X_1498, "1.498 CR-39"),
                spherePowerString = "1",
                cylinderPowerString = "5",
                axisString = "93",
                curveString = "",
                centerThicknessString = "",
                edgeThicknessString = "1",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 7`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.498 CR-39",
            spherePower = 6.0,
            cylinderPower = -5.0,
            axis = "3",
            thicknessOnAxis = "1.22",
            thicknessCenter = "8.71",
            thicknessEdge = "1.0",
            thicknessMax = "7.69",
            realBaseCurve = "8.0",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1498, Thickness.INDEX_X_1498, "1.498 CR-39"),
                spherePowerString = "6",
                cylinderPowerString = "-5",
                axisString = "3",
                curveString = "",
                centerThicknessString = "",
                edgeThicknessString = "1",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 8`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.560",
            spherePower = -3.0,
            cylinderPower = 4.5,
            axis = "0",
            thicknessOnAxis = "6.61",
            thicknessCenter = "2.74",
            thicknessEdge = "1.0",
            thicknessMax = "6.61",
            realBaseCurve = "4.0",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1560, Thickness.INDEX_X_1560, "1.560"),
                spherePowerString = "-3",
                cylinderPowerString = "4.5",
                axisString = "",
                curveString = "",
                centerThicknessString = "",
                edgeThicknessString = "1",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 9`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.560",
            spherePower = 1.5,
            cylinderPower = -4.5,
            axis = "0",
            thicknessOnAxis = "1.0",
            thicknessCenter = "2.74",
            thicknessEdge = "1.0",
            thicknessMax = "6.61",
            realBaseCurve = "4.0",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1560, Thickness.INDEX_X_1560, "1.560"),
                spherePowerString = "1.5",
                cylinderPowerString = "-4.5",
                axisString = "",
                curveString = "",
                centerThicknessString = "",
                edgeThicknessString = "1",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 10`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.560",
            spherePower = 1.5,
            cylinderPower = -0.5,
            axis = "0",
            thicknessOnAxis = "1.0",
            thicknessCenter = "2.74",
            thicknessEdge = "1.0",
            thicknessMax = "1.58",
            realBaseCurve = "4.0",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1560, Thickness.INDEX_X_1560, "1.560"),
                spherePowerString = "1.5",
                cylinderPowerString = "-0.5",
                axisString = "",
                curveString = "",
                centerThicknessString = "",
                edgeThicknessString = "1",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 11`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.560",
            spherePower = -1.75,
            cylinderPower = null,
            axis = null,
            thicknessOnAxis = null,
            thicknessCenter = "2.0",
            thicknessEdge = "4.2",
            thicknessMax = null,
            realBaseCurve = "4.0",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1560, Thickness.INDEX_X_1560, "1.560"),
                spherePowerString = "-1.75",
                cylinderPowerString = "",
                axisString = "",
                curveString = "",
                centerThicknessString = "2.0",
                edgeThicknessString = "",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 12`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.560",
            spherePower = -14.0,
            cylinderPower = 2.0,
            axis = "89",
            thicknessOnAxis = "19.04",
            thicknessCenter = "2.0",
            thicknessEdge = "18.97",
            thicknessMax = "24.89",
            realBaseCurve = "0.001",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1560, Thickness.INDEX_X_1560, "1.560"),
                spherePowerString = "-14.0",
                cylinderPowerString = "2.0",
                axisString = "89",
                curveString = "",
                centerThicknessString = "2.0",
                edgeThicknessString = "",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 13`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.560",
            spherePower = -4.0,
            cylinderPower = -2.0,
            axis = "66",
            thicknessOnAxis = "8.87",
            thicknessCenter = "2.0",
            thicknessEdge = "6.86",
            thicknessMax = "9.6",
            realBaseCurve = "2.0",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1560, Thickness.INDEX_X_1560, "1.560"),
                spherePowerString = "-4.0",
                cylinderPowerString = "-2.0",
                axisString = "66",
                curveString = "",
                centerThicknessString = "2.0",
                edgeThicknessString = "",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 14`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.610",
            spherePower = 10.0,
            cylinderPower = -2.0,
            axis = "45",
            thicknessOnAxis = "2.07",
            thicknessCenter = "12.22",
            thicknessEdge = "1.0",
            thicknessMax = "3.15",
            realBaseCurve = "10.5",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1610, Thickness.INDEX_X_1610, "1.610"),
                spherePowerString = "10.0",
                cylinderPowerString = "-2.0",
                axisString = "45",
                curveString = "",
                centerThicknessString = "",
                edgeThicknessString = "1",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 15`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.610",
            spherePower = -10.0,
            cylinderPower = 4.0,
            axis = "90",
            thicknessOnAxis = "8.43",
            thicknessCenter = "2.0",
            thicknessEdge = "8.43",
            thicknessMax = "13.5",
            realBaseCurve = "0.001",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1610, Thickness.INDEX_X_1610, "1.610"),
                spherePowerString = "-10.0",
                cylinderPowerString = "4.0",
                axisString = "90",
                curveString = "0.001",
                centerThicknessString = "2",
                edgeThicknessString = "",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 16`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.610",
            spherePower = 5.0,
            cylinderPower = -4.0,
            axis = "90",
            thicknessOnAxis = "3.46",
            thicknessCenter = "4.48",
            thicknessEdge = "-1.0",
            thicknessMax = "3.46",
            realBaseCurve = "7.0",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1610, Thickness.INDEX_X_1610, "1.610"),
                spherePowerString = "5",
                cylinderPowerString = "-4.0",
                axisString = "90",
                curveString = "",
                centerThicknessString = "",
                edgeThicknessString = "-1",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 17`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.610",
            spherePower = 0.0,
            cylinderPower = 3.0,
            axis = "90",
            thicknessOnAxis = "1.0",
            thicknessCenter = "4.14",
            thicknessEdge = "1.0",
            thicknessMax = "4.19",
            realBaseCurve = "4.0",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1610, Thickness.INDEX_X_1610, "1.610"),
                spherePowerString = "",
                cylinderPowerString = "3",
                axisString = "90",
                curveString = "4.0",
                centerThicknessString = "",
                edgeThicknessString = "1",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 18`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.610",
            spherePower = 0.0,
            cylinderPower = 3.0,
            axis = "90",
            thicknessOnAxis = "1.0",
            thicknessCenter = "4.23",
            thicknessEdge = "1.0",
            thicknessMax = "4.37",
            realBaseCurve = "6.0",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1610, Thickness.INDEX_X_1610, "1.610"),
                spherePowerString = "",
                cylinderPowerString = "3",
                axisString = "90",
                curveString = "",
                centerThicknessString = "",
                edgeThicknessString = "1",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 19`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.610",
            spherePower = 0.0,
            cylinderPower = -6.0,
            axis = "22",
            thicknessOnAxis = "3.85",
            thicknessCenter = "2.0",
            thicknessEdge = "2.02",
            thicknessMax = "9.49",
            realBaseCurve = "4.0",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1610, Thickness.INDEX_X_1610, "1.610"),
                spherePowerString = "",
                cylinderPowerString = "-6",
                axisString = "22",
                curveString = "",
                centerThicknessString = "2",
                edgeThicknessString = "",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `calculation 20`() = runBlockingTest {
        val expected = CalculatedData(
            refractionIndex = "1.610",
            spherePower = 5.0,
            cylinderPower = -5.0,
            axis = "0",
            thicknessOnAxis = "1.0",
            thicknessCenter = "6.39",
            thicknessEdge = "1.0",
            thicknessMax = "6.7",
            realBaseCurve = "7.0",
            diameter = "70.0"
        )

        viewModel.showResult.test {

            viewModel.onCalculateBtnClicked(
                lensIndex = Triple(Thickness.INDEX_1610, Thickness.INDEX_X_1610, "1.610"),
                spherePowerString = "5",
                cylinderPowerString = "-5",
                axisString = "",
                curveString = "",
                centerThicknessString = "",
                edgeThicknessString = "1",
                diameter = 70.0
            )

            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

}