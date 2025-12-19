@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)

package parkhomov.andrew.ltc.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.GetApp
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import ltc.composeapp.generated.resources.Res
import ltc.composeapp.generated.resources.app_name
import ltc.composeapp.generated.resources.ic_transpose
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import parkhomov.andrew.ltc.components.Transpose
import parkhomov.andrew.ltc.components.modifiers.dismissKeyboardOnTap
import parkhomov.andrew.ltc.data.InputType
import parkhomov.andrew.ltc.data.LensData
import parkhomov.andrew.ltc.data.RefractiveIndex
import parkhomov.andrew.ltc.data.Tab
import parkhomov.andrew.ltc.data.TabDiameter
import parkhomov.andrew.ltc.data.TabThickness
import parkhomov.andrew.ltc.provider.keyboardAsState
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiEvent
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiState
import parkhomov.andrew.ltc.ui.main.modal.FieldInfoDialog
import parkhomov.andrew.ltc.ui.main.tabs.DiameterTab
import parkhomov.andrew.ltc.ui.main.tabs.ThicknessTab
import kotlin.time.ExperimentalTime

@Preview
@Composable
fun MainScreenUi(
    uiData: MainScreenUiState = MainScreenUiState.mock(),
    uiEvent: (MainScreenUiEvent) -> Unit = {},
) {
    val isKeyboardVisible: Boolean by keyboardAsState()
    var selectedTab: Tab by remember { mutableStateOf(Tab.Thickness) }
    var infoDialogData: InputType? by remember { mutableStateOf(null) }

    infoDialogData?.let { dialogData: InputType ->
        FieldInfoDialog(
            inputType = dialogData,
            onDismiss = { infoDialogData = null }
        )
    }

    var selectedRefractiveIndex: RefractiveIndex by remember(uiData.lensData?.refractiveIndex) {
        mutableStateOf(uiData.lensData?.refractiveIndex ?: RefractiveIndex.CR39)
    }

    val thicknessInputValues: SnapshotStateMap<TabThickness, String?> = remember(uiData.lensData) {
        mutableStateMapOf(
            TabThickness.Sphere to uiData.lensData?.sphere?.toString(),
            TabThickness.Cylinder to uiData.lensData?.cylinder?.toString(),
            TabThickness.Axis to uiData.lensData?.axis?.toString(),
            TabThickness.BaseCurve to uiData.lensData?.baseCurve?.toString(),
            TabThickness.CenterThickness to uiData.lensData?.centerThickness?.toString(),
            TabThickness.EdgeThickness to uiData.lensData?.edgeThickness?.toString(),
            TabThickness.LensDiameter to uiData.lensData?.diameter?.toString()
        )
    }

    val diameterInputValues: SnapshotStateMap<TabDiameter, String?> = remember {
        mutableStateMapOf(
            TabDiameter.EffectiveDiameter to null,
            TabDiameter.DistanceBetweenLenses to null,
            TabDiameter.PupilDistance to null,
        )
    }

    val fieldsEnabledState: Map<TabThickness, Boolean> by rememberFieldsEnabledStateFlow(
        thicknessInputValues
    )
    LaunchedEffect(fieldsEnabledState) {
        if (fieldsEnabledState[TabThickness.CenterThickness] == false) {
            thicknessInputValues[TabThickness.CenterThickness] = null
        }
        if (fieldsEnabledState[TabThickness.EdgeThickness] == false) {
            thicknessInputValues[TabThickness.EdgeThickness] = null
        }
    }

    LaunchedEffect(uiData.calculateTransposition) {
        if (uiData.calculateTransposition != null) {
            val lensData: LensData =
                LensData.getLensData(selectedRefractiveIndex, thicknessInputValues)
            uiEvent(MainScreenUiEvent.DoTransposition(lensData))
        }
    }

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .dismissKeyboardOnTap(),
        topBar = {
            MainTopBar(
                comparisonCount = uiData.lensInCompareList,
                selectedTab = selectedTab,
                onCompareClick = { uiEvent(MainScreenUiEvent.OnCompareClick) },
                onTransposeClick = { uiEvent(MainScreenUiEvent.OnTranspositionIconClick) },
                onSettingsClick = { /* todo open dialog */ }
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = !isKeyboardVisible,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(300)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(0)
                )
            ) {
                MainBottomBar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selectedTab) {
                is Tab.Thickness -> ThicknessTab(
                    uiEvent = uiEvent,
                    selectedRefractiveIndex = selectedRefractiveIndex,
                    updateRefractiveIndex = { selectedRefractiveIndex = it },
                    thicknessInputValues = thicknessInputValues,
                    fieldsEnabledState = fieldsEnabledState,
                    onInfoIconClicked = { infoDialogData = it }
                )

                is Tab.Diameter -> DiameterTab(
                    diameterInputValues = diameterInputValues,
                    onInfoIconClicked = { infoDialogData = it }
                )
            }
        }
    }
}

