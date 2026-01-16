package parkhomov.andrew.ltc.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.HI)
internal val HiStrings = Strings(
    // App name
    appName = "LTC",
    appNameFull = "लेंस मोटाई",
    buttonOk = "ठीक है",

    // Tab headers
    tabThickness = "मोटाई",
    tabDiameter = "व्यास",

    // Thickness tab
    thicknessProvideCenterHint = "कृपया केंद्र की मोटाई दें",
    thicknessCalculateButton = "गणना करें",
    addCustomIndex = "+ कस्टम इंडेक्स जोड़ें",

    // Custom index dialog
    addCustomIndexTitle = "कस्टम इंडेक्स जोड़ें",
    customIndexNameLabel = "इंडेक्स का नाम",
    customIndexValueLabel = "इंडेक्स का मान",
    buttonCancel = "रद्द करें",
    buttonSave = "सहेजें",
    buttonDelete = "हटाएं",
    deleteIndexTitle = "इंडेक्स हटाएं",
    deleteIndexMessage = { indexName -> "क्या आप वाकई \"$indexName\" को हटाना चाहते हैं?" },

    // Validation
    validationInvalidNumber = "अमान्य संख्या",
    validationMinValue = { minValue -> "मान कम से कम $minValue होना चाहिए" },
    validationMaxValue = { maxValue -> "मान अधिकतम $maxValue होना चाहिए" },

    // Info dialog - Index of refraction
    infoIndexOfRefractionTitle = "अपवर्तनांक",
    infoIndexOfRefractionDesc = "वर्णन करता है कि प्रकाश लेंस सामग्री से कैसे गुजरता है। उच्च सूचकांक = पतले लेंस। सामान्य: 1.50 (CR-39), 1.59 (पॉलीकार्बोनेट), 1.67, 1.74।",

    // Info dialog - Sphere power
    infoSpherePowerTitle = "गोलाकार शक्ति",
    infoSpherePowerDesc = "निकट दृष्टि (−) या दूर दृष्टि (+) को ठीक करता है। डायोप्टर (D) में मापा जाता है।",

    // Info dialog - Cylinder power
    infoCylinderPowerTitle = "सिलेंडर शक्ति",
    infoCylinderPowerDesc = "एक विशिष्ट मेरिडियन में शक्ति जोड़कर दृष्टिवैषम्य को ठीक करता है। प्लस या माइनस रूप हो सकता है।",

    // Info dialog - Axis
    infoAxisTitle = "अक्ष",
    infoAxisDesc = "बेलनाकार सुधार के लिए कोण (0–180°)। दृष्टिवैषम्य को ठीक करने के लिए सिलेंडर के साथ जोड़ा जाता है।",

    // Info dialog - Real base curve
    infoBaseCurveTitle = "वास्तविक आधार वक्र",
    infoBaseCurveDesc = "डायोप्टर में लेंस की सामने की सतह की वक्रता। मोटाई और सौंदर्य को प्रभावित करती है। रेंज: 0–10.5 D।",

    // Info dialog - Center thickness
    infoCenterThicknessTitle = "केंद्र की मोटाई",
    infoCenterThicknessDesc = "ऑप्टिकल केंद्र पर मोटाई (मिमी)। माइनस लेंस के लिए सबसे पतला बिंदु, प्लस के लिए सबसे मोटा।",

    // Info dialog - Edge thickness
    infoEdgeThicknessTitle = "किनारे की मोटाई",
    infoEdgeThicknessDesc = "लेंस के किनारे पर मोटाई (मिमी)। माइनस लेंस के लिए सबसे मोटा बिंदु। रिमलेस के लिए न्यूनतम ~2मिमी।",

    // Info dialog - Lens diameter
    infoLensDiameterTitle = "लेंस का व्यास",
    infoLensDiameterDesc = "अनकट लेंस ब्लैंक का व्यास (मिमी)। बड़ा व्यास = मोटा लेंस।",

    // Info dialog - Effective diameter
    infoEffectiveDiameterTitle = "प्रभावी व्यास",
    infoEffectiveDiameterDesc = "फ्रेम ओपनिंग का सबसे लंबा विकर्ण (ED)। न्यूनतम ब्लैंक आकार की गणना के लिए उपयोग किया जाता है।",

    // Info dialog - Distance between lenses
    infoDistanceBetweenLensesTitle = "लेंसों के बीच की दूरी",
    infoDistanceBetweenLensesDesc = "ब्रिज का आकार (DBL) — फ्रेम ओपनिंग के नाक के किनारों के बीच की दूरी (मिमी)।",

    // Info dialog - Pupil distance
    infoPupilDistanceTitle = "पुतली की दूरी",
    infoPupilDistanceDesc = "पुतलियों के केंद्रों के बीच की दूरी (मिमी)। केंद्रीकरण के लिए आवश्यक। वयस्क औसत: 54–74मिमी।",

    // Diameter calculation
    diameterCalculationResult = { result -> "ED x 2 + DBL - PD = $result मिमी" },

    // Result dialog
    resultDialogTitle = "परिणाम",
    resultIndexOfRefraction = { index -> "अपवर्तनांक: $index" },
    resultSpherePower = { sphere -> "गोलाकार शक्ति: $sphere" },
    resultCylinderPower = { cylinder -> "सिलेंडर शक्ति: $cylinder" },
    resultAxis = { axis -> "अक्ष: $axis°" },
    resultOnAxisThickness = { axis, thickness -> "अक्ष $axis° पर मोटाई: $thickness मिमी" },
    resultCenterThickness = { thickness -> "केंद्र की मोटाई: $thickness मिमी" },
    resultMinEdgeThickness = { thickness -> "न्यूनतम मोटाई: $thickness मिमी" },
    resultMaxEdgeThickness = { thickness -> "अधिकतम मोटाई: $thickness मिमी" },
    resultBaseCurve = { curve -> "आधार वक्र: $curve" },
    resultDiameter = { diameter -> "व्यास: $diameter मिमी" },
    resultAddToList = "तुलना सूची में जोड़ें",
    resultRemoveFromList = "तुलना सूची से हटाएं",
    resultShare = "साझा करें",

    // Share
    shareSubject = "लेंस डेटा",
    shareWith = "इसके साथ साझा करें…",
    shareTextOnlySphere = { index, sphere, centerThickness, edgeThickness, baseCurve, diameter ->
        "लेंस मोटाई कैलकुलेटर\n\nअपवर्तनांक = $index\nगोलाकार शक्ति = $sphere\nकेंद्र की मोटाई = $centerThickness मिमी\nकिनारे की मोटाई = $edgeThickness मिमी\nवास्तविक आधार वक्र = $baseCurve\nव्यास = $diameter मिमी"
    },
    shareTextFull = { index, sphere, cylinder, axis, axisValue, thicknessOnAxis, centerThickness, edgeThickness, maxThickness, baseCurve, diameter ->
        "लेंस मोटाई कैलकुलेटर\n\nअपवर्तनांक = $index\nगोलाकार शक्ति = $sphere\nसिलेंडर शक्ति = $cylinder\nअक्ष = $axis°\nअक्ष $axisValue° पर मोटाई = $thicknessOnAxis मिमी\nकेंद्र की मोटाई = $centerThickness मिमी\nकिनारे की मोटाई = $edgeThickness मिमी\nअधिकतम मोटाई = $maxThickness मिमी\nवास्तविक आधार वक्र = $baseCurve\nव्यास = $diameter मिमी"
    },

    // Settings
    settingsTitle = "सेटिंग्स",

    // Settings - Language
    settingsLanguageTitle = "भाषा",
    settingsLanguageEnglish = "English",
    settingsLanguagePortuguese = "Portuguese",
    settingsLanguageUkrainian = "Українська",
    settingsLanguageHindi = "हिन्दी",

    // Settings - Theme
    settingsThemeTitle = "थीम",
    settingsThemeAuto = "ऑटो",
    settingsThemeLight = "हल्का",
    settingsThemeDark = "गहरा",

    // Compare list
    compareListTitle = "तुलना सूची",
    compareListIndexColumn = "सूचकांक",
    compareListSphereColumn = "गोला",
    compareListCylinderColumn = "सिलेंडर",
    compareListAxisColumn = "अक्ष",
    compareListThicknessAxisColumn = "मोटाई\nअक्ष",
    compareListThicknessCenterColumn = "मोटाई\nकेंद्र",
    compareListThicknessMinColumn = "मोटाई\nन्यूनतम",
    compareListThicknessMaxColumn = "मोटाई\nअधिकतम",
    compareListBaseCurveColumn = "वक्र",
    compareListDiameterColumn = "व्यास",
    compareListEmptyDescription = "आप गणना के बाद तुलना सूची में लेंस जोड़ सकते हैं",
    compareListClearButton = "सूची साफ़ करें",

    // Info dialog - Compare
    infoCompareTitle = "लेंस की तुलना करें",
    infoCompareDesc = "विभिन्न लेंसों की विशेषताओं की एक साथ तुलना करें। परिणाम स्क्रीन पर \"तुलना सूची में जोड़ें\" बटन पर टैप करके गणना के बाद सूची में लेंस जोड़ें।",

    // Info dialog - Transpose
    infoTransposeTitle = "ट्रांसपोज़िशन",
    infoTransposeDesc = "प्लस और माइनस सिलेंडर रूपों के बीच प्रिस्क्रिप्शन को परिवर्तित करता है। ट्रांसपोज़िशन सिलेंडर का चिह्न बदलता है और समान ऑप्टिकल सुधार बनाए रखते हुए स्फीयर और अक्ष को तदनुसार समायोजित करता है।",

    // Content descriptions for accessibility
    contentDescriptionClearField = "इनपुट फ़ील्ड साफ़ करें",
    contentDescriptionInfo = "जानकारी दिखाएं",
    contentDescriptionCompare = "तुलना सूची खोलें",
    contentDescriptionTranspose = "लेंस मानों को ट्रांसपोज़ करें",
    contentDescriptionSettings = "सेटिंग्स खोलें",
    contentDescriptionClose = "डायलॉग बंद करें",
    contentDescriptionBack = "वापस जाएं",
)
