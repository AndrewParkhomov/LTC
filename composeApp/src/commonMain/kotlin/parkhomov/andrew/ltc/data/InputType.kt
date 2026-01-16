package parkhomov.andrew.ltc.data

import androidx.compose.runtime.Immutable
import ltc.composeapp.generated.resources.Res
import ltc.composeapp.generated.resources.axis_img
import ltc.composeapp.generated.resources.cylinder_img
import ltc.composeapp.generated.resources.diam_img
import ltc.composeapp.generated.resources.ed_img
import ltc.composeapp.generated.resources.edge_thickness_img
import ltc.composeapp.generated.resources.front_curve_img
import ltc.composeapp.generated.resources.index_of_refraction_img
import ltc.composeapp.generated.resources.sphere_img
import ltc.composeapp.generated.resources.*
import ltc.composeapp.generated.resources.thickness_gauge_img
import org.jetbrains.compose.resources.DrawableResource
import parkhomov.andrew.ltc.strings.Strings

@Immutable
sealed interface InputType {
    val imageRes: DrawableResource
}

@Immutable
sealed class ValidationRule {
    @Immutable
    data class Range(
        val min: Double,
        val max: Double,
        val decimalPlaces: Int = 2
    ) : ValidationRule() {
        val allowsNegative: Boolean = min < 0

        val maxLength: Int by lazy {
            val maxAbsValue = maxOf(kotlin.math.abs(min), kotlin.math.abs(max))
            val integerDigits = maxAbsValue.toInt().toString().length
            val signChar = if (allowsNegative) 1 else 0
            val decimalPart = if (decimalPlaces > 0) 1 + decimalPlaces else 0
            signChar + integerDigits + decimalPart
        }

        val allowedChars: Set<Char> by lazy {
            val baseChars = ('0'..'9').toSet() + '.'
            if (allowsNegative) baseChars + '-' + '+' else baseChars
        }
    }

    fun validate(value: String): ValidationResult {
        if (value.isBlank()) return ValidationResult.Valid

        val doubleValue = value.toDoubleOrNull() ?: return ValidationResult.Invalid(
            getMessage = { it.validationInvalidNumber }
        )

        return when (this) {
            is Range -> {
                when {
                    doubleValue < min -> ValidationResult.Invalid(
                        getMessage = { it.validationMinValue(min.toString()) },
                        value = min.toString()
                    )
                    doubleValue > max -> ValidationResult.Invalid(
                        getMessage = { it.validationMaxValue(max.toString()) },
                        value = max.toString()
                    )
                    else -> ValidationResult.Valid
                }
            }
        }
    }
}

@Immutable
sealed class ValidationResult {
    @Immutable
    data object Valid : ValidationResult()
    @Immutable
    data class Invalid(
        val getMessage: (Strings) -> String,
        val value: String? = null
    ) : ValidationResult()
}

@Immutable
sealed class TabThickness(
    override val imageRes: DrawableResource,
    val validation: ValidationRule.Range
): InputType {

    @Immutable
    data object Index : TabThickness(
        imageRes = Res.drawable.index_of_refraction_img,
        validation = ValidationRule.Range(-40.0, 40.0)
    )

    @Immutable
    data object Sphere : TabThickness(
        imageRes = Res.drawable.sphere_img,
        validation = ValidationRule.Range(-40.0, 40.0)
    )

    @Immutable
    data object Cylinder : TabThickness(
        imageRes = Res.drawable.cylinder_img,
        validation = ValidationRule.Range(-10.0, 10.0)
    )

    @Immutable
    data object Axis : TabThickness(
        imageRes = Res.drawable.axis_img,
        validation = ValidationRule.Range(0.0, 180.0)
    )

    @Immutable
    data object BaseCurve : TabThickness(
        imageRes = Res.drawable.front_curve_img,
        validation = ValidationRule.Range(0.0, 15.0)
    )

    @Immutable
    data object CenterThickness : TabThickness(
        imageRes = Res.drawable.thickness_gauge_img,
        validation = ValidationRule.Range(0.0, 15.0)
    )

    @Immutable
    data object EdgeThickness : TabThickness(
        imageRes = Res.drawable.edge_thickness_img,
        validation = ValidationRule.Range(0.0, 25.0)
    )

    @Immutable
    data object LensDiameter : TabThickness(
        imageRes = Res.drawable.diam_img,
        validation = ValidationRule.Range(0.0, 100.0)
    )

    @Immutable
    companion object Companion {
        fun getAllFields(): List<TabThickness> = listOf(
            Index,
            Sphere,
            Cylinder,
            Axis,
            BaseCurve,
            CenterThickness,
            EdgeThickness,
            LensDiameter
        )
    }
}

@Immutable
sealed class TabDiameter(
    override val imageRes: DrawableResource,
    val validation: ValidationRule.Range
): InputType {
    @Immutable
    data object EffectiveDiameter : TabDiameter(
        imageRes = Res.drawable.ed_img,
        validation = ValidationRule.Range(min = 30.0, max = 80.0, decimalPlaces = 0)
    )
    @Immutable
    data object DistanceBetweenLenses : TabDiameter(
        imageRes = Res.drawable.dbl_img,
        validation = ValidationRule.Range(min = 10.0, max = 30.0, decimalPlaces = 0)
    )
    @Immutable
    data object PupilDistance : TabDiameter(
        imageRes = Res.drawable.pd_img,
        validation = ValidationRule.Range(min = 40.0, max = 80.0, decimalPlaces = 0)
    )

    companion object {
        fun getAllFields(): List<TabDiameter> = listOf(
            EffectiveDiameter,
            DistanceBetweenLenses,
            PupilDistance
        )
    }
}

fun InputType.getTitle(strings: Strings): String = when (this) {
    is TabThickness.Index -> strings.infoIndexOfRefractionTitle
    is TabThickness.Sphere -> strings.infoSpherePowerTitle
    is TabThickness.Cylinder -> strings.infoCylinderPowerTitle
    is TabThickness.Axis -> strings.infoAxisTitle
    is TabThickness.BaseCurve -> strings.infoBaseCurveTitle
    is TabThickness.CenterThickness -> strings.infoCenterThicknessTitle
    is TabThickness.EdgeThickness -> strings.infoEdgeThicknessTitle
    is TabThickness.LensDiameter -> strings.infoLensDiameterTitle
    is TabDiameter.EffectiveDiameter -> strings.infoEffectiveDiameterTitle
    is TabDiameter.DistanceBetweenLenses -> strings.infoDistanceBetweenLensesTitle
    is TabDiameter.PupilDistance -> strings.infoPupilDistanceTitle
}

fun InputType.getDescription(strings: Strings): String = when (this) {
    is TabThickness.Index -> strings.infoIndexOfRefractionDesc
    is TabThickness.Sphere -> strings.infoSpherePowerDesc
    is TabThickness.Cylinder -> strings.infoCylinderPowerDesc
    is TabThickness.Axis -> strings.infoAxisDesc
    is TabThickness.BaseCurve -> strings.infoBaseCurveDesc
    is TabThickness.CenterThickness -> strings.infoCenterThicknessDesc
    is TabThickness.EdgeThickness -> strings.infoEdgeThicknessDesc
    is TabThickness.LensDiameter -> strings.infoLensDiameterDesc
    is TabDiameter.EffectiveDiameter -> strings.infoEffectiveDiameterDesc
    is TabDiameter.DistanceBetweenLenses -> strings.infoDistanceBetweenLensesDesc
    is TabDiameter.PupilDistance -> strings.infoPupilDistanceDesc
}

