package parkhomov.andrew.ltc.analytics

import parkhomov.andrew.ltc.data.CalculatedData
import parkhomov.andrew.ltc.data.DiameterData

sealed class AnalyticsEvent(val name: String, val params: Map<String, Any?>) {

    class LensCalculation(data: CalculatedData) : AnalyticsEvent(
        name = "lens_calculation",
        params = mapOf(
            "sphere_power" to data.spherePower,
            "cylinder_power" to data.cylinderPower,
            "axis" to data.axis,
            "diameter" to data.diameter,
            "refractive_index" to data.refractionIndex.value,
            "refractive_index_label" to data.refractionIndex.label,
            "thickness_center" to data.thicknessCenter,
            "thickness_edge" to data.thicknessEdge,
            "thickness_max" to data.thicknessMax
        )
    )

    class DiameterCalculation(data: DiameterData) : AnalyticsEvent(
        name = "diameter_calculation",
        params = mapOf(
            "effective_diameter" to data.effectiveDiameter,
            "distance_between_lenses" to data.distanceBetweenLenses,
            "pupil_distance" to data.pupilDistance,
            "result" to data.result
        )
    )
}
