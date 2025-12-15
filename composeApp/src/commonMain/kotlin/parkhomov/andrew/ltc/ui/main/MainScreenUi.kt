@file:OptIn(ExperimentalMaterial3Api::class)

package parkhomov.andrew.ltc.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ltc.composeapp.generated.resources.Res
import ltc.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiEvent
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiState
import org.jetbrains.compose.ui.tooling.preview.Preview
import parkhomov.andrew.ltc.ui.main.tabs.DiameterTab
import parkhomov.andrew.ltc.ui.main.tabs.ThicknessTab

@Preview
@Composable
fun MainScreenUi(
    uiData: MainScreenUiState = MainScreenUiState.mock(),
    uiEvent: (MainScreenUiEvent) -> Unit = {},
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            MainTopBar(
                comparisonCount = uiData.lensInCompareList,
                onCompareClick = { uiEvent(MainScreenUiEvent.OnCompareClick) },
                onTransposeClick = { /* TODO vm change */ },
                onSettingsClick = { /* todo open dialog */ }
            )
        },
        bottomBar = {
            MainBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selectedTab) {
                0 -> ThicknessTab(
                    uiData = uiData,
                    uiEvent = uiEvent
                )
                1 -> DiameterTab()
            }
        }
    }
}


@Composable
private fun MainTopBar(
    comparisonCount: Int,
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


            IconButton(onClick = onTransposeClick) {
                Icon(
                    imageVector = Icons.Default.Refresh,
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
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Straighten,
                    contentDescription = null
                )
            },
            label = { Text("Товщина") }
        )

        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
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

@Preview
@Composable
private fun MainTopBarPreview(){
    MainTopBar(
        comparisonCount = 2,
        onCompareClick = {},
        onTransposeClick = {},
        onSettingsClick = {}
    )
}
