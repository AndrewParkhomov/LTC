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
import androidx.compose.material3.AlertDialog
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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ltc.composeapp.generated.resources.Res
import ltc.composeapp.generated.resources.result_add_to_list
import ltc.composeapp.generated.resources.result_axis
import ltc.composeapp.generated.resources.result_base_curve
import ltc.composeapp.generated.resources.result_center_thickness
import ltc.composeapp.generated.resources.result_cylinder_power
import ltc.composeapp.generated.resources.result_dialog_title
import ltc.composeapp.generated.resources.result_diameter
import ltc.composeapp.generated.resources.result_index_of_refraction
import ltc.composeapp.generated.resources.result_max_edge_thickness
import ltc.composeapp.generated.resources.result_min_edge_thickness
import ltc.composeapp.generated.resources.result_on_axis_thickness
import ltc.composeapp.generated.resources.result_remove_from_list
import ltc.composeapp.generated.resources.result_share
import ltc.composeapp.generated.resources.result_sphere_power
import ltc.composeapp.generated.resources.share_result_subject
import ltc.composeapp.generated.resources.share_text_full
import ltc.composeapp.generated.resources.share_text_only_sphere
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.provider.ShareManager
import parkhomov.andrew.ltc.provider.createShareText
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiEvent
import parkhomov.andrew.ltc.utils.toFormattedString

@Preview
@Composable
fun ResultDialog(
    data: CalculatedData = CalculatedData.mock(),
    uiEvent: (MainScreenUiEvent) -> Unit = {}
) {
    val textId: StringResource = if (data.isLensInCompareList) {
        Res.string.result_remove_from_list
    } else {
        Res.string.result_add_to_list
    }
    val onDismiss = { uiEvent(MainScreenUiEvent.HideResultDialog) }
    val title = stringResource(Res.string.share_result_subject)
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.result_dialog_title),
                    style = MaterialTheme.typography.headlineSmall
                )
                IconButton(onClick = {
                    scope.launch {
                        val appName = "Lens Thickness Calculator" // або stringResource
                        val appUrl =
                            "https://play.google.com/store/apps/details?id=your.package.name"

                        val shareText = data.createShareText(
                            appName = appName,
                            appUrl = appUrl,
                            shareTextOnlySphere = Res.string.share_text_only_sphere,
                            shareTextFull = Res.string.share_text_full
                        )

                        ShareManager().shareText(
                            text = shareText,
                            title = title
                        )
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = stringResource(Res.string.result_share),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                ResultItem(
                    label = stringResource(
                        Res.string.result_index_of_refraction,
                        data.refractionIndex
                    )
                )
                HorizontalDivider()
                ResultItem(
                    label = stringResource(
                        Res.string.result_sphere_power,
                        data.spherePower.toFormattedString(2)
                    )
                )
                data.cylinderPower?.let { cylinder: Double ->
                    HorizontalDivider()
                    ResultItem(
                        label = stringResource(
                            Res.string.result_cylinder_power,
                            cylinder.toFormattedString(2)
                        )
                    )
                }
                data.axis?.let { axis: String ->
                    HorizontalDivider()
                    ResultItem(label = stringResource(Res.string.result_axis, axis))
                }
                data.thicknessOnAxis?.let { thickness: String ->
                    HorizontalDivider()
                    ResultItem(
                        label = stringResource(
                            Res.string.result_on_axis_thickness,
                            data.axis.orEmpty(),
                            thickness
                        )
                    )
                }
                HorizontalDivider()
                ResultItem(
                    label = stringResource(Res.string.result_center_thickness, data.thicknessCenter)
                )
                HorizontalDivider()
                ResultItem(
                    label = stringResource(Res.string.result_min_edge_thickness, data.thicknessEdge)
                )
                data.thicknessMax?.let { thicknessMax: String ->
                    HorizontalDivider()
                    ResultItem(
                        label = stringResource(Res.string.result_max_edge_thickness, thicknessMax)
                    )
                }
                HorizontalDivider()
                ResultItem(
                    label = stringResource(Res.string.result_base_curve, data.realBaseCurve)
                )
                HorizontalDivider()
                ResultItem(
                    label = stringResource(Res.string.result_diameter, data.diameter)
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
                        if (data.isLensInCompareList) {
                            uiEvent(MainScreenUiEvent.OnRemoveFromCompareListClicked)
                        } else {
                            uiEvent(MainScreenUiEvent.OnAddToCompareClicked)
                        }
                    },
                ) {
                    Text(
                        text = stringResource(textId).uppercase(),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onDismiss
                ) {
                    Text("OK")
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
