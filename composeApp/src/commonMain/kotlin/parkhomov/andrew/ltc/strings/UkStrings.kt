package parkhomov.andrew.ltc.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.UK)
internal val UkStrings = Strings(
    // App name
    appName = "LTC",
    appNameFull = "Товщина лінзи",
    buttonOk = "OK",

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
    infoIndexOfRefractionDesc = "Характеризує заломлюючу силу матеріалу лінзи. Вищий індекс = тонша лінза. Типові: 1.50 (CR-39), 1.59 (полікарбонат), 1.67, 1.74.",

    // Info dialog - Sphere power
    infoSpherePowerTitle = "Сфера",
    infoSpherePowerDesc = "Коригує короткозорість (−) або далекозорість (+). Вимірюється в діоптріях (D).",

    // Info dialog - Cylinder power
    infoCylinderPowerTitle = "Циліндр",
    infoCylinderPowerDesc = "Коригує астигматизм, додаючи силу в певному меридіані. Може бути плюсовим або мінусовим.",

    // Info dialog - Axis
    infoAxisTitle = "Кут",
    infoAxisDesc = "Кут (0–180°) для циліндричної корекції. Визначає орієнтацію корекції астигматизму.",

    // Info dialog - Real base curve
    infoBaseCurveTitle = "Кривизна бази лінзи",
    infoBaseCurveDesc = "Кривизна передньої поверхні лінзи в діоптріях. Впливає на товщину та естетику. Діапазон: 0–10.5 D.",

    // Info dialog - Center thickness
    infoCenterThicknessTitle = "Товщина по центру",
    infoCenterThicknessDesc = "Товщина в оптичному центрі (мм). Найтонша точка для мінусових лінз, найтовща — для плюсових.",

    // Info dialog - Edge thickness
    infoEdgeThicknessTitle = "Товщина по краю",
    infoEdgeThicknessDesc = "Товщина на краю лінзи (мм). Найтовша для мінусових лінз. Мін. ~2мм для безоправних.",

    // Info dialog - Lens diameter
    infoLensDiameterTitle = "Діаметр лінзи",
    infoLensDiameterDesc = "Діаметр необробленої заготовки (мм). Більший діаметр = товща лінза.",

    // Info dialog - Effective diameter
    infoEffectiveDiameterTitle = "Ефективний діаметр",
    infoEffectiveDiameterDesc = "Найбільша діагональ отвору оправи (ED). Визначає мінімальний розмір заготовки.",

    // Info dialog - Distance between lenses
    infoDistanceBetweenLensesTitle = "Відстань між лінзами",
    infoDistanceBetweenLensesDesc = "Розмір містка (DBL) — відстань між носовими краями отворів оправи (мм).",

    // Info dialog - Pupil distance
    infoPupilDistanceTitle = "Відстань між зіницями",
    infoPupilDistanceDesc = "Відстань між центрами зіниць (мм). Важливо для центрування. Середнє: 54–74мм.",

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
    infoTransposeDesc = "Перетворює рецепт між плюсовою та мінусовою формою циліндра. Транспозиція змінює знак циліндра та відповідно коригує сферу і вісь, зберігаючи ту саму оптичну корекцію.",

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
