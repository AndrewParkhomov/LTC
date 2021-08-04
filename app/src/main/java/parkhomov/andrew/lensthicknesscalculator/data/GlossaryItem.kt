package parkhomov.andrew.lensthicknesscalculator.data

data class GlossaryItem(
    val title: String,
    val description: String,
    var imageId: Int,
    var isContentShown: Boolean = false
)