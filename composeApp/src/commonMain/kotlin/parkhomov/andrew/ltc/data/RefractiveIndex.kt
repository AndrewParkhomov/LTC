package parkhomov.andrew.ltc.data

import androidx.compose.runtime.Immutable

private const val THICKNESS_FACTOR: Double = 0.53

@Immutable
sealed class RefractiveIndex(
    val value: Double,
    val label: String
) {
    // indexX = THICKNESS_FACTOR / (value - 1)
    val indexX: Double get() = THICKNESS_FACTOR / (value - 1)

    data object CR39 : RefractiveIndex(
        value = 1.498,
        label   = "1.498 CR-39"
    )

    data object Index1560 : RefractiveIndex(
        value = 1.535,
        label = "1.560"
    )

    data object Trivex : RefractiveIndex(
        value = 1.53,
        label = "1.530 Trivex"
    )

    data object Poly : RefractiveIndex(
        value = 1.586,
        label = "1.590 Poly"
    )

    data object Index1610 : RefractiveIndex(
        value = 1.59,
        label = "1.610"
    )

    data object Index1670 : RefractiveIndex(
        value = 1.66,
        label = "1.670"
    )

    data object Index1740 : RefractiveIndex(
        value = 1.727,
        label = "1.740"
    )

    companion object {
        fun getAllIndices(): List<RefractiveIndex> = listOf(
            CR39,
            Index1560,
            Trivex,
            Poly,
            Index1610,
            Index1670,
            Index1740
        )

        fun fromValue(value: Double): RefractiveIndex? {
            return getAllIndices().find { it.value == value }
        }
    }
}