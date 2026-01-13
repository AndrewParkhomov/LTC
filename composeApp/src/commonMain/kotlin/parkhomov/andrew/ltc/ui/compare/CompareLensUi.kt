@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)

package parkhomov.andrew.ltc.ui.compare

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.unit.sp
import cafe.adriel.lyricist.LocalStrings
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.ui.tooling.preview.Preview
import parkhomov.andrew.ltc.strings.Strings
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
    val strings: Strings = LocalStrings.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(strings.compareListTitle) },
                navigationIcon = {
                    IconButton(onClick = { uiEvent(CompareLensScreenUiEvent.CloseScreen) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = strings.contentDescriptionBack)
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
    lenses: ImmutableList<CalculatedData>,
    onClearList: () -> Unit,
    modifier: Modifier = Modifier
) {
    val strings: Strings = LocalStrings.current

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                TableHeaderCell(text = "", isLabel = true, modifier = Modifier.weight(1f))

                CompareRow.entries.forEach { row: CompareRow ->
                    TableLabelCell(text = row.getLabel(strings), modifier = Modifier.weight(1f))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.surface)
                    .horizontalScroll(rememberScrollState())
            ) {
                lenses.forEach { lens: CalculatedData ->
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(100.dp)
                    ) {
                        TableHeaderCell(
                            text = lens.refractionIndex.label,
                            isLabel = false,
                            modifier = Modifier.weight(1f)
                        )

                        CompareRow.entries.forEach { row: CompareRow ->
                            TableDataCell(text = row.getValue(lens), modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
        AppOutlineButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = strings.compareListClearButton,
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
            style = MaterialTheme.typography.labelLarge,
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
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 18.sp,
            maxLines = 2
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
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }
}

enum class CompareRow {
    Index,
    Sphere,
    Cylinder,
    Axis,
    AxisThickness,
    CenterThickness,
    EdgeThicknessMin,
    EdgeThicknessMax,
    BaseCurve,
    Diameter;

    fun getLabel(strings: Strings): String {
        return when (this) {
            Index -> strings.compareListIndexColumn
            Sphere -> strings.compareListSphereColumn
            Cylinder -> strings.compareListCylinderColumn
            Axis -> strings.compareListAxisColumn
            AxisThickness -> strings.compareListThicknessAxisColumn
            CenterThickness -> strings.compareListThicknessCenterColumn
            EdgeThicknessMin -> strings.compareListThicknessMinColumn
            EdgeThicknessMax -> strings.compareListThicknessMaxColumn
            BaseCurve -> strings.compareListBaseCurveColumn
            Diameter -> strings.compareListDiameterColumn
        }
    }

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
