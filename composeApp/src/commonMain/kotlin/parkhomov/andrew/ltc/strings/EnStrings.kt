package parkhomov.andrew.ltc.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.EN, default = true)
internal val EnStrings = Strings(
    // App name
    appName = "LTC",
    appNameFull = "Lens Thickness",
    buttonOk = "OK",
    keyboardDone = "Done",

    // Tab headers
    tabThickness = "Thickness",
    tabDiameter = "Diameter",

    // Thickness tab
    thicknessProvideCenterHint = "Please, provide center thickness",
    thicknessCalculateButton = "Calculate",
    addCustomIndex = "+ Add custom index",

    // Custom index dialog
    addCustomIndexTitle = "Add custom index",
    customIndexNameLabel = "Index name",
    customIndexValueLabel = "Index value",
    buttonCancel = "Cancel",
    buttonSave = "Save",
    buttonDelete = "Delete",
    deleteIndexTitle = "Delete index",
    deleteIndexMessage = { indexName -> "Are you sure you want to delete \"$indexName\"?" },

    // Validation
    validationInvalidNumber = "Invalid number",
    validationMinValue = { minValue -> "Value must be at least $minValue" },
    validationMaxValue = { maxValue -> "Value must be at most $maxValue" },

    // Info dialog - Index of refraction
    infoIndexOfRefractionTitle = "Index of refraction",
    infoIndexOfRefractionDesc = "The refractive index is a value that characterizes the refracting power of a transparent medium. The higher the refractive index, the thinner the lens. For ophthalmic lenses, values typically range from 1.5 to 1.9.",

    // Info dialog - Sphere power
    infoSpherePowerTitle = "Sphere power",
    infoSpherePowerDesc = "Sphere is the optical power of the lens, measured in diopters (D), used to correct refractive errors. For nearsightedness (myopia), diverging lenses with a «−» sign are used. For farsightedness (hyperopia), converging lenses with a «+» sign are used. Values can range from −28 to +28 D.",

    // Info dialog - Cylinder power
    infoCylinderPowerTitle = "Cylinder power",
    infoCylinderPowerDesc = "Cylinder is the optical power of the lens for correcting astigmatism, measured in diopters (D). Astigmatism occurs when the cornea or lens has a non-spherical shape. Cylinder can be with a «−» or «+» sign. Values can range from −6 to +6 D.",

    // Info dialog - Axis
    infoAxisTitle = "Axis",
    infoAxisDesc = "Indicates the angle at which the cylinder axis is positioned. The axis position is measured in degrees from 1° to 180° (same as 0°).",

    // Info dialog - Real base curve
    infoBaseCurveTitle = "Real base curve",
    infoBaseCurveDesc = "The front surface of the lens is measured with a spherometer, and the value is converted into curvature expressed in millimeters or diopters (in this app). Values for «−» lenses start from 0.001 (flat surface), for «+» lenses can reach 10.5.",

    // Info dialog - Center thickness
    infoCenterThicknessTitle = "Center thickness",
    infoCenterThicknessDesc = "Thickness of the lens at its optical center. For «+» lenses, the greatest thickness is at the optical center, while for «−» lenses it's the opposite: the smallest thickness is at the optical center. Thickness is measured with a thickness gauge.",

    // Info dialog - Edge thickness
    infoEdgeThicknessTitle = "Edge thickness",
    infoEdgeThicknessDesc = "Edge thickness of the lens in millimeters. For semi-rimless and rimless frames (for «+» lenses), edge thickness will be on average 1.5 mm greater than in full-rim frames. For rimless frames, it's better to use polycarbonate or high-index materials (1.61, 1.67, 1.74) as they are stronger. In astigmatic lenses with «−» cylinder, maximum thickness is at 90°, with «+» cylinder — at 0° (180°). If minimal thickness is important — choose a small full-rim frame and high refractive index.",

    // Info dialog - Lens diameter
    infoLensDiameterTitle = "Lens diameter",
    infoLensDiameterDesc = "Lens diameter in millimeters. The larger the diameter — the greater the lens thickness. You can calculate the required diameter in the «Diameter» tab.",

    // Info dialog - Effective diameter
    infoEffectiveDiameterTitle = "Effective diameter",
    infoEffectiveDiameterDesc = "Effective diameter is the distance in millimeters between the two farthest points of the lens opening (the longest diagonal). For most corrective frames, this value exceeds the horizontal size (A) by 2–3 mm.",

    // Info dialog - Distance between lenses
    infoDistanceBetweenLensesTitle = "Distance between lenses",
    infoDistanceBetweenLensesDesc = "Distance between lenses (DBL) is the shortest distance in millimeters between the inner edges of the lenses. Also known as bridge size. Typical values: 14–24 mm.",

    // Info dialog - Pupil distance
    infoPupilDistanceTitle = "Pupil distance",
    infoPupilDistanceDesc = "Pupillary distance (PD) is the distance between the centers of the pupils in millimeters. Typical values: for adults 50–75 mm, for children 43–58 mm.",

    // Diameter calculation
    diameterCalculationResult = { result -> "ED x 2 + DBL - PD = $result mm" },

    // Result dialog
    resultDialogTitle = "Result",
    resultIndexOfRefraction = { index -> "Index of refraction: $index" },
    resultSpherePower = { sphere -> "Sphere power: $sphere" },
    resultCylinderPower = { cylinder -> "Cylinder power: $cylinder" },
    resultAxis = { axis -> "Axis: $axis°" },
    resultOnAxisThickness = { axis, thickness -> "Thickness on axis $axis°: $thickness mm" },
    resultCenterThickness = { thickness -> "Center thickness: $thickness mm" },
    resultMinEdgeThickness = { thickness -> "Minimum thickness: $thickness mm" },
    resultMaxEdgeThickness = { thickness -> "Maximum thickness: $thickness mm" },
    resultBaseCurve = { curve -> "Base curve: $curve" },
    resultDiameter = { diameter -> "Diameter: $diameter mm" },
    resultAddToList = "Add to compare list",
    resultRemoveFromList = "Remove from compare list",
    resultShare = "Share",

    // Share
    shareSubject = "Lens data",
    shareWith = "Share with…",
    shareTextOnlySphere = { index, sphere, centerThickness, edgeThickness, baseCurve, diameter ->
        "Lens Thickness Calculator\n\nIndex of refraction = $index\nSphere power = $sphere\nCenter thickness = $centerThickness mm\nEdge thickness = $edgeThickness mm\nReal base curve = $baseCurve\nDiameter = $diameter mm"
    },
    shareTextFull = { index, sphere, cylinder, axis, axisValue, thicknessOnAxis, centerThickness, edgeThickness, maxThickness, baseCurve, diameter ->
        "Lens Thickness Calculator\n\nIndex of refraction = $index\nSphere power = $sphere\nCylinder Power = $cylinder\nAxis = $axis°\nThickness on axis $axisValue° = $thicknessOnAxis mm\nCenter thickness = $centerThickness mm\nEdge thickness = $edgeThickness mm\nMax thickness = $maxThickness mm\nReal base curve = $baseCurve\nDiameter = $diameter mm"
    },

    // Settings
    settingsTitle = "Settings",

    // Settings - Language
    settingsLanguageTitle = "Language",
    settingsLanguageEnglish = "English",
    settingsLanguagePortuguese = "Portuguese",
    settingsLanguageUkrainian = "Українська",
    settingsLanguageHindi = "हिन्दी",

    // Settings - Theme
    settingsThemeTitle = "Theme",
    settingsThemeAuto = "Auto",
    settingsThemeLight = "Light",
    settingsThemeDark = "Dark",

    // Compare list
    compareListTitle = "Compare list",
    compareListMaterialColumn = "Material",
    compareListIndexColumn = "Index",
    compareListSphereColumn = "Sphere",
    compareListCylinderColumn = "Cylinder",
    compareListAxisColumn = "Axis",
    compareListThicknessAxisColumn = "Thickness\naxis",
    compareListThicknessCenterColumn = "Thickness\ncenter",
    compareListThicknessMinColumn = "Thickness\nmin",
    compareListThicknessMaxColumn = "Thickness\nmax",
    compareListBaseCurveColumn = "Curve",
    compareListDiameterColumn = "Diameter",
    compareListEmptyDescription = "You can add lens in compare list after calculation",
    compareListClearButton = "Clear list",

    // Info dialog - Compare
    infoCompareTitle = "Compare lenses",
    infoCompareDesc = "Compare characteristics of different lenses side by side. Add lenses to the list after calculation by tapping the «Add to compare list» button on the result screen.",

    // Info dialog - Transpose
    infoTransposeTitle = "Transposition",
    infoTransposeDesc = "Converts a prescription between «+» and «−» cylinder notation forms. Sphere, cylinder, and axis values change, but optical correction remains unchanged.",

    // Content descriptions for accessibility
    contentDescriptionClearField = "Clear input field",
    contentDescriptionInfo = "Show information",
    contentDescriptionCompare = "Open comparison list",
    contentDescriptionTranspose = "Transpose lens values",
    contentDescriptionSettings = "Open settings",
    contentDescriptionClose = "Close dialog",
    contentDescriptionBack = "Go back",

    // iOS promo dialog
    iosPromoTitle = "Available on iOS",
    iosPromoMessage = "Lens Thickness Calculator is now available on iOS! Share with your friends who use iPhone.",
)
