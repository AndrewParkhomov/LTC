@file:OptIn(ExperimentalMaterial3Api::class)

package parkhomov.andrew.ltc.ui.main.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ltc.composeapp.generated.resources.Res
import ltc.composeapp.generated.resources.tab_thkns_button
import ltc.composeapp.generated.resources.tab_thkns_provide_center_thickness
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import parkhomov.andrew.ltc.data.LensData
import parkhomov.andrew.ltc.data.RefractiveIndex
import parkhomov.andrew.ltc.data.LensParameter
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiEvent
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiState
import parkhomov.andrew.ltc.ui.main.modal.FieldInfoDialog
import androidx.compose.runtime.*
import androidx.compose.ui.focus.onFocusChanged


@Preview
@Composable
fun ThicknessTab(
    modifier: Modifier = Modifier,
    uiData: MainScreenUiState = MainScreenUiState.mock(),
    uiEvent: (MainScreenUiEvent) -> Unit = {},
) {
    var selectedIndex: RefractiveIndex by remember(
        uiData.lensData?.refractiveIndex
    ) { mutableStateOf(uiData.lensData?.refractiveIndex ?: RefractiveIndex.CR39) }

    val inputValues: SnapshotStateMap<LensParameter, String?> = remember(uiData.lensData) {
        mutableStateMapOf(
            LensParameter.Sphere to uiData.lensData?.sphere?.toString(),
            LensParameter.Cylinder to uiData.lensData?.cylinder?.toString(),
            LensParameter.Axis to uiData.lensData?.axis?.toString(),
            LensParameter.BaseCurve to uiData.lensData?.baseCurve?.toString(),
            LensParameter.CenterThickness to uiData.lensData?.centerThickness?.toString(),
            LensParameter.EdgeThickness to uiData.lensData?.edgeThickness?.toString(),
            LensParameter.LensDiameter to uiData.lensData?.diameter?.toString()
        )
    }

    val fieldsEnabledState: Map<LensParameter, Boolean> by rememberFieldsEnabledStateFlow(inputValues)
    LaunchedEffect(fieldsEnabledState) {
        if (fieldsEnabledState[LensParameter.CenterThickness] == false) {
            inputValues[LensParameter.CenterThickness] = null
        }
        if (fieldsEnabledState[LensParameter.EdgeThickness] == false) {
            inputValues[LensParameter.EdgeThickness] = null
        }
    }

    var selectedFieldForInfo by remember { mutableStateOf<LensParameter?>(null) }

    selectedFieldForInfo?.let { field: LensParameter ->
        FieldInfoDialog(
            field = field,
            onDismiss = { selectedFieldForInfo = null }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RefractiveIndexDropdown(
            selectedIndex = selectedIndex,
            onIndexSelected = { selectedIndex = it },
            field = LensParameter.Index,
            onInfoIconClicked = { selectedFieldForInfo = LensParameter.Index }
        )

        LensParameter.getAllFields()
            .drop(1) // dropdown
            .forEach { field: LensParameter ->
                LensInputField(
                    value = inputValues[field] ?: "",
                    onValueChange = { inputValues[field] = it },
                    field = field,
                    enabled = fieldsEnabledState[field] ?: true,
                    error = if (uiData.showCenterThicknessError && field is LensParameter.CenterThickness) {
                        Res.string.tab_thkns_provide_center_thickness
                    } else {
                        null
                    },
                    onInfoClick = { selectedFieldForInfo = field }
                )
            }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val lensData = LensData(
                    refractiveIndex = selectedIndex,
                    sphere = inputValues[LensParameter.Sphere]?.toDoubleOrNull(),
                    cylinder = inputValues[LensParameter.Cylinder]?.toDoubleOrNull(),
                    axis = inputValues[LensParameter.Axis]?.toIntOrNull(),
                    baseCurve = inputValues[LensParameter.BaseCurve]?.toDoubleOrNull(),
                    centerThickness = inputValues[LensParameter.CenterThickness]?.toDoubleOrNull(),
                    edgeThickness = inputValues[LensParameter.EdgeThickness]?.toDoubleOrNull(),
                    diameter = inputValues[LensParameter.LensDiameter]?.toDoubleOrNull()
                )
                uiEvent(MainScreenUiEvent.OnCalculateThickness(lensData))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(Res.string.tab_thkns_button))
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun LensInputField(
    value: String,
    onValueChange: (String) -> Unit,
    field: LensParameter,
    enabled: Boolean = true,
    error: StringResource?,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }

    val textFieldValue = remember(value) {
        TextFieldValue(
            text = value,
            selection = TextRange(value.length)
        )
    }

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newValue: TextFieldValue ->
            onValueChange(newValue.text)
        },
        label = { Text(stringResource(field.titleRes)) },
        enabled = enabled,
        isError = error != null,
        supportingText = error?.let {
            {
                Text(
                    text = stringResource(it),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isFocused && enabled) {
                    IconButton(
                        onClick = { onValueChange("") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Очистити",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(onClick = onInfoClick) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Інформація"
                    )
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        ),
        singleLine = true
    )
}

@Composable
private fun RefractiveIndexDropdown(
    modifier: Modifier = Modifier,
    selectedIndex: RefractiveIndex,
    onIndexSelected: (RefractiveIndex) -> Unit,
    field: LensParameter,
    onInfoIconClicked: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val indices = remember { RefractiveIndex.getAllIndices() }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedIndex.label,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(field.titleRes)) },
            trailingIcon = {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                    IconButton(onClick = onInfoIconClicked) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Інформація"
                        )
                    }
                }
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            indices.forEach { index ->
                DropdownMenuItem(
                    text = { Text(index.label) },
                    onClick = {
                        onIndexSelected(index)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun rememberFieldsEnabledStateFlow(
    inputValues: SnapshotStateMap<LensParameter, String?>
): State<Map<LensParameter, Boolean>> {
    return remember(inputValues) {
        derivedStateOf {
            val sphere = inputValues[LensParameter.Sphere]?.toDoubleOrNull()
            val cylinder = inputValues[LensParameter.Cylinder]?.toDoubleOrNull()

            val effectivePower = when {
                sphere == null -> null
                cylinder != null && cylinder > 0 -> sphere + cylinder
                else -> sphere
            }

            val centerThicknessEnabled = effectivePower?.let { it <= 0 } ?: true
            val edgeThicknessEnabled = effectivePower?.let { it > 0 } ?: true

            mapOf(
                LensParameter.Sphere to true,
                LensParameter.Cylinder to true,
                LensParameter.Axis to true,
                LensParameter.BaseCurve to true,
                LensParameter.CenterThickness to centerThicknessEnabled,
                LensParameter.EdgeThickness to edgeThicknessEnabled,
                LensParameter.LensDiameter to true
            )
        }
    }
}