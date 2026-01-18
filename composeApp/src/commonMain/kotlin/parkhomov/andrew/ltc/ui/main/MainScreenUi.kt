@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class, ExperimentalFoundationApi::class)

package parkhomov.andrew.ltc.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ripple
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.LocalStrings
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import ltc.composeapp.generated.resources.Res
import ltc.composeapp.generated.resources.ic_transpose
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.tooling.preview.Preview
import parkhomov.andrew.ltc.components.Transpose
import parkhomov.andrew.ltc.components.modifiers.dismissKeyboardOnTap
import parkhomov.andrew.ltc.data.InputType
import parkhomov.andrew.ltc.data.LensData
import parkhomov.andrew.ltc.data.RefractiveIndexUiModel
import parkhomov.andrew.ltc.data.Tab
import parkhomov.andrew.ltc.data.TabDiameter
import parkhomov.andrew.ltc.data.TabThickness
import parkhomov.andrew.ltc.provider.getVersionName
import parkhomov.andrew.ltc.provider.keyboardAsState
import parkhomov.andrew.ltc.strings.Strings
import parkhomov.andrew.ltc.theme.ThemeMode
import parkhomov.andrew.ltc.theme.isDarkTheme
import parkhomov.andrew.ltc.theme.toAppTheme
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiEvent
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiState
import parkhomov.andrew.ltc.ui.main.data.TopBarActions
import parkhomov.andrew.ltc.ui.main.modal.AddCustomIndexDialog
import parkhomov.andrew.ltc.ui.main.modal.DeleteConfirmDialog
import parkhomov.andrew.ltc.ui.main.modal.FieldInfoDialog
import parkhomov.andrew.ltc.ui.main.modal.SettingsDialog
import parkhomov.andrew.ltc.ui.main.modal.TopBarInfoDialog
import parkhomov.andrew.ltc.ui.main.tabs.DiameterTab
import parkhomov.andrew.ltc.ui.main.tabs.ThicknessTab
import parkhomov.andrew.ltc.utils.toFormattedString
import kotlin.time.ExperimentalTime

