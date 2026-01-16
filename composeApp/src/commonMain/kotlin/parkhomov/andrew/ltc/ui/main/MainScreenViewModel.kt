@file:OptIn(ExperimentalTime::class)

package parkhomov.andrew.ltc.ui.main

import androidx.compose.runtime.Stable
import game.dice.storage.repository.SettingsProvider
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import parkhomov.andrew.ltc.base.AppViewModel
import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.data.LensData
import parkhomov.andrew.ltc.data.RefractiveIndexUiModel
import parkhomov.andrew.ltc.database.RefractiveIndexEntity
import parkhomov.andrew.ltc.database.RefractiveIndexRepository
import parkhomov.andrew.ltc.domain.CompareLensStorage
import parkhomov.andrew.ltc.provider.isIos
import parkhomov.andrew.ltc.theme.ThemeMode
import parkhomov.andrew.ltc.toast.ToastProvider
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiEvent
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiState
import parkhomov.andrew.ltc.utils.BASE_0
import parkhomov.andrew.ltc.utils.BASE_1
import parkhomov.andrew.ltc.utils.BASE_10
import parkhomov.andrew.ltc.utils.BASE_10_5
import parkhomov.andrew.ltc.utils.BASE_2
import parkhomov.andrew.ltc.utils.BASE_3
import parkhomov.andrew.ltc.utils.BASE_4
import parkhomov.andrew.ltc.utils.BASE_5
import parkhomov.andrew.ltc.utils.BASE_6
import parkhomov.andrew.ltc.utils.BASE_7
import parkhomov.andrew.ltc.utils.BASE_8
import parkhomov.andrew.ltc.utils.BASE_9
import parkhomov.andrew.ltc.utils.LAB_INDEX
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@Stable
class MainScreenViewModel(
    private val compareLensStorage: CompareLensStorage,
    private val settingsProvider: SettingsProvider,
    private val refractiveIndexRepository: RefractiveIndexRepository
) : AppViewModel<MainScreenUiState, MainScreenUiEvent>() {
    override val initialState: MainScreenUiState
        get() = MainScreenUiState()

    init {
        launch {
            compareLensStorage.compareList
                .collectLatest { compareList: Set<CalculatedData> ->
                    val themeId: Int? = settingsProvider.getTheme()
                    val language: String = settingsProvider.getLanguage()
                    updateState {
                        copy(
                            themeId = themeId,
                            language = language,
                            lensInCompareList = compareList.count()
                        )
                    }
                }
        }
        launch {
            refractiveIndexRepository.getAllIndices().collect { entities ->
                val indices = entities.map { RefractiveIndexUiModel.fromEntity(it) }
                updateState {
                    copy(
                        refractiveIndices = indices.toImmutableList(),
                        selectedRefractiveIndex = if (selectedRefractiveIndex.id == 0L && indices.isNotEmpty()) {
                            indices.first()
                        } else {
                            selectedRefractiveIndex
                        }
                    )
                }
            }
        }
        showIosPromoModalIfNeeded()
    }

    override fun uiEvent(event: MainScreenUiEvent) {
        when (event) {
            is MainScreenUiEvent.OnCompareClick -> updateState { copy() }
            is MainScreenUiEvent.OnCalculateThickness -> handleCalculateButtonClick(event.lensData)
            is MainScreenUiEvent.HideResultDialog -> updateState { copy(calculatedData = null) }
            is MainScreenUiEvent.OnAddToCompareClicked -> onAddToCompareClicked()
            is MainScreenUiEvent.OnRemoveFromCompareListClicked -> onRemoveFromCompareListClicked()
            is MainScreenUiEvent.OnTranspositionIconClick -> onTranspositionClick()
            is MainScreenUiEvent.DoTransposition -> doTransposition(event.lensData)
            is MainScreenUiEvent.UpdateAppLanguage -> updateAppLanguage(event.language)
            is MainScreenUiEvent.UpdateAppTheme -> updateAppTheme(event.theme)
            is MainScreenUiEvent.ShowSettingsDialog -> updateState { copy(showSettingsDialog = true) }
            is MainScreenUiEvent.HideSettingsDialog -> updateState { copy(showSettingsDialog = false) }
            is MainScreenUiEvent.SelectRefractiveIndex -> selectRefractiveIndex(event.index)
            is MainScreenUiEvent.OnAddCustomIndexClick -> updateState { copy(showAddCustomIndexDialog = true) }
            is MainScreenUiEvent.HideAddCustomIndexDialog -> updateState { copy(showAddCustomIndexDialog = false) }
            is MainScreenUiEvent.SaveCustomIndex -> saveCustomIndex(event.label, event.value)
            is MainScreenUiEvent.OnDeleteCustomIndexClick -> updateState { copy(indexToDelete = event.index) }
            is MainScreenUiEvent.HideDeleteConfirmDialog -> updateState { copy(indexToDelete = null) }
            is MainScreenUiEvent.ConfirmDeleteIndex -> confirmDeleteIndex()
            is MainScreenUiEvent.HideIosPromoDialog -> hideIosPromoDialog()
        }
    }

    private fun saveCustomIndex(label: String, value: Double) {
        launch {
            val entity = RefractiveIndexEntity(
                value = value,
                label = label,
                isUserCreated = true
            )
            refractiveIndexRepository.insert(entity)
            updateState { copy(showAddCustomIndexDialog = false) }
        }
    }

    private fun confirmDeleteIndex() {
        val indexToDelete: RefractiveIndexUiModel = uiState.value.indexToDelete ?: return
        launch {
            refractiveIndexRepository.delete(indexToDelete.id)
            updateState {
                copy(
                    indexToDelete = null,
                    selectedRefractiveIndex = if (selectedRefractiveIndex.id == indexToDelete.id) {
                        refractiveIndices.firstOrNull() ?: RefractiveIndexUiModel.getDefaultIndex()
                    } else {
                        selectedRefractiveIndex
                    }
                )
            }
        }
    }

    private fun selectRefractiveIndex(index: RefractiveIndexUiModel) {
        updateState { copy(selectedRefractiveIndex = index) }
    }

    private fun updateAppTheme(theme: ThemeMode) {
        launch { settingsProvider.updateTheme(theme.id) }
        updateState { copy(themeId = theme.id) }
    }

    private fun updateAppLanguage(language: String) {
        launch { settingsProvider.updateLanguage(language) }
        updateState { copy(language = language) }
    }

    private fun doTransposition(lensData: LensData) = launch {
        val spherePower: Double? = lensData.sphere
        val cylinderPower: Double? = lensData.cylinder
        val axisPower: Int? = lensData.axis
        val (sphere: Double, cylinder: Double, axis: Int) = calculateTransposition(
            spherePower = spherePower ?: 0.0,
            cylinderPower = cylinderPower ?: 0.0,
            axisPower = axisPower ?: 0
        )
        updateState {
            copy(
                lensData = lensData.copy(
                    sphere = sphere,
                    cylinder = cylinder,
                    axis = axis
                )
            )
        }
    }

    private fun onTranspositionClick() {
        updateState {
            copy(calculateTransposition = Clock.System.now())
        }
    }

    private fun calculateTransposition(
        spherePower: Double,
        cylinderPower: Double,
        axisPower: Int
    ): Triple<Double, Double, Int> {
        val sphere = try {
            val sphereOriginal = (spherePower + cylinderPower)
            // prevent 0.000000000000002
            if (sphereOriginal.toString().length >= 5) {
                sphereOriginal.toString().substring(0, 5).toDouble()
            } else {
                sphereOriginal
            }
        } catch (_: NumberFormatException) {
            0.0
        }
        val cylinder = try {
            if (cylinderPower == 0.0) {
                0.0
            } else {
                -cylinderPower
            }
        } catch (_: NumberFormatException) {
            0.0
        }
        val axis = try {
            if (axisPower > 90) {
                abs(180 - (axisPower + 90))
            } else {
                axisPower + 90
            }
        } catch (_: NumberFormatException) {
            0
        }
        return Triple(sphere, cylinder, axis)
    }

    private fun onAddToCompareClicked() {
        uiState.value.calculatedData?.let(compareLensStorage::addItem)
        updateResultDialogCompareButton()
    }

    private fun onRemoveFromCompareListClicked() {
        uiState.value.calculatedData?.let(compareLensStorage::removeItem)
        updateResultDialogCompareButton()
    }

    private fun updateResultDialogCompareButton() {
        updateState {
            copy(
                isLensInCompareList = compareLensStorage.isInStorage(uiState.value.calculatedData)
            )
        }
    }

    private fun handleCalculateButtonClick(lensData: LensData) {
        onCalculateBtnClicked(
            lensData = lensData,
            refractiveIndex = lensData.refractiveIndex,
            spherePowerString = lensData.sphere?.toString().orEmpty(),
            cylinderPowerString = lensData.cylinder?.toString().orEmpty(),
            axisString = lensData.axis?.toString().orEmpty(),
            curveString = lensData.baseCurve?.toString().orEmpty(),
            centerThicknessString = lensData.centerThickness?.toString().orEmpty(),
            edgeThicknessString = lensData.edgeThickness?.toString().orEmpty(),
            diameter = lensData.diameter ?: 70.0
        )
    }

    fun onCalculateBtnClicked(
        lensData: LensData,
        refractiveIndex: RefractiveIndexUiModel,
        spherePowerString: String,
        cylinderPowerString: String,
        axisString: String,
        curveString: String,
        centerThicknessString: String,
        edgeThicknessString: String,
        diameter: Double
    ) = launch {

        val axisView: String

        var maybeRacalculatedSphere = try {
            spherePowerString.toDouble()
        } catch (_: NumberFormatException) {
            0.0
        }

        var cylinderPower = try {
            cylinderPowerString.toDouble()
        } catch (_: NumberFormatException) {
            0.0
        }

        var axis = (axisString.toIntOrNull() ?: 0).let { axis ->
            if (axis in 0..180) axis else 0
        }

        axisView = axis.toString()

        var curve = try {
            curveString.toDouble()
        } catch (_: NumberFormatException) {
            null
        }

        var edgeThickness = edgeThicknessString.toDoubleOrNull() ?: 0.0

        // must ba BEFORE cylinderPower possible recalculation
        axis = recalculateAxisInMinusCylinder(cylinderPower, axis)

        if (cylinderPower > 0) {
            maybeRacalculatedSphere += cylinderPower
            cylinderPower = -cylinderPower
        }

        curve = curve ?: handleNoBaseCurveBehaviour(maybeRacalculatedSphere)
        val realRadiusMm = getReaRadiusInMM(curve)

        var centerThickness = try {
            centerThicknessString.toDouble()
        } catch (_: NumberFormatException) {
            if (maybeRacalculatedSphere <= 0.0) {
                2.0
            } else {
                0.0 // plus lens
            }
        }

        centerThickness = when {
            maybeRacalculatedSphere <= 0 && cylinderPower == 0.0 -> centerThickness
            maybeRacalculatedSphere <= 0 && cylinderPower > 0 && maybeRacalculatedSphere == 0.0 ->
                (diameter / 2).pow(2.0) * cylinderPower / (2000 * (refractiveIndex.value - 1)) + edgeThickness

            maybeRacalculatedSphere <= 0 && cylinderPower > 0 && maybeRacalculatedSphere + cylinderPower > 0 ->
                (diameter / 2).pow(2.0) * (maybeRacalculatedSphere + cylinderPower) / (2000 * (refractiveIndex.value - 1)) + edgeThickness

            maybeRacalculatedSphere > 0 -> {
                val tempValue = if (cylinderPower > 0) {
                    maybeRacalculatedSphere + cylinderPower
                } else
                    maybeRacalculatedSphere
                (diameter / 2).pow(2.0) * tempValue / (2000 * (refractiveIndex.value - 1)) + edgeThickness
            }

            else -> centerThickness
        }

        // Find D1
        val recalculatedFrontCurve = getRecalculatedFrontCurve(
            refractiveIndex.value,
            realRadiusMm
        )

        val recalculatedCylinderCurve = getRecalculatedCylinderCurve(
            recalculatedFrontCurve,
            cylinderPower,
            centerThickness,
            refractiveIndex,
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
            refractiveIndex
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
                refractionIndex = refractiveIndex,
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
                refractionIndex = refractiveIndex,
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
        updateState {
            copy(
                calculatedData = calculatedData,
                isLensInCompareList = compareLensStorage.isInStorage(calculatedData),
                lensData = lensData.copy(
                    baseCurve = curve,
                    centerThickness = centerThickness,
                    diameter = diameter
                )
            )
        }
    }

    private fun handleNoBaseCurveBehaviour(value: Double): Double {
        return when {
            value <= -8.0 -> BASE_0
            value in -7.99..-6.0 -> BASE_1
            value in -5.99..-4.0 -> BASE_2
            value in -3.99..-2.0 -> BASE_3
            value in -1.99..2.0 -> BASE_4
            value in 2.01..2.99 -> BASE_5
            value in 3.0..4.99 -> BASE_6
            value in 5.0..5.99 -> BASE_7
            value in 6.0..6.99 -> BASE_8
            value in 7.0..7.99 -> BASE_9
            value in 8.0..9.99 -> BASE_10
            else -> BASE_10_5 // value >= 10.0
        }
    }

    private fun getReaRadiusInMM(curveInDptr: Double): Double =
        (LAB_INDEX - 1) / (curveInDptr / 1000)

    private fun getRecalculatedFrontCurve(lensIndex: Double, realRadiusMm: Double): Double =
        (lensIndex - 1) * 1000 / realRadiusMm

    private fun getRecalculatedCylinderCurve(
        recalculatedFrontCurve: Double,
        cylinderPower: Double,
        centerThickness: Double,
        lensIndex: RefractiveIndexUiModel,
        spherePower: Double
    ): Double {
        return (cylinderPower - (recalculatedFrontCurve / (1 - centerThickness / lensIndex.value /
                1000.0 * recalculatedFrontCurve) - spherePower)) * lensIndex.indexX
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
        lensIndex: RefractiveIndexUiModel
    ): Double {
        return (spherePower - recalculatedFrontCurve /
                (1 - centerThickness / lensIndex.value / 1000.0 * recalculatedFrontCurve)) * lensIndex.indexX
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

    private fun hideIosPromoDialog() {
        launch {
            settingsProvider.setIosPromoShown(true)
            updateState { copy(showIosPromoDialog = false) }
        }
    }

    private fun showIosPromoModalIfNeeded() {
        launch {
            if (!isIos()) {
                val wasShown: Boolean = settingsProvider.isIosPromoShown()
                if (!wasShown) {
                    delay(5.seconds)
                    updateState { copy(showIosPromoDialog = true) }
                }
            }
        }
    }

}
