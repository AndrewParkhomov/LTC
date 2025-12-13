package parkhomov.andrew.ltc.data

data class LensData(
    val refractiveIndex: RefractiveIndex,
    val sphere: Double?,
    val cylinder: Double?,
    val axis: Int?,
    val baseCurve: Double?,
    val centerThickness: Double?,
    val edgeThickness: Double?,
    val diameter: Double?
)