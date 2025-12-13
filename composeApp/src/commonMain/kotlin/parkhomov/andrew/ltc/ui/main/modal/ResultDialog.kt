package parkhomov.andrew.ltc.ui.main.modal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ltc.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.utils.toFormattedString

@Composable
fun ResultDialog(
    data: CalculatedData,
    onDismiss: () -> Unit,
    onShare: () -> Unit = {},
    onAddToCompare: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = null,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                // Index of refraction
                ResultItem(
                    label = stringResource(Res.string.result_index_of_refraction, data.refractionIndex)
                )

                HorizontalDivider()

                // Sphere power (форматуємо Double)
                ResultItem(
                    label = stringResource(
                        Res.string.result_sphere_power,
                        data.spherePower.toFormattedString(2)
                    )
                )

                // Cylinder power (якщо є)
                data.cylinderPower?.let { cylinder ->
                    HorizontalDivider()
                    ResultItem(
                        label = stringResource(
                            Res.string.result_cylinder_power,
                            cylinder.toFormattedString(2)
                        )
                    )
                }

                // Axis (якщо є)
                data.axis?.let {
                    HorizontalDivider()
                    ResultItem(
                        label = stringResource(Res.string.result_axis, it)
                    )
                }

                // Thickness on axis (якщо є)
                data.thicknessOnAxis?.let {
                    HorizontalDivider()
                    ResultItem(
                        label = stringResource(
                            Res.string.result_on_axis_thickness,
                            data.axis ?: "",
                            it
                        )
                    )
                }

                HorizontalDivider()

                // Center thickness
                ResultItem(
                    label = stringResource(Res.string.result_center_thickness, data.thicknessCenter)
                )

                HorizontalDivider()

                // Minimum thickness (edge thickness)
                ResultItem(
                    label = stringResource(Res.string.result_min_edge_thickness, data.thicknessEdge)
                )

                // Maximum thickness (якщо є)
                data.thicknessMax?.let {
                    HorizontalDivider()
                    ResultItem(
                        label = stringResource(Res.string.result_max_edge_thickness, it)
                    )
                }

                HorizontalDivider()

                // Base curve
                ResultItem(
                    label = stringResource(Res.string.result_base_curve, data.realBaseCurve)
                )

                HorizontalDivider()

                // Diameter
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
                // Share button with icon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onShare) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = stringResource(Res.string.result_share),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Add to compare list button
                TextButton(
                    onClick = {
                        onAddToCompare()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(Res.string.result_add_to_list).uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        shape = RoundedCornerShape(16.dp)
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