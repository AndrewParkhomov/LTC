@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)

package parkhomov.andrew.ltc.ui.main.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.LocalStrings
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import androidx.compose.ui.tooling.preview.Preview
import parkhomov.andrew.ltc.components.AppOutlineButton
import parkhomov.andrew.ltc.components.LensInputField
import parkhomov.andrew.ltc.data.InputType
import parkhomov.andrew.ltc.data.LensData
import parkhomov.andrew.ltc.data.RefractiveIndexUiModel
import parkhomov.andrew.ltc.data.TabThickness
import parkhomov.andrew.ltc.data.ValidationResult
import parkhomov.andrew.ltc.data.getTitle
import parkhomov.andrew.ltc.provider.getDecimalSignedKeyboard
import parkhomov.andrew.ltc.strings.Strings
import parkhomov.andrew.ltc.ui.main.data.MainScreenUiEvent
import kotlin.time.ExperimentalTime


@Preview
@Composable
fun ThicknessTab(
    modifier: Modifier = Modifier,
    refractiveIndices: ImmutableList<RefractiveIndexUiModel> = persistentListOf(),
    selectedRefractiveIndex: RefractiveIndexUiModel = RefractiveIndexUiModel.getDefaultIndex(),
    thicknessInputValues: SnapshotStateMap<TabThickness, String?> = SnapshotStateMap(),
    fieldsEnabledState: ImmutableMap<TabThickness, Boolean> = persistentMapOf(),
    uiEvent: (MainScreenUiEvent) -> Unit = {},
    onInfoIconClicked: (InputType) -> Unit = {}
) {
    val strings: Strings = LocalStrings.current
    val validationErrors = remember { mutableStateMapOf<TabThickness, ValidationResult.Invalid?>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RefractiveIndexDropdown(
            indices = refractiveIndices,
            selectedIndex = selectedRefractiveIndex,
            onIndexSelected = { uiEvent(MainScreenUiEvent.SelectRefractiveIndex(it)) },
            onAddCustomIndexClick = { uiEvent(MainScreenUiEvent.OnAddCustomIndexClick) },
            onDeleteIndexClick = { uiEvent(MainScreenUiEvent.OnDeleteCustomIndexClick(it)) },
            field = TabThickness.Index,
            onInfoIconClicked = { onInfoIconClicked(TabThickness.Index) }
        )

        TabThickness.getAllFields()
            .drop(1) // dropdown
            .forEach { field: TabThickness ->
                val imeAction: ImeAction = if (field == TabThickness.LensDiameter) {
                    ImeAction.Done
                } else {
                    ImeAction.Next
                }
                LensInputField(
                    value = thicknessInputValues[field] ?: "",
                    onValueChange = { newValue: String ->
                        thicknessInputValues[field] = newValue
                        val result: ValidationResult = field.validation.validate(newValue)
                        validationErrors[field] = when (result) {
                            is ValidationResult.Valid -> null
                            is ValidationResult.Invalid -> result
                        }
                    },
                    inputType = field,
                    enabled = fieldsEnabledState[field] ?: true,
                    keyboardOptions = getDecimalSignedKeyboard().copy(imeAction = imeAction),
                    error = when {
                        validationErrors[field] != null -> validationErrors[field]
                        else -> null
                    },
                    onInfoClick = onInfoIconClicked
                )
            }
        AppOutlineButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = strings.thicknessCalculateButton,
            onClick = {
                val lensData: LensData =
                    LensData.getLensData(selectedRefractiveIndex, thicknessInputValues)
                uiEvent(MainScreenUiEvent.OnCalculateThickness(lensData))
            },
        )
    }
}

@Composable
private fun RefractiveIndexDropdown(
    modifier: Modifier = Modifier,
    indices: ImmutableList<RefractiveIndexUiModel>,
    selectedIndex: RefractiveIndexUiModel,
    onIndexSelected: (RefractiveIndexUiModel) -> Unit,
    onAddCustomIndexClick: () -> Unit,
    onDeleteIndexClick: (RefractiveIndexUiModel) -> Unit,
    field: TabThickness,
    onInfoIconClicked: () -> Unit,
) {
    val strings: Strings = LocalStrings.current
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedIndex.label,
            onValueChange = {},
            readOnly = true,
            label = { Text(field.getTitle(strings)) },
            trailingIcon = {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )

                    IconButton(
                        onClick = onInfoIconClicked
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = strings.contentDescriptionInfo,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.heightIn(max = 450.dp)
        ) {
            val (defaultIndices, customIndices) = indices.partition { !it.isUserCreated }
            val sortedIndices = defaultIndices + customIndices
            sortedIndices.forEach { index ->
                DropdownMenuItem(
                    text = { Text(index.label) },
                    onClick = {
                        onIndexSelected(index)
                        expanded = false
                    },
                    trailingIcon = if (index.isUserCreated) {
                        {
                            IconButton(
                                onClick = { onDeleteIndexClick(index) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = strings.contentDescriptionClose,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    } else null
                )
            }
            HorizontalDivider(modifier = Modifier.padding(top = 4.dp))
            DropdownMenuItem(
                text = { Text(text = strings.addCustomIndex) },
                onClick = {
                    expanded = false
                    onAddCustomIndexClick()
                },
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}