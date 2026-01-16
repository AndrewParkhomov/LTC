package parkhomov.andrew.ltc.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.EN, default = true)
internal val EnStrings = Strings(
    // App name
    appName = "LTC",
    appNameFull = "Lens Thickness",
    buttonOk = "OK",

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
    infoIndexOfRefractionDesc = "Describes how light bends through a lens material. Higher index = thinner lenses. Common: 1.50 (CR-39), 1.59 (polycarbonate), 1.67, 1.74.",

    // Info dialog - Sphere power
    infoSpherePowerTitle = "Sphere power",
    infoSpherePowerDesc = "Corrects nearsightedness (−) or farsightedness (+). Measured in diopters (D).",

    // Info dialog - Cylinder power
    infoCylinderPowerTitle = "Cylinder power",
    infoCylinderPowerDesc = "Corrects astigmatism by adding power in a specific meridian. Can be plus or minus form.",

    // Info dialog - Axis
    infoAxisTitle = "Axis",
    infoAxisDesc = "The angle (0–180°) for cylindrical correction. Paired with cylinder power to correct astigmatism.",

    // Info dialog - Real base curve
    infoBaseCurveTitle = "Base curve",
    infoBaseCurveDesc = "Front surface curvature of the lens in diopters. Affects thickness and aesthetics. Range: 0–10.5 D.",

    // Info dialog - Center thickness
    infoCenterThicknessTitle = "Center thickness",
    infoCenterThicknessDesc = "Thickness at the optical center (mm). Thinnest point for minus lenses, thickest for plus.",

    // Info dialog - Edge thickness
    infoEdgeThicknessTitle = "Edge thickness",
    infoEdgeThicknessDesc = "Thickness at lens edge (mm). Thickest point for minus lenses. Min ~2mm for rimless frames.",

    // Info dialog - Lens diameter
    infoLensDiameterTitle = "Lens diameter",
    infoLensDiameterDesc = "Diameter of uncut lens blank (mm). Larger diameter = thicker lens.",

    // Info dialog - Effective diameter
    infoEffectiveDiameterTitle = "Effective diameter",
    infoEffectiveDiameterDesc = "Longest diagonal across frame opening (ED). Used to calculate minimum blank size.",

    // Info dialog - Distance between lenses
    infoDistanceBetweenLensesTitle = "Distance between lenses",
    infoDistanceBetweenLensesDesc = "Bridge size (DBL) — distance between nasal edges of frame openings (mm).",

    // Info dialog - Pupil distance
    infoPupilDistanceTitle = "Pupil distance",
    infoPupilDistanceDesc = "Distance between pupil centers (mm). Essential for lens centration. Adult average: 54–74mm.",

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
    infoCompareDesc = "Compare characteristics of different lenses side by side. Add lenses to the list after calculation by tapping \"Add to compare list\" button on the result screen.",

    // Info dialog - Transpose
    infoTransposeTitle = "Transposition",
    infoTransposeDesc = "Converts a prescription between plus and minus cylinder forms. Transposition changes the sign of the cylinder and adjusts the sphere and axis accordingly while maintaining the same optical correction.",

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
