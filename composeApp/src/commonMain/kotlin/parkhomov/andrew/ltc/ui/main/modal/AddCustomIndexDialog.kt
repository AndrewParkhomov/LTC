package parkhomov.andrew.ltc.ui.main.modal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.LocalStrings
import parkhomov.andrew.ltc.provider.getAlphanumericKeyboard

@Composable
fun AddCustomIndexDialog(
    onSave: (label: String, value: Double) -> Unit,
    onDismiss: () -> Unit
) {
    val strings = LocalStrings.current
    var indexName by remember { mutableStateOf("") }
    var indexValue by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    val isValidValue = indexValue.toDoubleOrNull()?.let { it > 1.0 && it < 2.0 } ?: false
    val isSaveEnabled = indexName.isNotBlank() && isValidValue

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        title = {
            Text(
                text = strings.addCustomIndexTitle,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = indexName,
                    onValueChange = { indexName = it },
                    label = { Text(strings.customIndexNameLabel) },
                    singleLine = true,
                    keyboardOptions = getAlphanumericKeyboard().copy(
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                )

                OutlinedTextField(
                    value = indexValue,
                    onValueChange = { indexValue = it },
                    label = { Text(strings.customIndexValueLabel) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    isError = indexValue.isNotEmpty() && !isValidValue,
                    supportingText = if (indexValue.isNotEmpty() && !isValidValue) {
                        { Text(strings.validationMinValue("1.0") + ", " + strings.validationMaxValue("2.0")) }
                    } else null,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismiss) {
                    Text(strings.buttonCancel)
                }
                TextButton(
                    onClick = {
                        indexValue.toDoubleOrNull()?.let { value ->
                            onSave(indexName, value)
                        }
                    },
                    enabled = isSaveEnabled
                ) {
                    Text(strings.buttonSave)
                }
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}
