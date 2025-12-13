@file:OptIn(ExperimentalMaterial3Api::class)

package parkhomov.andrew.ltc.ui.main.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ltc.composeapp.generated.resources.Res
import ltc.composeapp.generated.resources.tab_thkns_button
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import parkhomov.andrew.ltc.data.LensData
import parkhomov.andrew.ltc.data.RefractiveIndex
import parkhomov.andrew.ltc.data.ThicknessField
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiEvent
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiState
import parkhomov.andrew.ltc.ui.main.modal.FieldInfoDialog

@Preview
@Composable
fun ThicknessTab(
    modifier: Modifier = Modifier,
    uiData: MainScreenUiState = MainScreenUiState.mock(),
    uiEvent: (MainScreenUiEvent) -> Unit = {},
) {
    var selectedIndex: RefractiveIndex by remember { mutableStateOf(RefractiveIndex.CR39) }

    // Поля для введення (порядок відповідає ThicknessField)
    val inputValues = remember(uiData.frontCurve) {
        mutableStateMapOf(
            ThicknessField.Index to "",      // Буде показано як dropdown
            ThicknessField.Sphere to "",
            ThicknessField.Cylinder to "",
            ThicknessField.Axis to "",
            ThicknessField.BaseCurve to uiData.frontCurve,
            ThicknessField.CenterThickness to "",
            ThicknessField.EdgeThickness to "",
            ThicknessField.LensDiameter to ""
        )
    }

    var selectedFieldForInfo by remember { mutableStateOf<ThicknessField?>(null) }

    selectedFieldForInfo?.let { field ->
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
        // Індекс заломлення (особливий випадок - dropdown)
        RefractiveIndexDropdown(
            selectedIndex = selectedIndex,
            onIndexSelected = { selectedIndex = it },
            field = ThicknessField.Index,
            onInfoIconClicked = { selectedFieldForInfo = ThicknessField.Index }
        )

        // Решта полів (генеруємо автоматично)
        ThicknessField.getAllFields()
            .drop(1) // Пропускаємо Index (вже показали як dropdown)
            .forEach { field ->
                LensInputField(
                    value = inputValues[field] ?: "",
                    onValueChange = { inputValues[field] = it },
                    field = field,
                    onInfoClick = { selectedFieldForInfo = field }
                )
            }

        Spacer(modifier = Modifier.height(8.dp))

        // Кнопка розрахувати
        Button(
            onClick = {
                val lensData = LensData(
                    refractiveIndex = selectedIndex,
                    sphere = inputValues[ThicknessField.Sphere]?.toDoubleOrNull(),
                    cylinder = inputValues[ThicknessField.Cylinder]?.toDoubleOrNull(),
                    axis = inputValues[ThicknessField.Axis]?.toIntOrNull(),
                    baseCurve = inputValues[ThicknessField.BaseCurve]?.toDoubleOrNull(),
                    centerThickness = inputValues[ThicknessField.CenterThickness]?.toDoubleOrNull(),
                    edgeThickness = inputValues[ThicknessField.EdgeThickness]?.toDoubleOrNull(),
                    diameter = inputValues[ThicknessField.LensDiameter]?.toDoubleOrNull()
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
    field: ThicknessField,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(field.titleRes)) },
        trailingIcon = {
            IconButton(onClick = onInfoClick) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Інформація"
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        ),
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun RefractiveIndexDropdown(
    modifier: Modifier = Modifier,
    selectedIndex: RefractiveIndex,
    onIndexSelected: (RefractiveIndex) -> Unit,
    field: ThicknessField,
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