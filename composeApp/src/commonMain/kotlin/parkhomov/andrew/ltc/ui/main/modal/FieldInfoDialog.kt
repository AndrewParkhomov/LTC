package parkhomov.andrew.ltc.ui.main.modal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.Lyricist
import cafe.adriel.lyricist.rememberStrings
import org.jetbrains.compose.resources.painterResource
import parkhomov.andrew.ltc.data.InputType
import parkhomov.andrew.ltc.data.getDescription
import parkhomov.andrew.ltc.data.getTitle
import parkhomov.andrew.ltc.strings.Strings

@Composable
fun FieldInfoDialog(
    inputType: InputType,
    onDismiss: () -> Unit
) {
    val strings: Lyricist<Strings> = rememberStrings()

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = inputType.getTitle(strings.strings),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(inputType.imageRes),
                    contentDescription = inputType.getTitle(strings.strings),
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )

                Text(
                    text = inputType.getDescription(strings.strings),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("OK")
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}