package parkhomov.andrew.ltc.strings

data class Strings(
    // App name
    val appName: String,
    val appNameFull: String,
    val buttonOk: String,

    // Tab headers
    val tabThickness: String,
    val tabDiameter: String,

    // Thickness tab
    val thicknessProvideCenterHint: String,
    val thicknessCalculateButton: String,

    // Validation
    val validationInvalidNumber: String,
    val validationMinValue: (minValue: String) -> String,
    val validationMaxValue: (maxValue: String) -> String,

    // Info dialog - Index of refraction
    val infoIndexOfRefractionTitle: String,
    val infoIndexOfRefractionDesc: String,

    // Info dialog - Sphere power
    val infoSpherePowerTitle: String,
    val infoSpherePowerDesc: String,

    // Info dialog - Cylinder power
    val infoCylinderPowerTitle: String,
    val infoCylinderPowerDesc: String,

    // Info dialog - Axis
    val infoAxisTitle: String,
    val infoAxisDesc: String,

    // Info dialog - Real base curve
    val infoBaseCurveTitle: String,
    val infoBaseCurveDesc: String,

    // Info dialog - Center thickness
    val infoCenterThicknessTitle: String,
    val infoCenterThicknessDesc: String,

    // Info dialog - Edge thickness
    val infoEdgeThicknessTitle: String,
    val infoEdgeThicknessDesc: String,

    // Info dialog - Lens diameter
    val infoLensDiameterTitle: String,
    val infoLensDiameterDesc: String,

    // Info dialog - Effective diameter
    val infoEffectiveDiameterTitle: String,
    val infoEffectiveDiameterDesc: String,

    // Info dialog - Distance between lenses
    val infoDistanceBetweenLensesTitle: String,
    val infoDistanceBetweenLensesDesc: String,

    // Info dialog - Pupil distance
    val infoPupilDistanceTitle: String,
    val infoPupilDistanceDesc: String,

    // Diameter calculation
    val diameterCalculationResult: (result: String) -> String,

    // Result dialog
    val resultDialogTitle: String,
    val resultIndexOfRefraction: (index: String) -> String,
    val resultSpherePower: (sphere: String) -> String,
    val resultCylinderPower: (cylinder: String) -> String,
    val resultAxis: (axis: String) -> String,
    val resultOnAxisThickness: (axis: String, thickness: String) -> String,
    val resultCenterThickness: (thickness: String) -> String,
    val resultMinEdgeThickness: (thickness: String) -> String,
    val resultMaxEdgeThickness: (thickness: String) -> String,
    val resultBaseCurve: (curve: String) -> String,
    val resultDiameter: (diameter: String) -> String,
    val resultAddToList: String,
    val resultRemoveFromList: String,
    val resultShare: String,

    // Share
    val shareSubject: String,
    val shareWith: String,
    val shareTextOnlySphere: (
        index: String,
        sphere: String,
        centerThickness: String,
        edgeThickness: String,
        baseCurve: String,
        diameter: String
    ) -> String,
    val shareTextFull: (
        index: String,
        sphere: String,
        cylinder: String,
        axis: String,
        axisValue: String,
        thicknessOnAxis: String,
        centerThickness: String,
        edgeThickness: String,
        maxThickness: String,
        baseCurve: String,
        diameter: String
    ) -> String,

    // Settings
    val settingsTitle: String,

    // Settings - Language
    val settingsLanguageTitle: String,
    val settingsLanguageEnglish: String,
    val settingsLanguagePortuguese: String,
    val settingsLanguageUkrainian: String,
    val settingsLanguageHindi: String,

    // Settings - Theme
    val settingsThemeTitle: String,
    val settingsThemeAuto: String,
    val settingsThemeLight: String,
    val settingsThemeDark: String,

    // Compare list
    val compareListTitle: String,
    val compareListIndexColumn: String,
    val compareListSphereColumn: String,
    val compareListCylinderColumn: String,
    val compareListAxisColumn: String,
    val compareListThicknessAxisColumn: String,
    val compareListThicknessCenterColumn: String,
    val compareListThicknessMinColumn: String,
    val compareListThicknessMaxColumn: String,
    val compareListBaseCurveColumn: String,
    val compareListDiameterColumn: String,
    val compareListEmptyDescription: String,
    val compareListClearButton: String,

    // Info dialog - Compare
    val infoCompareTitle: String,
    val infoCompareDesc: String,

    // Info dialog - Transpose
    val infoTransposeTitle: String,
    val infoTransposeDesc: String,

    // Content descriptions for accessibility
    val contentDescriptionClearField: String,
    val contentDescriptionInfo: String,
    val contentDescriptionCompare: String,
    val contentDescriptionTranspose: String,
    val contentDescriptionSettings: String,
    val contentDescriptionClose: String,
    val contentDescriptionBack: String,
)
