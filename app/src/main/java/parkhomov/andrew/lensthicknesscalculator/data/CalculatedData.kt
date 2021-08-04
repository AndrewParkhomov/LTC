package parkhomov.andrew.lensthicknesscalculator.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CalculatedData(
        val refractionIndex: String,
        val spherePower: String,
        val cylinderPower: String?,
        val axis: String?,
        val thicknessOnAxis: String?,
        val thicknessCenter: String,
        val thicknessEdge: String,
        val thicknessMax: String?,
        val realBaseCurve: String,
        val diameter: String
) : Parcelable