@Preview
@Composable
fun MainScreenUi(
    uiData: MainScreenUiState = MainScreenUiState.mock(),
    uiEvent: (MainScreenUiEvent) -> Unit = {},
) {
    val strings: Strings = LocalStrings.current
    val isKeyboardVisible: Boolean by keyboardAsState()
    var selectedTab: Tab by remember { mutableStateOf(Tab.Thickness) }
    var infoDialogData: InputType? by remember { mutableStateOf(null) }
    var topBarInfoDialog: TopBarInfoType? by remember { mutableStateOf(null) }

    infoDialogData?.let { dialogData: InputType ->
        FieldInfoDialog(
            inputType = dialogData,
            onDismiss = { infoDialogData = null }
        )
    }

    topBarInfoDialog?.let { infoType: TopBarInfoType ->
        TopBarInfoDialog(
            title = infoType.getTitle(strings),
            description = infoType.getDescription(strings),
            onDismiss = { topBarInfoDialog = null }
        )
    }

    if (uiData.showSettingsDialog) {
        SettingsDialog(
            currentLanguage = uiData.language,
            currentTheme = uiData.themeId.toAppTheme(),
            appVersion = getVersionName(),
            onLanguageSelected = { language: String ->
                uiEvent(MainScreenUiEvent.UpdateAppLanguage(language))
            },
            onThemeSelected = { theme: ThemeMode ->
                uiEvent(MainScreenUiEvent.UpdateAppTheme(theme))
            },
            onDismiss = { uiEvent(MainScreenUiEvent.HideSettingsDialog) }
        )
    }

    if (uiData.showAddCustomIndexDialog) {
        AddCustomIndexDialog(
            onSave = { label, value ->
                uiEvent(MainScreenUiEvent.SaveCustomIndex(label, value))
            },
            onDismiss = { uiEvent(MainScreenUiEvent.HideAddCustomIndexDialog) }
        )
    }

    uiData.indexToDelete?.let { indexToDelete ->
        DeleteConfirmDialog(
            indexName = indexToDelete.label,
            onConfirm = { uiEvent(MainScreenUiEvent.ConfirmDeleteIndex) },
            onDismiss = { uiEvent(MainScreenUiEvent.HideDeleteConfirmDialog) }
        )
    }

    val thicknessInputValues: SnapshotStateMap<TabThickness, String?> = remember(uiData.lensData) {
        mutableStateMapOf(
            TabThickness.Sphere to uiData.lensData?.sphere?.toString(),
            TabThickness.Cylinder to uiData.lensData?.cylinder?.toString(),
            TabThickness.Axis to uiData.lensData?.axis?.toString(),
            TabThickness.BaseCurve to uiData.lensData?.baseCurve?.toString(),
            TabThickness.CenterThickness to uiData.lensData?.centerThickness?.toFormattedString(),
            TabThickness.EdgeThickness to uiData.lensData?.edgeThickness?.toFormattedString(),
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

    val fieldsEnabledState: ImmutableMap<TabThickness, Boolean> by rememberFieldsEnabledStateFlow(
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
                LensData.getLensData(uiData.selectedRefractiveIndex, thicknessInputValues)
            uiEvent(MainScreenUiEvent.DoTransposition(lensData))
        }
    }

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .dismissKeyboardOnTap(),
        topBar = {
            val isCylinderEntered = thicknessInputValues[TabThickness.Cylinder]
                ?.toDoubleOrNull() != null
            MainTopBar(
                strings = strings,
                comparisonCount = uiData.lensInCompareList,
                selectedTab = selectedTab,
                isCylinderEntered = isCylinderEntered,
                topBarActions = TopBarActions(
                    onCompareClick = { uiEvent(MainScreenUiEvent.OnCompareClick) },
                    onCompareLongClick = { topBarInfoDialog = TopBarInfoType.Compare },
                    onTransposeClick = { uiEvent(MainScreenUiEvent.OnTranspositionIconClick) },
                    onTransposeLongClick = { topBarInfoDialog = TopBarInfoType.Transpose },
                    onSettingsClick = { uiEvent(MainScreenUiEvent.ShowSettingsDialog) }
                )
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
                    strings = strings,
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
                    refractiveIndices = uiData.refractiveIndices,
                    selectedRefractiveIndex = uiData.selectedRefractiveIndex,
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
    strings: Strings,
    comparisonCount: Int,
    selectedTab: Tab,
    isCylinderEntered: Boolean,
    topBarActions: TopBarActions,
) {
    TopAppBar(
        title = { Text(strings.appNameFull) },
        actions = {
            val isCompareEnabled = comparisonCount >= 2
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .combinedClickable(
                        onClick = { if (isCompareEnabled) topBarActions.onCompareClick() },
                        onLongClick = topBarActions.onCompareLongClick,
                        role = Role.Button,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = if (isCompareEnabled) {
                            ripple(bounded = false, radius = 24.dp)
                        } else {
                            null
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                BadgedBox(
                    badge = {
                        if (comparisonCount > 0) {
                            Badge(
                                containerColor = if (isCompareEnabled)
                                    MaterialTheme.colorScheme.error
                                else
                                    MaterialTheme.colorScheme.outline
                            ) {
                                Text(
                                    text = comparisonCount.toString(),
                                    color = if (isCompareEnabled)
                                        MaterialTheme.colorScheme.onError
                                    else
                                        MaterialTheme.colorScheme.surface
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Balance,
                        contentDescription = strings.contentDescriptionCompare,
                        tint = if (isCompareEnabled)
                            MaterialTheme.colorScheme.onSurface
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    )
                }
            }

            val isTransposeEnabled = selectedTab == Tab.Thickness && isCylinderEntered
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .combinedClickable(
                        onClick = { if (isTransposeEnabled) topBarActions.onTransposeClick() },
                        onLongClick = topBarActions.onTransposeLongClick,
                        role = Role.Button,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = if (isTransposeEnabled) {
                            ripple(bounded = false, radius = 24.dp)
                        } else {
                            null
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Transpose,
                    contentDescription = strings.contentDescriptionTranspose,
                    tint = if (isTransposeEnabled)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )
            }

            IconButton(onClick = topBarActions.onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = strings.contentDescriptionSettings
                )
            }
        }
    )
}

@Composable
private fun MainBottomBar(
    strings: Strings,
    selectedTab: Tab,
    onTabSelected: (Tab) -> Unit
) {
    val indicatorColor: Color = if (isDarkTheme()) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        NavigationBarItem(
            selected = selectedTab == Tab.Thickness,
            onClick = { onTabSelected(Tab.Thickness) },
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_transpose),
                    contentDescription = null
                )
            },
            label = { Text(strings.tabThickness) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = indicatorColor
            )
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
            label = { Text(strings.tabDiameter) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor =  indicatorColor
            )
        )
    }
}

@Composable
private fun rememberFieldsEnabledStateFlow(
    inputValues: SnapshotStateMap<TabThickness, String?>
): State<ImmutableMap<TabThickness, Boolean>> {
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

            persistentMapOf(
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

private enum class TopBarInfoType {
    Compare,
    Transpose;

    fun getTitle(strings: Strings): String = when (this) {
        Compare -> strings.infoCompareTitle
        Transpose -> strings.infoTransposeTitle
    }

    fun getDescription(strings: Strings): String = when (this) {
        Compare -> strings.infoCompareDesc
        Transpose -> strings.infoTransposeDesc
    }
}

@Preview
@Composable
private fun MainTopBarPreview() {
    val strings: Strings = LocalStrings.current
    MainTopBar(
        strings = strings,
        comparisonCount = 2,
        selectedTab = Tab.Thickness,
        isCylinderEntered = true,
        topBarActions = TopBarActions(
            onCompareClick = {},
            onCompareLongClick = {},
            onTransposeClick = {},
            onTransposeLongClick = {},
            onSettingsClick = {}
        )
    )
}
