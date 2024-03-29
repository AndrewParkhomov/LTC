package parkhomov.andrew.lensthicknesscalculator.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GlossaryItem(
    val title: String,
    val description: String,
    var imageId: Int,
) : Parcelable