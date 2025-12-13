package parkhomov.andrew.ltc.utils


private fun Double.pow(exponent: Int): Double {
    var result = 1.0
    repeat(exponent) { result *= this }
    return result
}

fun Double.toFormattedString(decimals: Int = 2): String {

    val factor = 10.0.pow(decimals)
    val rounded = kotlin.math.round(this * factor) / factor

    val intPart = rounded.toInt()
    val decimalPart = ((rounded - intPart) * factor).toInt()

    return if (decimals > 0) {
        "$intPart.${decimalPart.toString().padStart(decimals, '0')}"
    } else {
        "$intPart"
    }
}