package parkhomov.andrew.ltc.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import parkhomov.andrew.ltc.theme.isDarkTheme

@Composable
fun AppOutlineButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val borderColor by animateColorAsState(
        targetValue =
            if (isPressed) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colorScheme.outline
            },
        animationSpec = tween(durationMillis = 150),
        label = "borderColor",
    )

    val textColor by animateColorAsState(
        targetValue =
            when {
                !enabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                isPressed -> MaterialTheme.colorScheme.onPrimaryContainer
                else -> MaterialTheme.colorScheme.onPrimary
            },
        animationSpec = tween(durationMillis = 150),
        label = "textColor",
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "scale",
    )

    OutlinedButton(
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        border =
            BorderStroke(
                width = 1.dp,
                color =
                    if (enabled) {
                        borderColor
                    } else {
                        MaterialTheme.colorScheme.outline.copy(
                            alpha = 0.12f,
                        )
                    },
            ),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor =
                    if (isDarkTheme()) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
        interactionSource = interactionSource,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
        )
    }
}

@Composable
fun AppOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val borderColor by animateColorAsState(
        targetValue =
            if (isPressed) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colorScheme.outline
            },
        animationSpec = tween(durationMillis = 150),
        label = "borderColor",
    )

    val textColor by animateColorAsState(
        targetValue =
            when {
                !enabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                isPressed -> MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                else -> MaterialTheme.colorScheme.primary
            },
        animationSpec = tween(durationMillis = 150),
        label = "textColor",
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "scale",
    )

    OutlinedButton(
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        border =
            BorderStroke(
                width = 1.dp,
                color =
                    if (enabled) {
                        borderColor
                    } else {
                        MaterialTheme.colorScheme.outline.copy(
                            alpha = 0.12f,
                        )
                    },
            ),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor =
                    MaterialTheme.colorScheme.surfaceVariant.copy(
                        alpha = 0.5f,
                    ),
                disabledContentColor =
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.4f,
                    ),
            ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
        interactionSource = interactionSource,
    ) {
        if (leadingIcon != null) {
            leadingIcon()
        }

        Text(
            modifier = Modifier,
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
        )

        if (trailingIcon != null) {
            trailingIcon()
        }
    }
}
