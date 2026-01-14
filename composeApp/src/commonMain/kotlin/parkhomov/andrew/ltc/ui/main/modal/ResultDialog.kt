package parkhomov.andrew.ltc.ui.main.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.LocalStrings
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import parkhomov.andrew.ltc.components.AppDialog
import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.provider.ShareManager
import parkhomov.andrew.ltc.strings.Strings
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiEvent
import parkhomov.andrew.ltc.utils.toFormattedString

@Preview
@Composable
fun ResultDialog(
    data: CalculatedData = CalculatedData.mock(),
    isLensInCompareList: Boolean = false,
    uiEvent: (MainScreenUiEvent) -> Unit = {}
) {
    val strings: Strings = LocalStrings.current

    val textId: String = if (isLensInCompareList) {
        strings.resultRemoveFromList
    } else {
        strings.resultAddToList
    }
    val onDismiss = { uiEvent(MainScreenUiEvent.HideResultDialog) }
    val title = strings.shareSubject
    val scope = rememberCoroutineScope()

    AppDialog(
        onDismiss = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = strings.resultDialogTitle,
                    style = MaterialTheme.typography.headlineSmall
                )
                IconButton(onClick = {
                    scope.launch {
                        val shareText = if (data.cylinderPower == null) {
                            strings.shareTextOnlySphere(
                                data.refractionIndex.value.toString(),
                                data.spherePower.toFormattedString(2),
                                data.thicknessCenter,
                                data.thicknessEdge,
                                data.realBaseCurve,
                                data.diameter
                            )
                        } else {
                            strings.shareTextFull(
                                data.refractionIndex.value.toString(),
                                data.spherePower.toFormattedString(2),
                                data.cylinderPower.toFormattedString(2),
                                data.axis ?: "",
                                data.axis ?: "",
                                data.thicknessOnAxis ?: "",
                                data.thicknessCenter,
                                data.thicknessEdge,
                                data.thicknessMax ?: "",
                                data.realBaseCurve,
                                data.diameter
                            )
                        }

                        ShareManager().shareText(
                            text = shareText,
                            title = title
                        )
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = strings.resultShare,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                ResultItem(
                    label = strings.resultIndexOfRefraction(
                        data.refractionIndex.value.toString()
                    )
                )
                HorizontalDivider()
                ResultItem(
                    label = strings.resultSpherePower(
                        data.spherePower.toFormattedString(2)
                    )
                )
                data.cylinderPower?.let { cylinder: Double ->
                    HorizontalDivider()
                    ResultItem(
                        label = strings.resultCylinderPower(
                            cylinder.toFormattedString(2)
                        )
                    )
                }
                data.axis?.let { axis: String ->
                    HorizontalDivider()
                    ResultItem(label = strings.resultAxis(axis))
                }
                data.thicknessOnAxis?.let { thickness: String ->
                    HorizontalDivider()
                    ResultItem(
                        label = strings.resultOnAxisThickness(
                            data.axis.orEmpty(),
                            thickness
                        )
                    )
                }
                HorizontalDivider()
                ResultItem(
                    label = strings.resultCenterThickness(data.thicknessCenter)
                )
                HorizontalDivider()
                ResultItem(
                    label = strings.resultMinEdgeThickness(data.thicknessEdge)
                )
                data.thicknessMax?.let { thicknessMax: String ->
                    HorizontalDivider()
                    ResultItem(
                        label = strings.resultMaxEdgeThickness(thicknessMax)
                    )
                }
                HorizontalDivider()
                ResultItem(
                    label = strings.resultBaseCurve(data.realBaseCurve)
                )
                HorizontalDivider()
                ResultItem(
                    label = strings.resultDiameter(data.diameter)
                )
            }
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (isLensInCompareList) {
                            uiEvent(MainScreenUiEvent.OnRemoveFromCompareListClicked)
                        } else {
                            uiEvent(MainScreenUiEvent.OnAddToCompareClicked)
                        }
                    },
                ) {
                    Text(
                        text = textId.uppercase(),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onDismiss
                ) {
                    Text(strings.buttonOk)
                }
            }
        }
    )
}

@Composable
private fun ResultItem(
    label: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    )
}
