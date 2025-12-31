package parkhomov.andrew.ltc.ui.main.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.lyricist.rememberStrings
import org.jetbrains.compose.ui.tooling.preview.Preview
import parkhomov.andrew.ltc.strings.Strings
import parkhomov.andrew.ltc.components.LensInputField
import parkhomov.andrew.ltc.data.InputType
import parkhomov.andrew.ltc.data.TabDiameter
import parkhomov.andrew.ltc.provider.getDecimalSignedKeyboard
import parkhomov.andrew.ltc.theme.AppTheme
import parkhomov.andrew.ltc.theme.ThemeMode
import parkhomov.andrew.ltc.theme.isDarkTheme
import parkhomov.andrew.ltc.utils.toFormattedString
import kotlin.math.ceil

@Preview(showBackground = true)
@Composable
fun DiameterTab(
    modifier: Modifier = Modifier,
    diameterInputValues: SnapshotStateMap<TabDiameter, String?> = SnapshotStateMap(),
    onInfoIconClicked: (InputType) -> Unit = {}
) {
    val strings: Strings = rememberStrings().strings

    val effectiveDiameter: String = diameterInputValues[TabDiameter.EffectiveDiameter].orEmpty()
    val distanceBetweenLenses = diameterInputValues[TabDiameter.DistanceBetweenLenses].orEmpty()
    val pupilDistance = diameterInputValues[TabDiameter.PupilDistance].orEmpty()

    val calculatedDiameter: Double = remember(
        effectiveDiameter,
        distanceBetweenLenses,
        pupilDistance
    ) {
        val edValue: Double = effectiveDiameter.toDoubleOrNull() ?: 0.0
        val dblValue: Double = distanceBetweenLenses.toDoubleOrNull() ?: 0.0
        val pdValue: Double = pupilDistance.toDoubleOrNull() ?: 0.0

        if (edValue > 0 && dblValue > 0 && pdValue > 0) {
            val result: Double = ceil(edValue * 2 + dblValue - pdValue)
            if (result > 0) {
                result
            } else {
                0.0
            }
        } else {
            0.0
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TabDiameter.getAllFields().forEach { field: TabDiameter ->
            val imeAction: ImeAction = if (field == TabDiameter.PupilDistance) {
                ImeAction.Done
            } else {
                ImeAction.Next
            }

            LensInputField(
                modifier = Modifier.fillMaxWidth(),
                value = diameterInputValues[field].orEmpty(),
                inputType = field,
                keyboardOptions = getDecimalSignedKeyboard().copy(
                    imeAction = imeAction
                ),
                enabled = true,
                error = null,
                onValueChange = { diameterInputValues[field] = it },
                onInfoClick = onInfoIconClicked
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = strings.diameterCalculationResult(
                    calculatedDiameter.toFormattedString(1)
                ),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = if(isDarkTheme()){
                    MaterialTheme.colorScheme.primary
                }else{
                    MaterialTheme.colorScheme.onPrimary
                },
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun DiameterTabDarkPreview(){
    AppTheme(themeMode = ThemeMode.DARK) {
        DiameterTab(
            modifier = Modifier,
            diameterInputValues = SnapshotStateMap(),
            onInfoIconClicked = {}
        )
    }
}


@Preview
@Composable
private fun DiameterTabLightPreview(){
    AppTheme(themeMode = ThemeMode.LIGHT) {
        DiameterTab(
            modifier = Modifier,
            diameterInputValues = SnapshotStateMap(),
            onInfoIconClicked = {}
        )
    }
}
