package parkhomov.andrew.ltc.provider

import org.jetbrains.compose.resources.StringResource
import parkhomov.andrew.ltc.data.CalculatedData

expect class ShareManager() {
    fun shareText(text: String, title: String)
}

expect suspend fun StringResource.format(vararg args: Any): String

/**
 * Creates formatted text for sharing calculation results
 */
suspend fun CalculatedData.createShareText(
    appName: String,
    appUrl: String,
    shareTextOnlySphere: StringResource,
    shareTextFull: StringResource
): String {
    return if (cylinderPower == null) {
        // Тільки сфера
        shareTextOnlySphere.format(
            appName,
            appUrl,
            refractionIndex,
            if(spherePower > 0.0){
                "+$spherePower"
            }else{
                spherePower.toString()
            },
            thicknessCenter,
            thicknessEdge,
            realBaseCurve,
            diameter
        )
    } else {
        // Повна інформація з циліндром
        shareTextFull.format(
            appName,
            appUrl,
            refractionIndex,
            if(spherePower > 0.0){
                "+$spherePower"
            }else{
                spherePower.toString()
            },
            cylinderPower.toString(),
            axis ?: "",
            axis ?: "",
            thicknessOnAxis ?: "",
            thicknessCenter,
            thicknessEdge,
            thicknessMax ?: "",
            realBaseCurve,
            diameter
        )
    }
}