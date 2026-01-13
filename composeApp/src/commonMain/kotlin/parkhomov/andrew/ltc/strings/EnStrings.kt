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

    // Validation
    validationInvalidNumber = "Invalid number",
    validationMinValue = { minValue -> "Value must be at least $minValue" },
    validationMaxValue = { maxValue -> "Value must be at most $maxValue" },

    // Info dialog - Index of refraction
    infoIndexOfRefractionTitle = "Index of refraction",
    infoIndexOfRefractionDesc = "In optics, the refractive index or index of refraction of a material is a dimensionless number that describes how light propagates through that medium. It is defined as n = c / v, where c is the speed of light in vacuum and v is the phase velocity of light in the medium.\n\tFor example, the refractive index of water is 1.333, meaning that light travels 1.333 times faster in a vacuum than it does in water ©Wikipedia",

    // Info dialog - Sphere power
    infoSpherePowerTitle = "Sphere power",
    infoSpherePowerDesc = "Spherical correction corrects refractive error of the eye with a single convergent or divergent refractive power in all meridians",

    // Info dialog - Cylinder power
    infoCylinderPowerTitle = "Cylinder power",
    infoCylinderPowerDesc = "Cylindrical correction corrects astigmatic refractive error of the eye by adding or subtracting power cylindrically in a meridian specified by the prescribed axis",

    // Info dialog - Axis
    infoAxisTitle = "Axis",
    infoAxisDesc = "Axis is present only if there is a value for cylinder. This indicates the angle in degrees of one of two major meridians the prescribed cylindrical power is in. Which major meridian is referenced is indicated by the cylindrical correction being in plus or minus notation",

    // Info dialog - Real base curve
    infoBaseCurveTitle = "Real base curve",
    infoBaseCurveDesc = "The base curve (usually determined from the profile of the front surface of an ophthalmic lens) can be changed to result in the best optic and cosmetic characteristics across the entire surface of the lens. Optometrists may choose to specify a particular base curve when prescribing a corrective lens for either of these reasons. A multitude of mathematical formulas and professional clinical experience has allowed optometrists and lens designers to determine standard base curves that are ideal for most people. As a result, the front surface curve is more standardized and the characteristics that generate a person\"s unique prescription are typically derived from the geometry of the back surface of the lens",

    // Info dialog - Center thickness
    infoCenterThicknessTitle = "Center thickness",
    infoCenterThicknessDesc = "Thickness in optical center of the lens in millimeters",

    // Info dialog - Edge thickness
    infoEdgeThicknessTitle = "Edge thickness",
    infoEdgeThicknessDesc = "Thickness of the lens in edge expressed in millimeters",

    // Info dialog - Lens diameter
    infoLensDiameterTitle = "Lens diameter",
    infoLensDiameterDesc = "Diameter of the lens in millimeters",

    // Info dialog - Effective diameter
    infoEffectiveDiameterTitle = "Effective diameter",
    infoEffectiveDiameterDesc = "Effective diameter is the longest diagonal across the lens",

    // Info dialog - Distance between lenses
    infoDistanceBetweenLensesTitle = "Distance between lenses",
    infoDistanceBetweenLensesDesc = "The shortest distance in millimeters between the nasal edges of each lens. DBL is also commonly referred to as bridge size",

    // Info dialog - Pupil distance
    infoPupilDistanceTitle = "Pupil distance",
    infoPupilDistanceDesc = "Pupil Distance is the distance (expressed in millimeters) between the centers of the pupils",

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
    shareTextOnlySphere = { appName, fullName, index, sphere, centerThickness, edgeThickness, baseCurve, diameter ->
        "Calculated via $appName\n$fullName\nIndex of refraction = $index\nSphere power = $sphere\nCenter thickness = $centerThickness mm\nEdge thickness = $edgeThickness mm\nReal base curve = $baseCurve\nDiameter = $diameter mm"
    },
    shareTextFull = { appName, fullName, index, sphere, cylinder, axis, axisValue, thicknessOnAxis, centerThickness, edgeThickness, maxThickness, baseCurve, diameter ->
        "Calculated via $appName\n$fullName\nIndex of refraction = $index\nSphere power = $sphere\nCylinder Power = $cylinder\nAxis = $axis°\nThickness on axis $axisValue° = $thicknessOnAxis mm\nCenter thickness = $centerThickness mm\nEdge thickness = $edgeThickness mm\nMax thickness = $maxThickness mm\nReal base curve = $baseCurve\nDiameter = $diameter mm"
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
)
