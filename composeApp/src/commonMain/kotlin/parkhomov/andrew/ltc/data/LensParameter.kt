package parkhomov.andrew.ltc.data

import ltc.composeapp.generated.resources.Res
import ltc.composeapp.generated.resources.axis_img
import ltc.composeapp.generated.resources.cylinder_img
import ltc.composeapp.generated.resources.diam_img
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
import ltc.composeapp.generated.resources.thickness_gauge_img
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource


sealed class LensParameter(
    val titleRes: StringResource,
    val descriptionRes: StringResource,
    val imageRes: DrawableResource
) {
    data object Index : LensParameter(
        titleRes = Res.string.field_title_0,
        descriptionRes = Res.string.field_desc_0,
        imageRes = Res.drawable.index_of_refraction_img
    )

    data object Sphere : LensParameter(
        titleRes = Res.string.field_title_1,
        descriptionRes = Res.string.field_desc_1,
        imageRes = Res.drawable.sphere_img
    )

    data object Cylinder : LensParameter(
        titleRes = Res.string.field_title_2,
        descriptionRes = Res.string.field_desc_2,
        imageRes = Res.drawable.cylinder_img
    )

    data object Axis : LensParameter(
        titleRes = Res.string.field_title_3,
        descriptionRes = Res.string.field_desc_3,
        imageRes = Res.drawable.axis_img
    )

    data object BaseCurve : LensParameter(
        titleRes = Res.string.field_title_4,
        descriptionRes = Res.string.field_desc_4,
        imageRes = Res.drawable.front_curve_img
    )

    data object CenterThickness : LensParameter(
        titleRes = Res.string.field_title_5,
        descriptionRes = Res.string.field_desc_5,
        imageRes = Res.drawable.thickness_gauge_img
    )

    data object EdgeThickness : LensParameter(
        titleRes = Res.string.field_title_6,
        descriptionRes = Res.string.field_desc_6,
        imageRes = Res.drawable.edge_thickness_img
    )

    data object LensDiameter : LensParameter(
        titleRes = Res.string.field_title_7,
        descriptionRes = Res.string.field_desc_7,
        imageRes = Res.drawable.diam_img
    )

    companion object Companion {
        fun getAllFields(): List<LensParameter> = listOf(
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