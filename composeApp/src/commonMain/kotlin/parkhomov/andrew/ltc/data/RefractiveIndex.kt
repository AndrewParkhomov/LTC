package parkhomov.andrew.ltc.data

import androidx.compose.runtime.Immutable

@Immutable
sealed class RefractiveIndex(
    val value: Double,
    val indexX: Double,
    val label: String
) {
    data object CR39 : RefractiveIndex(
        value = 1.498,
        indexX = 1.06425,
        label = "1.498 CR-39"
    )

    data object Index1560 : RefractiveIndex(
        value = 1.535,
        indexX = 0.9909,
        label = "1.560"
    )

    data object Trivex : RefractiveIndex(
        value = 1.53,
        indexX = 0.998,
        label = "1.530 Trivex"
    )

    data object Poly : RefractiveIndex(
        value = 1.586,
        indexX = 0.9044,
        label = "1.590 Poly"
    )

    data object Index1610 : RefractiveIndex(
        value = 1.59,
        indexX = 0.8983,
        label = "1.610"
    )

    data object Index1670 : RefractiveIndex(
        value = 1.66,
        indexX = 0.803,
        label = "1.670"
    )

    data object Index1740 : RefractiveIndex(
        value = 1.727,
        indexX = 0.729,
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