@Composable
private fun MainTopBar(
    comparisonCount: Int,
    selectedTab: Tab,
    onCompareClick: () -> Unit,
    onTransposeClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(Res.string.app_name)) },
        actions = {
            IconButton(
                onClick = onCompareClick,
                enabled = comparisonCount >= 2
            ) {
                BadgedBox(
                    badge = {
                        if (comparisonCount > 0) {
                            Badge(
                                containerColor = if (comparisonCount >= 2)
                                    MaterialTheme.colorScheme.error
                                else
                                    MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = comparisonCount.toString(),
                                    color = if (comparisonCount >= 2)
                                        MaterialTheme.colorScheme.onError
                                    else
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Balance,
                        contentDescription = "Порівняти",
                        tint = if (comparisonCount >= 2)
                            LocalContentColor.current
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    )
                }
            }


            IconButton(
                onClick = onTransposeClick,
                enabled = selectedTab == Tab.Thickness
            ) {
                Icon(
                    imageVector = Icons.Transpose,
                    contentDescription = "Транспозиція"
                )
            }

            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Налаштування"
                )
            }
        }
    )
}

@Composable
private fun MainBottomBar(
    selectedTab: Tab,
    onTabSelected: (Tab) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == Tab.Thickness,
            onClick = { onTabSelected(Tab.Thickness) },
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_transpose),
                    contentDescription = null
                )
            },
            label = { Text("Товщина") }
        )

        NavigationBarItem(
            selected = selectedTab == Tab.Diameter,
            onClick = { onTabSelected(Tab.Diameter) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Circle,
                    contentDescription = null
                )
            },
            label = { Text("Діаметр") }
        )
    }
}

@Composable
private fun rememberFieldsEnabledStateFlow(
    inputValues: SnapshotStateMap<TabThickness, String?>
): State<Map<TabThickness, Boolean>> {
    return remember(inputValues) {
        derivedStateOf {
            val sphere = inputValues[TabThickness.Sphere]?.toDoubleOrNull()
            val cylinder = inputValues[TabThickness.Cylinder]?.toDoubleOrNull()

            val effectivePower = when {
                sphere == null -> null
                cylinder != null && cylinder > 0 -> sphere + cylinder
                else -> sphere
            }

            val centerThicknessEnabled = effectivePower?.let { it <= 0 } ?: true
            val edgeThicknessEnabled = effectivePower?.let { it > 0 } ?: true

            mapOf(
                TabThickness.Sphere to true,
                TabThickness.Cylinder to true,
                TabThickness.Axis to true,
                TabThickness.BaseCurve to true,
                TabThickness.CenterThickness to centerThicknessEnabled,
                TabThickness.EdgeThickness to edgeThicknessEnabled,
                TabThickness.LensDiameter to true
            )
        }
    }
}

@Preview
@Composable
private fun MainTopBarPreview() {
    MainTopBar(
        comparisonCount = 2,
        selectedTab = Tab.Thickness,
        onCompareClick = {},
        onTransposeClick = {},
        onSettingsClick = {}
    )
}
