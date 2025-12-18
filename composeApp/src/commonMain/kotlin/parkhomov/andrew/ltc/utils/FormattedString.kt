package parkhomov.andrew.ltc.utils

import kotlin.math.abs
import kotlin.math.round

private fun Double.pow(exponent: Int): Double {
    var result = 1.0
    repeat(exponent) { result *= this }
    return result
}

fun Double.toFormattedString(decimals: Int = 2): String {
    val factor = 10.0.pow(decimals)
    val rounded = round(this * factor) / factor

    val sign = if (rounded < 0) "-" else ""
    val absValue = abs(rounded)

    val intPart = absValue.toInt()
    val decimalPart = ((absValue - intPart) * factor).toInt()

    return if (decimals > 0) {
        "$sign$intPart.${decimalPart.toString().padStart(decimals, '0')}"
    } else {
        "$sign$intPart"
    }
}