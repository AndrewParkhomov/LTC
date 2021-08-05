package parkhomov.andrew.lensthicknesscalculator.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GlossaryItem(
    val title: String,
    val description: String,
    var imageId: Int,
    var isContentShown: Boolean = false
) : Parcelable