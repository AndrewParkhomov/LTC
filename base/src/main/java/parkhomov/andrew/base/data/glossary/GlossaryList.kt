package parkhomov.andrew.base.data.glossary

import com.google.gson.annotations.SerializedName

data class GlossaryList(
        @SerializedName("data") val data: List<GlossaryData>
)

data class GlossaryData(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("body") val body: String,
        @SerializedName("imageId") var imageId: Int
)