package parkhomov.andrew.ltc.data

import androidx.compose.runtime.Immutable
import ltc.composeapp.generated.resources.Res
import ltc.composeapp.generated.resources.axis_img
import ltc.composeapp.generated.resources.cylinder_img
import ltc.composeapp.generated.resources.diam_img
import ltc.composeapp.generated.resources.ed_img
import ltc.composeapp.generated.resources.edge_thickness_img
import ltc.composeapp.generated.resources.field_desc_0
import ltc.composeapp.generated.resources.field_desc_1
import ltc.composeapp.generated.resources.field_desc_2
import ltc.composeapp.generated.resources.field_desc_3
import ltc.composeapp.generated.resources.field_desc_4
import ltc.composeapp.generated.resources.field_desc_5
import ltc.composeapp.generated.resources.field_desc_6
import ltc.composeapp.generated.resources.field_desc_7
import ltc.composeapp.generated.resources.field_title_0
import ltc.composeapp.generated.resources.field_title_1
import ltc.composeapp.generated.resources.field_title_2
import ltc.composeapp.generated.resources.field_title_3
import ltc.composeapp.generated.resources.field_title_4
import ltc.composeapp.generated.resources.field_title_5
import ltc.composeapp.generated.resources.field_title_6
import ltc.composeapp.generated.resources.field_title_7
import ltc.composeapp.generated.resources.front_curve_img
import ltc.composeapp.generated.resources.index_of_refraction_img
import ltc.composeapp.generated.resources.sphere_img
import ltc.composeapp.generated.resources.*
import ltc.composeapp.generated.resources.thickness_gauge_img
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

@Immutable
sealed interface InputType {
    val titleRes: StringResource
    val descriptionRes: StringResource
    val imageRes: DrawableResource
}

@Immutable
sealed class ValidationRule {
    @Immutable
    data class Range(val min: Double, val max: Double) : ValidationRule()

    fun validate(value: String): ValidationResult {
        if (value.isBlank()) return ValidationResult.Valid

        val doubleValue = value.toDoubleOrNull() ?: return ValidationResult.Invalid(
            Res.string.validation_invalid_number
        )

        return when (this) {
            is Range -> {
                when {
                    doubleValue < min -> ValidationResult.Invalid(
                        Res.string.validation_min_value,
                        min.toString()
                    )
                    doubleValue > max -> ValidationResult.Invalid(
                        Res.string.validation_max_value,
                        max.toString()
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
        val message: StringResource,
        val value: String? = null
    ) : ValidationResult()
}

@Immutable
sealed class TabThickness(
    override val titleRes: StringResource,
    override val descriptionRes: StringResource,
    override  val imageRes: DrawableResource,
    val validation: ValidationRule
): InputType {

    @Immutable
    data object Index : TabThickness(
        titleRes = Res.string.field_title_0,
        descriptionRes = Res.string.field_desc_0,
        imageRes = Res.drawable.index_of_refraction_img,
        validation = ValidationRule.Range(-40.0, 40.0)
    )

    @Immutable
    data object Sphere : TabThickness(
        titleRes = Res.string.field_title_1,
        descriptionRes = Res.string.field_desc_1,
        imageRes = Res.drawable.sphere_img,
        validation = ValidationRule.Range(-40.0, 40.0)
    )

    @Immutable
    data object Cylinder : TabThickness(
        titleRes = Res.string.field_title_2,
        descriptionRes = Res.string.field_desc_2,
        imageRes = Res.drawable.cylinder_img,
        validation = ValidationRule.Range(-10.0, 10.0)
    )

    @Immutable
    data object Axis : TabThickness(
        titleRes = Res.string.field_title_3,
        descriptionRes = Res.string.field_desc_3,
        imageRes = Res.drawable.axis_img,
        validation = ValidationRule.Range(0.0, 180.0)
    )

    @Immutable
    data object BaseCurve : TabThickness(
        titleRes = Res.string.field_title_4,
        descriptionRes = Res.string.field_desc_4,
        imageRes = Res.drawable.front_curve_img,
        validation = ValidationRule.Range(0.0, 15.0)
    )

    @Immutable
    data object CenterThickness : TabThickness(
        titleRes = Res.string.field_title_5,
        descriptionRes = Res.string.field_desc_5,
        imageRes = Res.drawable.thickness_gauge_img,
        validation = ValidationRule.Range(0.0, 15.0)
    )

    @Immutable
    data object EdgeThickness : TabThickness(
        titleRes = Res.string.field_title_6,
        descriptionRes = Res.string.field_desc_6,
        imageRes = Res.drawable.edge_thickness_img,
        validation = ValidationRule.Range(0.0, 25.0)
    )

    @Immutable
    data object LensDiameter : TabThickness(
        titleRes = Res.string.field_title_7,
        descriptionRes = Res.string.field_desc_7,
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
    override val titleRes: StringResource,
    override val descriptionRes: StringResource,
    override  val imageRes: DrawableResource
): InputType {
    @Immutable
    data object EffectiveDiameter : TabDiameter(
        titleRes = Res.string.field_title_8,
        descriptionRes = Res.string.field_desc_8,
        imageRes = Res.drawable.ed_img
    )
    @Immutable
    data object DistanceBetweenLenses : TabDiameter(
        titleRes = Res.string.field_title_9,
        descriptionRes = Res.string.field_desc_9,
        imageRes = Res.drawable.dbl_img
    )
    @Immutable
    data object PupilDistance : TabDiameter(
        titleRes = Res.string.field_title_10,
        descriptionRes = Res.string.field_desc_10,
        imageRes = Res.drawable.pd_img
    )


    companion object {
        fun getAllFields(): List<TabDiameter> = listOf(
            EffectiveDiameter,
            DistanceBetweenLenses,
            PupilDistance
        )
    }
}