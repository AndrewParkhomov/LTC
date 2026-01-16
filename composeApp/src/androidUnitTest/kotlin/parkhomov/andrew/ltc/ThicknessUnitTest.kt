package parkhomov.andrew.ltc

import app.cash.turbine.test
import game.dice.storage.repository.TestSettingsProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.data.LensData
import parkhomov.andrew.ltc.data.RefractiveIndexUiModel
import parkhomov.andrew.ltc.database.TestRefractiveIndexRepository
import parkhomov.andrew.ltc.domain.TestCompareLensStorage
import parkhomov.andrew.ltc.ui.main.MainScreenViewModel
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiEvent

@ExperimentalCoroutinesApi
class ThicknessUnitTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val viewModel: MainScreenViewModel =
        MainScreenViewModel(
            compareLensStorage = TestCompareLensStorage(),
            settingsProvider = TestSettingsProvider(),
            refractiveIndexRepository = TestRefractiveIndexRepository(),
        )

    // Test RefractiveIndexUiModels
    private val cr39 = RefractiveIndexUiModel(id = 1, value = 1.498, label = "1.498 CR-39", isUserCreated = false)
    private val index1560 = RefractiveIndexUiModel(id = 2, value = 1.535, label = "1.560", isUserCreated = false)
    private val index1610 = RefractiveIndexUiModel(id = 5, value = 1.59, label = "1.610", isUserCreated = false)
    private val index1670 = RefractiveIndexUiModel(id = 6, value = 1.66, label = "1.670", isUserCreated = false)

    @Test
    fun `calculation 1`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = cr39,
                    spherePower = 2.0,
                    cylinderPower = null,
                    axis = null,
                    thicknessOnAxis = null,
                    thicknessCenter = "2.5",
                    thicknessEdge = "0.0",
                    thicknessMax = null,
                    realBaseCurve = "4.0",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = cr39,
                    sphere = 2.0,
                    cylinder = null,
                    axis = null,
                    baseCurve = 4.0,
                    centerThickness = 2.5,
                    edgeThickness = null,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 2`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = cr39,
                    spherePower = -2.25,
                    cylinderPower = -2.0,
                    axis = "92",
                    thicknessOnAxis = "7.22",
                    thicknessCenter = "2.0",
                    thicknessEdge = "4.69",
                    thicknessMax = "7.28",
                    realBaseCurve = "3.0",
                    diameter = "67.0",
                )

            val actual =
                LensData(
                    refractiveIndex = cr39,
                    sphere = -2.25,
                    cylinder = -2.0,
                    axis = 92,
                    baseCurve = 3.0,
                    centerThickness = 2.0,
                    edgeThickness = null,
                    diameter = 67.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 3`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = cr39,
                    spherePower = 3.5,
                    cylinderPower = -1.0,
                    axis = "0",
                    thicknessOnAxis = "1.0",
                    thicknessCenter = "4.22",
                    thicknessEdge = "1.0",
                    thicknessMax = "1.92",
                    realBaseCurve = "6.0",
                    diameter = "60.0",
                )

            val actual =
                LensData(
                    refractiveIndex = cr39,
                    sphere = 3.5,
                    cylinder = -1.0,
                    axis = 0,
                    baseCurve = 6.0,
                    centerThickness = null,
                    edgeThickness = 1.0,
                    diameter = 60.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 4`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1670,
                    spherePower = -12.0,
                    cylinderPower = null,
                    axis = null,
                    thicknessOnAxis = null,
                    thicknessCenter = "1.4",
                    thicknessEdge = "13.04",
                    thicknessMax = null,
                    realBaseCurve = "2.0",
                    diameter = "65.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1670,
                    sphere = -12.0,
                    cylinder = null,
                    axis = null,
                    baseCurve = 2.0,
                    centerThickness = 1.4,
                    edgeThickness = null,
                    diameter = 65.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 5`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1670,
                    spherePower = -12.0,
                    cylinderPower = null,
                    axis = null,
                    thicknessOnAxis = null,
                    thicknessCenter = "1.4",
                    thicknessEdge = "12.02",
                    thicknessMax = null,
                    realBaseCurve = "0.001",
                    diameter = "65.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1670,
                    sphere = -12.0,
                    cylinder = null,
                    axis = null,
                    baseCurve = 0.001,
                    centerThickness = 1.4,
                    edgeThickness = null,
                    diameter = 65.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 6`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = cr39,
                    spherePower = 1.0,
                    cylinderPower = 5.0,
                    axis = "93",
                    thicknessOnAxis = "1.22",
                    thicknessCenter = "8.71",
                    thicknessEdge = "1.0",
                    thicknessMax = "7.69",
                    realBaseCurve = "8.0",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = cr39,
                    sphere = 1.0,
                    cylinder = 5.0,
                    axis = 93,
                    baseCurve = 8.0,
                    centerThickness = null,
                    edgeThickness = 1.0,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 7`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = cr39,
                    spherePower = 6.0,
                    cylinderPower = -5.0,
                    axis = "3",
                    thicknessOnAxis = "1.22",
                    thicknessCenter = "8.71",
                    thicknessEdge = "1.0",
                    thicknessMax = "7.69",
                    realBaseCurve = "8.0",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = cr39,
                    sphere = 6.0,
                    cylinder = -5.0,
                    axis = 3,
                    baseCurve = 8.0,
                    centerThickness = null,
                    edgeThickness = 1.0,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 8`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1560,
                    spherePower = -3.0,
                    cylinderPower = 4.5,
                    axis = "0",
                    thicknessOnAxis = "6.61",
                    thicknessCenter = "2.74",
                    thicknessEdge = "1.0",
                    thicknessMax = "6.61",
                    realBaseCurve = "4.0",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1560,
                    sphere = -3.0,
                    cylinder = 4.5,
                    axis = 0,
                    baseCurve = 4.0,
                    centerThickness = null,
                    edgeThickness = 1.0,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 9`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1560,
                    spherePower = 1.5,
                    cylinderPower = -4.5,
                    axis = "0",
                    thicknessOnAxis = "1.0",
                    thicknessCenter = "2.74",
                    thicknessEdge = "1.0",
                    thicknessMax = "6.61",
                    realBaseCurve = "4.0",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1560,
                    sphere = 1.5,
                    cylinder = -4.5,
                    axis = 0,
                    baseCurve = 4.0,
                    centerThickness = null,
                    edgeThickness = 1.0,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 10`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1560,
                    spherePower = 1.5,
                    cylinderPower = -0.5,
                    axis = "0",
                    thicknessOnAxis = "1.0",
                    thicknessCenter = "2.74",
                    thicknessEdge = "1.0",
                    thicknessMax = "1.58",
                    realBaseCurve = "4.0",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1560,
                    sphere = 1.5,
                    cylinder = -0.5,
                    axis = 0,
                    baseCurve = 4.0,
                    centerThickness = null,
                    edgeThickness = 1.0,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 11`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1560,
                    spherePower = -1.75,
                    cylinderPower = null,
                    axis = null,
                    thicknessOnAxis = null,
                    thicknessCenter = "2.0",
                    thicknessEdge = "4.2",
                    thicknessMax = null,
                    realBaseCurve = "4.0",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1560,
                    sphere = -1.75,
                    cylinder = null,
                    axis = null,
                    baseCurve = 4.0,
                    centerThickness = 2.0,
                    edgeThickness = null,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 12`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1560,
                    spherePower = -14.0,
                    cylinderPower = 2.0,
                    axis = "89",
                    thicknessOnAxis = "19.03",
                    thicknessCenter = "2.0",
                    thicknessEdge = "18.96",
                    thicknessMax = "24.87",
                    realBaseCurve = "0.001",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1560,
                    sphere = -14.0,
                    cylinder = 2.0,
                    axis = 89,
                    baseCurve = 0.001,
                    centerThickness = 2.0,
                    edgeThickness = null,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 13`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1560,
                    spherePower = -4.0,
                    cylinderPower = -2.0,
                    axis = "66",
                    thicknessOnAxis = "8.87",
                    thicknessCenter = "2.0",
                    thicknessEdge = "6.86",
                    thicknessMax = "9.6",
                    realBaseCurve = "2.0",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1560,
                    sphere = -4.0,
                    cylinder = -2.0,
                    axis = 66,
                    baseCurve = 2.0,
                    centerThickness = 2.0,
                    edgeThickness = null,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 14`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1610,
                    spherePower = 10.0,
                    cylinderPower = -2.0,
                    axis = "45",
                    thicknessOnAxis = "2.07",
                    thicknessCenter = "12.22",
                    thicknessEdge = "1.0",
                    thicknessMax = "3.15",
                    realBaseCurve = "10.5",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1610,
                    sphere = 10.0,
                    cylinder = -2.0,
                    axis = 45,
                    baseCurve = 10.5,
                    centerThickness = null,
                    edgeThickness = 1.0,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 15`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1610,
                    spherePower = -10.0,
                    cylinderPower = 4.0,
                    axis = "90",
                    thicknessOnAxis = "8.43",
                    thicknessCenter = "2.0",
                    thicknessEdge = "8.43",
                    thicknessMax = "13.5",
                    realBaseCurve = "0.001",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1610,
                    sphere = -10.0,
                    cylinder = 4.0,
                    axis = 90,
                    baseCurve = 0.001,
                    centerThickness = 2.0,
                    edgeThickness = null,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 16`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1610,
                    spherePower = 5.0,
                    cylinderPower = -4.0,
                    axis = "90",
                    thicknessOnAxis = "3.46",
                    thicknessCenter = "4.48",
                    thicknessEdge = "-1.0",
                    thicknessMax = "3.46",
                    realBaseCurve = "7.0",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1610,
                    sphere = 5.0,
                    cylinder = -4.0,
                    axis = 90,
                    baseCurve = 7.0,
                    centerThickness = null,
                    edgeThickness = -1.0,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 17`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1610,
                    spherePower = 0.0,
                    cylinderPower = 3.0,
                    axis = "90",
                    thicknessOnAxis = "1.0",
                    thicknessCenter = "4.14",
                    thicknessEdge = "1.0",
                    thicknessMax = "4.19",
                    realBaseCurve = "4.0",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1610,
                    sphere = 0.0,
                    cylinder = 3.0,
                    axis = 90,
                    baseCurve = 4.0,
                    centerThickness = null,
                    edgeThickness = 1.0,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 18`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1610,
                    spherePower = 0.0,
                    cylinderPower = 3.0,
                    axis = "90",
                    thicknessOnAxis = "1.0",
                    thicknessCenter = "4.23",
                    thicknessEdge = "1.0",
                    thicknessMax = "4.37",
                    realBaseCurve = "6.0",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1610,
                    sphere = 0.0,
                    cylinder = 3.0,
                    axis = 90,
                    baseCurve = 6.0,
                    centerThickness = null,
                    edgeThickness = 1.0,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 19`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1610,
                    spherePower = 0.0,
                    cylinderPower = -6.0,
                    axis = "22",
                    thicknessOnAxis = "3.85",
                    thicknessCenter = "2.0",
                    thicknessEdge = "2.02",
                    thicknessMax = "9.49",
                    realBaseCurve = "4.0",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1610,
                    sphere = 0.0,
                    cylinder = -6.0,
                    axis = 22,
                    baseCurve = 4.0,
                    centerThickness = 2.0,
                    edgeThickness = null,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }

    @Test
    fun `calculation 20`() =
        runTest {
            val expected =
                CalculatedData(
                    refractionIndex = index1610,
                    spherePower = 5.0,
                    cylinderPower = -5.0,
                    axis = "0",
                    thicknessOnAxis = "1.0",
                    thicknessCenter = "6.39",
                    thicknessEdge = "1.0",
                    thicknessMax = "6.7",
                    realBaseCurve = "7.0",
                    diameter = "70.0",
                )

            val actual =
                LensData(
                    refractiveIndex = index1610,
                    sphere = 5.0,
                    cylinder = -5.0,
                    axis = 0,
                    baseCurve = 7.0,
                    centerThickness = null,
                    edgeThickness = 1.0,
                    diameter = 70.0,
                )

            viewModel.uiState.test {
                skipItems(1)
                viewModel.uiEvent(MainScreenUiEvent.OnCalculateThickness(actual))
                assertEquals(expected, awaitItem().calculatedData)
            }
        }
}
