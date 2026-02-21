package parkhomov.andrew.ltc.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.HI)
internal val HiStrings = Strings(
    // App name
    appName = "LTC",
    appNameFull = "लेंस मोटाई",
    buttonOk = "ठीक है",
    keyboardDone = "हो गया",

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
    infoIndexOfRefractionDesc = "अपवर्तनांक एक मान है जो पारदर्शी माध्यम की अपवर्तक शक्ति को दर्शाता है। अपवर्तनांक जितना अधिक होगा, लेंस उतना पतला होगा। चश्मे की लेंस के लिए, मान आमतौर पर 1.5 से 1.9 तक होते हैं।",

    // Info dialog - Sphere power
    infoSpherePowerTitle = "गोलाकार शक्ति",
    infoSpherePowerDesc = "स्फीयर लेंस की ऑप्टिकल पावर है, जो डायोप्टर (D) में मापी जाती है, अपवर्तन दोषों को ठीक करने के लिए उपयोग की जाती है। निकट दृष्टि (मायोपिया) के लिए «−» चिह्न वाले अपसारी लेंस का उपयोग किया जाता है। दूर दृष्टि (हाइपरोपिया) के लिए «+» चिह्न वाले अभिसारी लेंस का उपयोग किया जाता है। मान −28 से +28 D तक हो सकते हैं।",

    // Info dialog - Cylinder power
    infoCylinderPowerTitle = "सिलेंडर शक्ति",
    infoCylinderPowerDesc = "सिलेंडर दृष्टिवैषम्य को ठीक करने के लिए लेंस की ऑप्टिकल पावर है, जो डायोप्टर (D) में मापी जाती है। दृष्टिवैषम्य तब होता है जब कॉर्निया या लेंस का आकार गैर-गोलाकार होता है। सिलेंडर «−» या «+» चिह्न के साथ हो सकता है। मान −6 से +6 D तक हो सकते हैं।",

    // Info dialog - Axis
    infoAxisTitle = "अक्ष",
    infoAxisDesc = "सिलेंडर अक्ष किस कोण पर स्थित है यह दर्शाता है। अक्ष की स्थिति 1° से 180° (0° के समान) डिग्री में मापी जाती है।",

    // Info dialog - Real base curve
    infoBaseCurveTitle = "वास्तविक आधार वक्र",
    infoBaseCurveDesc = "लेंस की सामने की सतह को स्फेरोमीटर से मापा जाता है, और मान को मिलीमीटर या डायोप्टर में व्यक्त वक्रता में परिवर्तित किया जाता है (इस ऐप में)। «−» लेंस के लिए मान 0.001 (सपाट सतह) से शुरू होते हैं, «+» लेंस के लिए 10.5 तक पहुँच सकते हैं।",

    // Info dialog - Center thickness
    infoCenterThicknessTitle = "केंद्र की मोटाई",
    infoCenterThicknessDesc = "लेंस की उसके ऑप्टिकल केंद्र पर मोटाई। «+» लेंस के लिए, सबसे अधिक मोटाई ऑप्टिकल केंद्र पर होती है, जबकि «−» लेंस के लिए इसके विपरीत: सबसे कम मोटाई ऑप्टिकल केंद्र पर होती है। मोटाई थिकनेस गेज से मापी जाती है।",

    // Info dialog - Edge thickness
    infoEdgeThicknessTitle = "किनारे की मोटाई",
    infoEdgeThicknessDesc = "लेंस की किनारे की मोटाई मिलीमीटर में। सेमी-रिमलेस और रिमलेस फ्रेम के लिए («+» लेंस), किनारे की मोटाई फुल-रिम फ्रेम की तुलना में औसतन 1.5 मिमी अधिक होगी। रिमलेस फ्रेम के लिए पॉलीकार्बोनेट या हाई-इंडेक्स सामग्री (1.61, 1.67, 1.74) का उपयोग करना बेहतर है क्योंकि वे मजबूत होते हैं। «−» सिलेंडर वाले दृष्टिवैषम्य लेंस में अधिकतम मोटाई 90° पर होती है, «+» सिलेंडर के साथ — 0° (180°) पर। यदि न्यूनतम मोटाई महत्वपूर्ण है — छोटा फुल-रिम फ्रेम और उच्च अपवर्तनांक चुनें।",

    // Info dialog - Lens diameter
    infoLensDiameterTitle = "लेंस का व्यास",
    infoLensDiameterDesc = "लेंस का व्यास मिलीमीटर में। व्यास जितना बड़ा — लेंस की मोटाई उतनी अधिक। आवश्यक व्यास की गणना «व्यास» टैब में की जा सकती है।",

    // Info dialog - Effective diameter
    infoEffectiveDiameterTitle = "प्रभावी व्यास",
    infoEffectiveDiameterDesc = "प्रभावी व्यास लेंस ओपनिंग के दो सबसे दूर बिंदुओं के बीच मिलीमीटर में दूरी है (सबसे लंबा विकर्ण)। अधिकांश करेक्टिव फ्रेम के लिए, यह मान क्षैतिज आकार (A) से 2-3 मिमी अधिक होता है।",

    // Info dialog - Distance between lenses
    infoDistanceBetweenLensesTitle = "लेंसों के बीच की दूरी",
    infoDistanceBetweenLensesDesc = "लेंस के बीच की दूरी (DBL) लेंस के आंतरिक किनारों के बीच मिलीमीटर में सबसे छोटी दूरी है। इसे ब्रिज साइज के रूप में भी जाना जाता है। सामान्य मान: 14-24 मिमी।",

    // Info dialog - Pupil distance
    infoPupilDistanceTitle = "पुतली की दूरी",
    infoPupilDistanceDesc = "पुतली की दूरी (PD) पुतलियों के केंद्रों के बीच मिलीमीटर में दूरी है। सामान्य मान: वयस्कों के लिए 50-75 मिमी, बच्चों के लिए 43-58 मिमी।",

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
    compareListMaterialColumn = "सामग्री",
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
    infoCompareDesc = "विभिन्न लेंसों की विशेषताओं की साथ-साथ तुलना करें। परिणाम स्क्रीन पर «तुलना सूची में जोड़ें» बटन पर टैप करके गणना के बाद सूची में लेंस जोड़ें।",

    // Info dialog - Transpose
    infoTransposeTitle = "ट्रांसपोज़िशन",
    infoTransposeDesc = "«+» और «−» सिलेंडर नोटेशन रूपों के बीच प्रिस्क्रिप्शन को परिवर्तित करता है। स्फीयर, सिलेंडर और अक्ष के मान बदलते हैं, लेकिन ऑप्टिकल करेक्शन अपरिवर्तित रहता है।",

    // Content descriptions for accessibility
    contentDescriptionClearField = "इनपुट फ़ील्ड साफ़ करें",
    contentDescriptionInfo = "जानकारी दिखाएं",
    contentDescriptionCompare = "तुलना सूची खोलें",
    contentDescriptionTranspose = "लेंस मानों को ट्रांसपोज़ करें",
    contentDescriptionSettings = "सेटिंग्स खोलें",
    contentDescriptionClose = "डायलॉग बंद करें",
    contentDescriptionBack = "वापस जाएं",

    // iOS promo dialog
    iosPromoTitle = "iOS पर उपलब्ध",
    iosPromoMessage = "लेंस मोटाई कैलकुलेटर अब iOS पर उपलब्ध है! अपने iPhone उपयोग करने वाले दोस्तों के साथ साझा करें।",
)
