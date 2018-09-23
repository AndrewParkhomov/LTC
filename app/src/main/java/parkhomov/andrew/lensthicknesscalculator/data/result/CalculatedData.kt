package parkhomov.andrew.lensthicknesscalculator.data.result

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CalculatedData(
        val thicknessCenter: String,
        val thicknessEdge: String,
        val thicknessMax: String?,
        val thicknessOnAxis: String?,
        val axis: String?
) : Parcelable