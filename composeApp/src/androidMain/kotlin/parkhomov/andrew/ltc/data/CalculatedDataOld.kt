package parkhomov.andrew.ltc.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalculatedDataOld(
        val refractionIndex: String,
        val spherePower: Double,
        val cylinderPower: Double?,
        val axis: String?,
        val thicknessOnAxis: String?,
        val thicknessCenter: String,
        val thicknessEdge: String,
        val thicknessMax: String?,
        val realBaseCurve: String,
        val diameter: String
) : Parcelable