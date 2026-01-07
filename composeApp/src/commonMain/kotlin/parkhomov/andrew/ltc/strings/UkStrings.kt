package parkhomov.andrew.ltc.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.UK)
internal val UkStrings = Strings(
    // App name
    appName = "LTC",
    appNameFull = "Калькулятор товщини лінзи",
    buttonOk = "OK",

    // Tab headers
    tabThickness = "Товщина",
    tabDiameter = "Діаметр",

    // Thickness tab
    thicknessProvideCenterHint = "Введіть товщину по центру",
    thicknessCalculateButton = "Розрахувати",
    thicknessAddCylinderForTransposition = "Додайте значення циліндру для траснпозиції",

    // Validation
    validationInvalidNumber = "Невірне число",
    validationMinValue = { minValue -> "Значення має бути не менше $minValue" },
    validationMaxValue = { maxValue -> "Значення має бути не більше $maxValue" },

    // Info dialog - Index of refraction
    infoIndexOfRefractionTitle = "Індекс заломлення",
    infoIndexOfRefractionDesc = "Показник заломлення — це величина, яка характеризує заломлюючу силу прозорого середовища. Чим вищий показник заломлення, тим тоншою є лінза",

    // Info dialog - Sphere power
    infoSpherePowerTitle = "Сфера",
    infoSpherePowerDesc = "Сфера - це оптична сила лінзи, вимірюється в діоптріях, позначається D чи Дптр., застосовується для корекції аномалії рефракції. Для далекозорості (міопії) використовують розсіювальні лінзи — перед числовим значенням стоїть знак «-». Для далекозорості (гіперметропії) використовують збираючі лінзи — перед числовим значенням стоїть знак «+»",

    // Info dialog - Cylinder power
    infoCylinderPowerTitle = "Циліндр",
    infoCylinderPowerDesc = "Циліндр - оптична сила лінзи, яка застосовується для корекції астигматизму. У здоровому вигляді рогівка і кришталик мають сферичну форму. Якщо ця форма змінюється, то при проходженні світлових променів крізь рогівку утворюється викривлене зображення предмета. Одні фрагменти зображення фокусуються на сітківці, інші — перед нею, або поза нею. У результаті цього людина бачить картинку, в якій присутні як чіткі, так і розмиті ділянки і лінії. Астигматизм може бути двох видів: — рогівковий— кришталиковийТакож астигматизм може бути як вродженим, так і набутим. При астигматизмі у рецепті обов\"язково вказується положення осі циліндра в градусах — від 1° до 180° (він же 0°). Це пов\"язано з відмінностями заломлення світла, яке проходить крізь циліндричну лінзу:— промені, які проходять перпендикулярно осі циліндра переломлюються— промені, які проходять паралельно осі, не змінюють напрямок рухуТакі властивості дозволяють «виправити» переломлення світла у потрібному меридіані. Значення циліндра буває мінусовим і плюсовим (див. Транспозиція)",

    // Info dialog - Axis
    infoAxisTitle = "Кут",
    infoAxisDesc = "Вказує, під яким кутом розташована вісь циліндра. Положення осі вимірюється у градусах від 1° до 180° (він же 0°)",

    // Info dialog - Real base curve
    infoBaseCurveTitle = "Реальна кривизна бази лінзи",
    infoBaseCurveDesc = "Передня поверхня лінзи вимірюється сферометром, отримане значення по формулі перераховується в кривизну, виражену в міліметрах або в діоптріях (в цьому додатку). Значення у межах від 0.001 (плоска передня поверхня) для виготовлення лінз з великим негативним значенням (\"мінусових\" лінз),  до 10.5 (приблизне значення), для виготовлення лінз з великим позитивним значенням (\"плюсових\" лінз)",

    // Info dialog - Center thickness
    infoCenterThicknessTitle = "Товщина по центру",
    infoCenterThicknessDesc = "Товщина лінзи в її оптичному центрі. Для \"плюсових\" лінз найбільша товщина буде в оптичному центрі, а для \"мінусових\" навпаки: найменша товщина буде у оптичному центрі лінзи. Товщина вимірюється товщинометром",

    // Info dialog - Edge thickness
    infoEdgeThicknessTitle = "Товщина по краю",
    infoEdgeThicknessDesc = "Для \"плюсових\" лінз мінімальна товщина буде по краю, а для \"мінусових\", відповідно, мінімальна товщина буде по центру, а максимальна – по краю. Слід зауважити, що для оправ \"на жилці\" та \"на гвинтах\" (для \"плюсових\" лінз), товщина по краю буде в середньому на 1.5 мм більша ніж в ободкових (рамочних) оправах. Це пов\"язано з тим, що для нарізання канавки для жилки необхідно близько 2 мм (для металевої жилки 2.5 – 3.0 мм), для оправ \"на гвинтах\" необхідні ті ж 2 мм \"тіла\" лінзи для того, щоб лінза не ламалася при найменших навантаженнях. Для оправ \"на гвинтах\" найкраще всього застосовувати полікарбонат, або ж високоіндексні матеріали (1.61, 1.67, 1.74), оскільки вони міцніші порівняно з низькоіндексними. Також необхідно враховувати, що у астигматичних лінз із знаком циліндра «-» максимальна товщина буде під кутом 90°, а для лінз із знаком циліндра «+» — під кутом 0°, він же 180° (див. Транспозиція). P.S. Якщо одним із головних критеріїв при підборі окулярів є товщина лінз, то слід вибирати невелику ободкову оправу та високий показник заломлення лінзи",

    // Info dialog - Lens diameter
    infoLensDiameterTitle = "Діаметр лінзи",
    infoLensDiameterDesc = "Діаметр лінзи в міліметрах. Чим більший діаметр — тим більша товщина лінзи. Розрахувати необхідний діаметр можна в меню «ДІАМЕТР»",

    // Info dialog - Effective diameter
    infoEffectiveDiameterTitle = "Ефективний діаметр",
    infoEffectiveDiameterDesc = "Ефективний діаметр окуляра (Effective diameter) — це відстань в міліметрах між двома найвіддаленішими точками окуляра (найбільша діагональ). Здебільшого, для коригуючих оправ, це значення перевищує горизонтальний розмір (А) на 2 – 3 мм",

    // Info dialog - Distance between lenses
    infoDistanceBetweenLensesTitle = "Відстань між лінзами",
    infoDistanceBetweenLensesDesc = "Відстань між лінзами (Distance between lenses) — це відстань в міліметрах між лінзами (перенісся)",

    // Info dialog - Pupil distance
    infoPupilDistanceTitle = "Відстань між зіницями",
    infoPupilDistanceDesc = "Відстань між зіницями, міжцентрова відстань (Pupil distance) — це відстань між зіницями в міліметрах",

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
    shareTextOnlySphere = { appName, fullName, index, sphere, centerThickness, edgeThickness, baseCurve, diameter ->
        "Пораховано за допомогою додатку $appName\n$fullName\nІндекс заломлення = $index\nСфера = $sphere\nТовщина по центру = $centerThickness мм\nТовщина по краю = $edgeThickness мм\nРеальна кривизна бази лінзи = $baseCurve\nДіаметр лінзи = $diameter мм"
    },
    shareTextFull = { appName, fullName, index, sphere, cylinder, axis, axisValue, thicknessOnAxis, centerThickness, edgeThickness, maxThickness, baseCurve, diameter ->
        "Пораховано за допомогою додатку $appName\n$fullName\nІндекс заломлення = $index\nСфера = $sphere\nЦиліндр = $cylinder\nКут = $axis°\nТовщина під кутом $axisValue° = $thicknessOnAxis мм\nТовщина по центру = $centerThickness мм\nТовщина по краю = $edgeThickness мм\nМаксимальна товщина = $maxThickness мм\nРеальна кривизна бази лінзи = $baseCurve\nДіаметр лінзи = $diameter мм"
    },

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
    compareListThicknessAxisColumn = "Тов. кут",
    compareListThicknessCenterColumn = "Тов. центр",
    compareListThicknessMinColumn = "Тов. мін",
    compareListThicknessMaxColumn = "Тов. макс",
    compareListBaseCurveColumn = "Кривизна",
    compareListDiameterColumn = "Діаметр",
    compareListEmptyDescription = "Ви можете додати лінзи у список порівняння після розрахунку",
    compareListClearButton = "Очистити список",

    // Content descriptions for accessibility
    contentDescriptionClearField = "Очистити поле вводу",
    contentDescriptionInfo = "Показати інформацію",
    contentDescriptionCompare = "Відкрити список порівняння",
    contentDescriptionTranspose = "Транспонувати значення лінзи",
    contentDescriptionSettings = "Відкрити налаштування",
    contentDescriptionClose = "Закрити діалог",
    contentDescriptionBack = "Повернутися назад",
)
