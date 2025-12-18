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
) {
    companion object {
        fun getLensData(
            refractiveIndex: RefractiveIndex,
            inputValues: Map<TabThickness, String?>
        ): LensData {
            return LensData(
                refractiveIndex = refractiveIndex,
                sphere = inputValues[TabThickness.Sphere]?.toDoubleOrNull(),
                cylinder = inputValues[TabThickness.Cylinder]?.toDoubleOrNull(),
                axis = inputValues[TabThickness.Axis]?.toIntOrNull(),
                baseCurve = inputValues[TabThickness.BaseCurve]?.toDoubleOrNull(),
                centerThickness = inputValues[TabThickness.CenterThickness]?.toDoubleOrNull(),
                edgeThickness = inputValues[TabThickness.EdgeThickness]?.toDoubleOrNull(),
                diameter = inputValues[TabThickness.LensDiameter]?.toDoubleOrNull()
            )
        }
    }
}