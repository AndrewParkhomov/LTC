package parkhomov.andrew.ltc.ui.main.modal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.LocalStrings
import androidx.compose.ui.tooling.preview.Preview
import parkhomov.andrew.ltc.components.AppDialog
import parkhomov.andrew.ltc.strings.Strings
import parkhomov.andrew.ltc.strings.Locales
import parkhomov.andrew.ltc.theme.ThemeMode

@Preview
@Composable
fun SettingsDialog(
    currentLanguage: String = Locales.EN,
    currentTheme: ThemeMode = ThemeMode.SYSTEM,
    appVersion: String = "5.0.2",
    onLanguageSelected: (String) -> Unit = {},
    onThemeSelected: (ThemeMode) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val strings: Strings = LocalStrings.current

    AppDialog(
        onDismiss = onDismiss,
        title = {
            Text(
                text = strings.settingsTitle,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = strings.settingsLanguageTitle,
                    style = MaterialTheme.typography.bodyLarge
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                LanguageSelection(
                    strings = strings,
                    currentLanguage = currentLanguage,
                    onLanguageSelected = onLanguageSelected
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = strings.settingsThemeTitle,
                    style = MaterialTheme.typography.bodyLarge
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                ThemeSelection(
                    currentTheme = currentTheme,
                    onThemeSelected = onThemeSelected
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "v$appVersion",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }
    )
}

@Composable
private fun LanguageSelection(
    strings: Strings,
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    val languages = listOf(
        Language(Locales.EN, strings.settingsLanguageEnglish),
        Language(Locales.PT, strings.settingsLanguagePortuguese),
        Language(Locales.UK, strings.settingsLanguageUkrainian),
        Language(Locales.HI, strings.settingsLanguageHindi)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        languages.forEach { language ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLanguageSelected(language.code) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentLanguage == language.code,
                    onClick = { onLanguageSelected(language.code) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = language.name,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun ThemeSelection(
    currentTheme: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit
) {
    val strings: Strings = LocalStrings.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        ThemeMode.entries.forEach { theme: ThemeMode ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onThemeSelected(theme) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                    selected = currentTheme == theme,
                    onClick = { onThemeSelected(theme) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = theme.getLabel(strings),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

data class Language(
    val code: String,
    val name: String
)
