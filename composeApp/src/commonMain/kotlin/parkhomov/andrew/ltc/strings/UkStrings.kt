package parkhomov.andrew.ltc.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.UK)
internal val UkStrings = Strings(
    // App name
    appName = "LTC",
    appNameFull = "Товщина лінзи",
    buttonOk = "OK",
    keyboardDone = "Готово",

    // Tab headers
    tabThickness = "Товщина",
    tabDiameter = "Діаметр",

    // Thickness tab
    thicknessProvideCenterHint = "Введіть товщину по центру",
    thicknessCalculateButton = "Розрахувати",
    addCustomIndex = "+ Додати власний індекс",

    // Custom index dialog
    addCustomIndexTitle = "Додати власний індекс",
    customIndexNameLabel = "Назва індексу",
    customIndexValueLabel = "Значення індексу",
    buttonCancel = "Скасувати",
    buttonSave = "Зберегти",
    buttonDelete = "Видалити",
    deleteIndexTitle = "Видалити індекс",
    deleteIndexMessage = { indexName -> "Ви впевнені, що хочете видалити \"$indexName\"?" },

    // Validation
    validationInvalidNumber = "Невірне число",
    validationMinValue = { minValue -> "Значення має бути не менше $minValue" },
    validationMaxValue = { maxValue -> "Значення має бути не більше $maxValue" },

    // Info dialog - Index of refraction
    infoIndexOfRefractionTitle = "Індекс заломлення",
    infoIndexOfRefractionDesc = "Показник заломлення — це величина, яка характеризує заломлюючу силу прозорого середовища. Чим вищий показник заломлення, тим тоншою є лінза. Для окулярних лінз значення зазвичай від 1.5 до 1.9.",

    // Info dialog - Sphere power
    infoSpherePowerTitle = "Сфера",
    infoSpherePowerDesc = "Сфера — це оптична сила лінзи, вимірюється в діоптріях (D), застосовується для корекції порушень рефракції. Для короткозорості (міопії) використовують розсіювальні лінзи зі знаком «−». Для далекозорості (гіперметропії) використовують збираючі лінзи зі знаком «+». Значення можуть варіюватися від −28 до +28 D.",

    // Info dialog - Cylinder power
    infoCylinderPowerTitle = "Циліндр",
    infoCylinderPowerDesc = "Циліндр — оптична сила лінзи для корекції астигматизму, вимірюється в діоптріях (D). Астигматизм виникає, коли рогівка або кришталик мають несферичну форму. Циліндр може бути зі знаком «−» або «+». Значення можуть варіюватися від −6 до +6 D.",

    // Info dialog - Axis
    infoAxisTitle = "Вісь",
    infoAxisDesc = "Вказує, під яким кутом розташована вісь циліндра. Положення осі вимірюється в градусах від 1° до 180° (він же 0°).",

    // Info dialog - Real base curve
    infoBaseCurveTitle = "Реальна кривизна бази лінзи",
    infoBaseCurveDesc = "Передня поверхня лінзи вимірюється сферометром, отримане значення за формулою перераховується в кривизну, виражену в міліметрах або в діоптріях (у цьому додатку). Значення для лінз «−» починається від 0.001 (плоска поверхня), для лінз «+» може досягати 10.5.",

    // Info dialog - Center thickness
    infoCenterThicknessTitle = "Товщина по центру",
    infoCenterThicknessDesc = "Товщина лінзи в її оптичному центрі. Для лінз «+» найбільша товщина буде в оптичному центрі, а для лінз «−» навпаки: найменша товщина буде в оптичному центрі. Товщина вимірюється товщиноміром.",

    // Info dialog - Edge thickness
    infoEdgeThicknessTitle = "Товщина по краю",
    infoEdgeThicknessDesc = "Товщина лінзи по краю в міліметрах. Для оправ «на жилці» та «на гвинтах» (для лінз «+») товщина по краю буде в середньому на 1.5 мм більша, ніж в ободкових оправах. Для оправ «на гвинтах» краще використовувати полікарбонат або високоіндексні матеріали (1.61, 1.67, 1.74), оскільки вони міцніші. В астигматичних лінзах із циліндром «−» максимальна товщина буде під кутом 90°, а з циліндром «+» — під кутом 0° (180°). Якщо важлива мінімальна товщина — обирайте невелику ободкову оправу та високий індекс заломлення.",

    // Info dialog - Lens diameter
    infoLensDiameterTitle = "Діаметр лінзи",
    infoLensDiameterDesc = "Діаметр лінзи в міліметрах. Чим більший діаметр — тим більша товщина лінзи. Розрахувати необхідний діаметр можна у вкладці «Діаметр».",

    // Info dialog - Effective diameter
    infoEffectiveDiameterTitle = "Ефективний діаметр",
    infoEffectiveDiameterDesc = "Ефективний діаметр окуляра (Effective diameter) — це відстань в міліметрах між двома найвіддаленішими точками окуляра (найбільша діагональ). Здебільшого, для коригувальних оправ, це значення перевищує горизонтальний розмір (А) на 2–3 мм.",

    // Info dialog - Distance between lenses
    infoDistanceBetweenLensesTitle = "Відстань між лінзами",
    infoDistanceBetweenLensesDesc = "Відстань між лінзами (DBL) — це найкоротша відстань в міліметрах між внутрішніми краями лінз. Також відома як розмір містка (bridge). Типові значення: 14–24 мм.",

    // Info dialog - Pupil distance
    infoPupilDistanceTitle = "Відстань між зіницями",
    infoPupilDistanceDesc = "Відстань між зіницями, міжцентрова відстань (PD) — це відстань між центрами зіниць в міліметрах. Типові значення: для дорослих 50–75 мм, для дітей 43–58 мм.",

    // Diameter calculation
    diameterCalculationResult = { result -> "ED x 2 + DBL - PD = $result мм" },

    // Result dialog
    resultDialogTitle = "Результат",
    resultIndexOfRefraction = { index -> "Індекс заломлення: $index" },
    resultSpherePower = { sphere -> "Сфера: $sphere" },
    resultCylinderPower = { cylinder -> "Циліндр: $cylinder" },
    resultAxis = { axis -> "Кут: $axis°" },
    resultOnAxisThickness = { axis, thickness -> "Товщина під кутом $axis°: $thickness мм" },
    resultCenterThickness = { thickness -> "Товщина по центру: $thickness мм" },
    resultMinEdgeThickness = { thickness -> "Мінімальна товщина: $thickness мм" },
    resultMaxEdgeThickness = { thickness -> "Максимальна товщина: $thickness мм" },
    resultBaseCurve = { curve -> "Кривизна бази лінзи: $curve" },
    resultDiameter = { diameter -> "Діаметр: $diameter мм" },
    resultAddToList = "Додати в список порівняння",
    resultRemoveFromList = "Видалити із списку порівняння",
    resultShare = "Поділитись",

    // Share
    shareSubject = "Дані про лінзу",
    shareWith = "Поділитись з допомогою…",
    shareTextOnlySphere = { index, sphere, centerThickness, edgeThickness, baseCurve, diameter ->
        "Калькулятор товщини лінзи\n\nІндекс заломлення = $index\nСфера = $sphere\nТовщина по центру = $centerThickness мм\nТовщина по краю = $edgeThickness мм\nРеальна кривизна бази лінзи = $baseCurve\nДіаметр лінзи = $diameter мм"
    },
    shareTextFull = { index, sphere, cylinder, axis, axisValue, thicknessOnAxis, centerThickness, edgeThickness, maxThickness, baseCurve, diameter ->
        "Калькулятор товщини лінзи\n\nІндекс заломлення = $index\nСфера = $sphere\nЦиліндр = $cylinder\nКут = $axis°\nТовщина під кутом $axisValue° = $thicknessOnAxis мм\nТовщина по центру = $centerThickness мм\nТовщина по краю = $edgeThickness мм\nМаксимальна товщина = $maxThickness мм\nРеальна кривизна бази лінзи = $baseCurve\nДіаметр лінзи = $diameter мм"
    },

    // Settings
    settingsTitle = "Налаштування",

    // Settings - Language
    settingsLanguageTitle = "Мова",
    settingsLanguageEnglish = "English",
    settingsLanguagePortuguese = "Portuguese",
    settingsLanguageUkrainian = "Українська",
    settingsLanguageHindi = "हिन्दी",

    // Settings - Theme
    settingsThemeTitle = "Тема",
    settingsThemeAuto = "Авто",
    settingsThemeLight = "Світла",
    settingsThemeDark = "Темна",

    // Compare list
    compareListTitle = "Список порівняння",
    compareListMaterialColumn = "Матеріал",
    compareListIndexColumn = "Індекс",
    compareListSphereColumn = "Сфера",
    compareListCylinderColumn = "Циліндр",
    compareListAxisColumn = "Кут",
    compareListThicknessAxisColumn = "Товщина\nпід кутом",
    compareListThicknessCenterColumn = "Товщина\nцентр",
    compareListThicknessMinColumn = "Товщина\nмін",
    compareListThicknessMaxColumn = "Товщина\nмакс",
    compareListBaseCurveColumn = "Кривизна",
    compareListDiameterColumn = "Діаметр",
    compareListEmptyDescription = "Ви можете додати лінзи у список порівняння після розрахунку",
    compareListClearButton = "Очистити список",

    // Info dialog - Compare
    infoCompareTitle = "Порівняння лінз",
    infoCompareDesc = "Порівняйте характеристики різних лінз поруч. Додайте лінзи у список після розрахунку, натиснувши кнопку «Додати в список порівняння» на екрані результату.",

    // Info dialog - Transpose
    infoTransposeTitle = "Транспозиція",
    infoTransposeDesc = "Перераховує рецепт між формами запису «+» та «−» циліндра. Змінюються значення сфери, циліндра та осі, але оптична корекція залишається незмінною.",

    // Content descriptions for accessibility
    contentDescriptionClearField = "Очистити поле вводу",
    contentDescriptionInfo = "Показати інформацію",
    contentDescriptionCompare = "Відкрити список порівняння",
    contentDescriptionTranspose = "Транспонувати значення лінзи",
    contentDescriptionSettings = "Відкрити налаштування",
    contentDescriptionClose = "Закрити діалог",
    contentDescriptionBack = "Повернутися назад",

    // iOS promo dialog
    iosPromoTitle = "Доступно на iOS",
    iosPromoMessage = "Калькулятор товщини лінзи тепер доступний на iOS! Поділіться з друзями, які користуються iPhone.",
)
