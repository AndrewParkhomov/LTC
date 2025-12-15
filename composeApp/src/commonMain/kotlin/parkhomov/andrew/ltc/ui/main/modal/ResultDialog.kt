package parkhomov.andrew.ltc.ui.main.modal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ltc.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.utils.toFormattedString

@Preview
@Composable
fun ResultDialog(
    data: CalculatedData = CalculatedData.mock(),
    onDismiss: () -> Unit = {},
    onShare: () -> Unit = {},
    onAddToCompare: () -> Unit = {}
) {
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
                IconButton(onClick = onShare) {
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
                    label = stringResource(Res.string.result_index_of_refraction, data.refractionIndex)
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
                    onClick = {
                        onAddToCompare()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(Res.string.result_add_to_list).uppercase(),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
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