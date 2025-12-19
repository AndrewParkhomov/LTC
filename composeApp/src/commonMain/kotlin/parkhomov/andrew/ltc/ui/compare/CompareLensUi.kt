@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)

package parkhomov.andrew.ltc.ui.compare

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ltc.composeapp.generated.resources.Res
import ltc.composeapp.generated.resources.compare_clear_list
import ltc.composeapp.generated.resources.compare_list_axis
import ltc.composeapp.generated.resources.compare_list_base_curve
import ltc.composeapp.generated.resources.compare_list_center_thickness
import ltc.composeapp.generated.resources.compare_list_cylinder_power
import ltc.composeapp.generated.resources.compare_list_diameter
import ltc.composeapp.generated.resources.compare_list_index_of_refraction
import ltc.composeapp.generated.resources.compare_list_max_edge_thickness
import ltc.composeapp.generated.resources.compare_list_min_edge_thickness
import ltc.composeapp.generated.resources.compare_list_on_axis_thickness
import ltc.composeapp.generated.resources.compare_list_sphere_power
import ltc.composeapp.generated.resources.compare_list_title
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import parkhomov.andrew.ltc.components.AppOutlineButton
import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.ui.compare.data.CompareLensScreenUiEvent
import parkhomov.andrew.ltc.ui.compare.data.CompareLensScreenUiState
import parkhomov.andrew.ltc.utils.toFormattedString
import kotlin.time.ExperimentalTime


@Preview
@Composable
fun CompareLensUi(
    uiData: CompareLensScreenUiState = CompareLensScreenUiState.mock(),
    uiEvent: (CompareLensScreenUiEvent) -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.compare_list_title)) },
                navigationIcon = {
                    IconButton(onClick = { uiEvent(CompareLensScreenUiEvent.CloseScreen) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        CompareTable(
            lenses = uiData.compareList,
            onClearList = { uiEvent(CompareLensScreenUiEvent.ClearList) },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun CompareTable(
    lenses: List<CalculatedData>,
    onClearList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .horizontalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                TableHeaderCell(text = "", isLabel = true)

                CompareRow.entries.forEach { row ->
                    TableLabelCell(text = stringResource(row.labelRes))
                }
            }

            lenses.forEach { lens: CalculatedData ->
                Column(
                    modifier = Modifier.width(100.dp)
                ) {
                    TableHeaderCell(
                        text = lens.refractionIndex.label,
                        isLabel = false
                    )

                    CompareRow.entries.forEach { row: CompareRow ->
                        TableDataCell(text = row.getValue(lens))
                    }
                }
            }
        }
        AppOutlineButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(Res.string.compare_clear_list),
            onClick = onClearList,
        )
    }
}

@Composable
private fun TableHeaderCell(
    text: String,
    isLabel: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                if (isLabel) {
                    MaterialTheme.colorScheme.surfaceVariant
                } else {
                    MaterialTheme.colorScheme.primaryContainer
                }
            )
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outline
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = if (isLabel) {
                MaterialTheme.colorScheme.onSurfaceVariant
            } else {
                MaterialTheme.colorScheme.onPrimaryContainer
            }
        )
    }
}

@Composable
private fun TableLabelCell(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outline
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun TableDataCell(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outline
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

enum class CompareRow(
    val labelRes: StringResource
) {
    Index(Res.string.compare_list_index_of_refraction),
    Sphere(Res.string.compare_list_sphere_power),
    Cylinder(Res.string.compare_list_cylinder_power),
    Axis(Res.string.compare_list_axis),
    AxisThickness(Res.string.compare_list_on_axis_thickness),
    CenterThickness(Res.string.compare_list_center_thickness),
    EdgeThicknessMin(Res.string.compare_list_min_edge_thickness),
    EdgeThicknessMax(Res.string.compare_list_max_edge_thickness),
    BaseCurve(Res.string.compare_list_base_curve),
    Diameter(Res.string.compare_list_diameter);

    fun getValue(lens: CalculatedData): String {
        return when (this) {
            Index -> lens.refractionIndex.value.toString()
            Sphere -> lens.spherePower.toFormattedString(2)
            Cylinder -> lens.cylinderPower?.toFormattedString(2) ?: "-"
            Axis -> lens.axis ?: "-"
            AxisThickness -> lens.thicknessOnAxis ?: "-"
            CenterThickness -> lens.thicknessCenter
            EdgeThicknessMin -> lens.thicknessEdge
            EdgeThicknessMax -> lens.thicknessMax ?: "-"
            BaseCurve -> lens.realBaseCurve
            Diameter -> lens.diameter
        }
    }
}
