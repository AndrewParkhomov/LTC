package parkhomov.andrew.ltc.utils

import parkhomov.andrew.ltc.data.ValidationRule
import kotlin.math.abs

object InputValidator {

    fun filterInput(
        input: String,
        validation: ValidationRule.Range
    ): String {
        val filtered = input.filter { it in validation.allowedChars }

        if (filtered.isEmpty()) return filtered

        val hasSign = filtered.firstOrNull() in setOf('+', '-')
        val signPart = if (hasSign) filtered.first().toString() else ""
        val numberPart = if (hasSign) filtered.drop(1) else filtered

        val parts = numberPart.split('.')
        val integerPart = parts.getOrElse(0) { "" }
        val decimalPart = parts.getOrElse(1) { null }
        val hasDecimalPoint = numberPart.contains('.')

        val maxIntegerDigits = calculateMaxIntegerDigits(validation)

        val limitedIntegerPart = integerPart.take(maxIntegerDigits)
        val limitedDecimalPart = decimalPart?.take(validation.decimalPlaces)

        return buildString {
            append(signPart)
            append(limitedIntegerPart)
            if (hasDecimalPoint && validation.decimalPlaces > 0) {
                append('.')
                if (limitedDecimalPart != null) {
                    append(limitedDecimalPart)
                }
            }
        }
    }

    private fun calculateMaxIntegerDigits(validation: ValidationRule.Range): Int {
        val maxAbsValue = maxOf(abs(validation.min), abs(validation.max))
        return maxAbsValue.toInt().toString().length
    }

    fun filterInput(
        input: String,
        maxLength: Int,
        allowedChars: Set<Char>
    ): String {
        return input
            .filter { it in allowedChars }
            .take(maxLength)
    }
}
