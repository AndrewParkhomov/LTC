package parkhomov.andrew.ltc.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.HI)
internal val HiStrings = Strings(
    // App name
    appName = "LTC",
    appNameFull = "लेंस मोटाई कैलकुलेटर",
    buttonOk = "ठीक है",

    // Tab headers
    tabThickness = "मोटाई",
    tabDiameter = "व्यास",

    // Thickness tab
    thicknessProvideCenterHint = "कृपया केंद्र की मोटाई दें",
    thicknessCalculateButton = "गणना करें",
    thicknessAddCylinderForTransposition = "ट्रांसपोज़िशन के लिए सिलेंडर मान जोड़ें",

    // Validation
    validationInvalidNumber = "अमान्य संख्या",
    validationMinValue = { minValue -> "मान कम से कम $minValue होना चाहिए" },
    validationMaxValue = { maxValue -> "मान अधिकतम $maxValue होना चाहिए" },

    // Info dialog - Index of refraction
    infoIndexOfRefractionTitle = "अपवर्तनांक",
    infoIndexOfRefractionDesc = "प्रकाशिकी में, किसी सामग्री का अपवर्तनांक या अपवर्तन सूचकांक एक आयामहीन संख्या है जो वर्णन करती है कि उस माध्यम से प्रकाश कैसे फैलता है। इसे n = c / v के रूप में परिभाषित किया गया है, जहां c निर्वात में प्रकाश की गति है और v माध्यम में प्रकाश का चरण वेग है।\n\tउदाहरण के लिए, पानी का अपवर्तनांक 1.333 है, जिसका अर्थ है कि प्रकाश निर्वात में पानी की तुलना में 1.333 गुना तेजी से यात्रा करता है ©विकिपीडिया",

    // Info dialog - Sphere power
    infoSpherePowerTitle = "गोलाकार शक्ति",
    infoSpherePowerDesc = "गोलाकार सुधार सभी मेरिडियन में एकल अभिसारी या अपसारी अपवर्तक शक्ति के साथ आंख की अपवर्तक त्रुटि को ठीक करता है",

    // Info dialog - Cylinder power
    infoCylinderPowerTitle = "सिलेंडर शक्ति",
    infoCylinderPowerDesc = "बेलनाकार सुधार निर्धारित अक्ष द्वारा निर्दिष्ट मेरिडियन में बेलनाकार रूप से शक्ति जोड़कर या घटाकर आंख की दृष्टिवैषम्य अपवर्तक त्रुटि को ठीक करता है",

    // Info dialog - Axis
    infoAxisTitle = "अक्ष",
    infoAxisDesc = "अक्ष केवल तभी मौजूद होता है जब सिलेंडर के लिए कोई मान हो। यह दो प्रमुख मेरिडियन में से एक के कोण को डिग्री में इंगित करता है जिसमें निर्धारित बेलनाकार शक्ति है। किस प्रमुख मेरिडियन का संदर्भ दिया जाता है यह प्लस या माइनस नोटेशन में बेलनाकार सुधार द्वारा इंगित किया जाता है",

    // Info dialog - Real base curve
    infoBaseCurveTitle = "वास्तविक आधार वक्र",
    infoBaseCurveDesc = "आधार वक्र (आमतौर पर नेत्र लेंस की सामने की सतह की प्रोफ़ाइल से निर्धारित) को लेंस की पूरी सतह पर सर्वोत्तम ऑप्टिक और कॉस्मेटिक विशेषताओं के परिणामस्वरूप बदला जा सकता है। नेत्र चिकित्सक इन कारणों में से किसी के लिए भी सुधारात्मक लेंस निर्धारित करते समय एक विशेष आधार वक्र निर्दिष्ट करने का विकल्प चुन सकते हैं। गणितीय सूत्रों की एक भीड़ और पेशेवर नैदानिक अनुभव ने नेत्र चिकित्सकों और लेंस डिजाइनरों को मानक आधार वक्रों को निर्धारित करने की अनुमति दी है जो अधिकांश लोगों के लिए आदर्श हैं। परिणामस्वरूप, सामने की सतह की वक्र अधिक मानकीकृत है और जो विशेषताएं किसी व्यक्ति के अद्वितीय नुस्खे को उत्पन्न करती हैं, वे आमतौर पर लेंस की पिछली सतह की ज्यामिति से प्राप्त होती हैं",

    // Info dialog - Center thickness
    infoCenterThicknessTitle = "केंद्र की मोटाई",
    infoCenterThicknessDesc = "मिलीमीटर में लेंस के ऑप्टिकल केंद्र में मोटाई",

    // Info dialog - Edge thickness
    infoEdgeThicknessTitle = "किनारे की मोटाई",
    infoEdgeThicknessDesc = "मिलीमीटर में व्यक्त किनारे में लेंस की मोटाई",

    // Info dialog - Lens diameter
    infoLensDiameterTitle = "लेंस का व्यास",
    infoLensDiameterDesc = "मिलीमीटर में लेंस का व्यास",

    // Info dialog - Effective diameter
    infoEffectiveDiameterTitle = "प्रभावी व्यास",
    infoEffectiveDiameterDesc = "प्रभावी व्यास लेंस के पार सबसे लंबा विकर्ण है",

    // Info dialog - Distance between lenses
    infoDistanceBetweenLensesTitle = "लेंसों के बीच की दूरी",
    infoDistanceBetweenLensesDesc = "प्रत्येक लेंस के नाक के किनारों के बीच मिलीमीटर में सबसे छोटी दूरी। DBL को आमतौर पर ब्रिज आकार के रूप में भी जाना जाता है",

    // Info dialog - Pupil distance
    infoPupilDistanceTitle = "पुतली की दूरी",
    infoPupilDistanceDesc = "पुतली की दूरी पुतलियों के केंद्रों के बीच (मिलीमीटर में व्यक्त) की दूरी है",

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
    shareTextOnlySphere = { appName, fullName, index, sphere, centerThickness, edgeThickness, baseCurve, diameter ->
        "$appName के माध्यम से गणना की गई\n$fullName\nअपवर्तनांक = $index\nगोलाकार शक्ति = $sphere\nकेंद्र की मोटाई = $centerThickness मिमी\nकिनारे की मोटाई = $edgeThickness मिमी\nवास्तविक आधार वक्र = $baseCurve\nव्यास = $diameter मिमी"
    },
    shareTextFull = { appName, fullName, index, sphere, cylinder, axis, axisValue, thicknessOnAxis, centerThickness, edgeThickness, maxThickness, baseCurve, diameter ->
        "$appName के माध्यम से गणना की गई\n$fullName\nअपवर्तनांक = $index\nगोलाकार शक्ति = $sphere\nसिलेंडर शक्ति = $cylinder\nअक्ष = $axis°\nअक्ष $axisValue° पर मोटाई = $thicknessOnAxis मिमी\nकेंद्र की मोटाई = $centerThickness मिमी\nकिनारे की मोटाई = $edgeThickness मिमी\nअधिकतम मोटाई = $maxThickness मिमी\nवास्तविक आधार वक्र = $baseCurve\nव्यास = $diameter मिमी"
    },

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
    compareListThicknessAxisColumn = "मोटाई अक्ष",
    compareListThicknessCenterColumn = "मोटाई केंद्र",
    compareListThicknessMinColumn = "मोटाई न्यूनतम",
    compareListThicknessMaxColumn = "मोटाई अधिकतम",
    compareListBaseCurveColumn = "वक्र",
    compareListDiameterColumn = "व्यास",
    compareListEmptyDescription = "आप गणना के बाद तुलना सूची में लेंस जोड़ सकते हैं",
    compareListClearButton = "सूची साफ़ करें",

    // Content descriptions for accessibility
    contentDescriptionClearField = "इनपुट फ़ील्ड साफ़ करें",
    contentDescriptionInfo = "जानकारी दिखाएं",
    contentDescriptionCompare = "तुलना सूची खोलें",
    contentDescriptionTranspose = "लेंस मानों को ट्रांसपोज़ करें",
    contentDescriptionSettings = "सेटिंग्स खोलें",
    contentDescriptionClose = "डायलॉग बंद करें",
    contentDescriptionBack = "वापस जाएं",
)
