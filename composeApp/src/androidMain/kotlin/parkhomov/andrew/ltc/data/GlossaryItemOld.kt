package parkhomov.andrew.ltc.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GlossaryItemOld(
    val title: String,
    val description: String,
    val imageId: Int,
) : Parcelable