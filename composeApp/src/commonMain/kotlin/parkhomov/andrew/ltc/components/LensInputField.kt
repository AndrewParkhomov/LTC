package parkhomov.andrew.ltc.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import parkhomov.andrew.ltc.data.InputType
import parkhomov.andrew.ltc.provider.getDecimalSignedKeyboard

@Composable
fun LensInputField(
    modifier: Modifier = Modifier,
    value: String,
    inputType: InputType,
    keyboardOptions: KeyboardOptions = getDecimalSignedKeyboard(),
    enabled: Boolean = true,
    error: StringResource?,
    onValueChange: (String) -> Unit,
    onInfoClick: (InputType) -> Unit
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
        label = { Text(stringResource(inputType.titleRes)) },
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

                IconButton(onClick = { onInfoClick(inputType) }) {
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
        keyboardOptions = keyboardOptions,
        singleLine = true
    )
}