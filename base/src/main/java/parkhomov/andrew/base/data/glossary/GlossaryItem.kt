package parkhomov.andrew.base.data.glossary

import com.google.gson.annotations.SerializedName

data class GlossaryItem(
        @SerializedName("data") val data: List<Data>
)

data class Data(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("body") val body: String,
        @SerializedName("imageId") var imageId: Int
)