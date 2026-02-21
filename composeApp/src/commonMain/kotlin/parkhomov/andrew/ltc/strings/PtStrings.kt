package parkhomov.andrew.ltc.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.PT)
internal val PtStrings = Strings(
    // App name
    appName = "LTC",
    appNameFull = "Espessura da Lente",
    buttonOk = "OK",
    keyboardDone = "Concluído",

    // Tab headers
    tabThickness = "Espessura",
    tabDiameter = "Diâmetro",

    // Thickness tab
    thicknessProvideCenterHint = "Forneça a espessura do centro",
    thicknessCalculateButton = "Calcular",
    addCustomIndex = "+ Adicionar índice personalizado",

    // Custom index dialog
    addCustomIndexTitle = "Adicionar índice personalizado",
    customIndexNameLabel = "Nome do índice",
    customIndexValueLabel = "Valor do índice",
    buttonCancel = "Cancelar",
    buttonSave = "Salvar",
    buttonDelete = "Excluir",
    deleteIndexTitle = "Excluir índice",
    deleteIndexMessage = { indexName -> "Tem certeza de que deseja excluir \"$indexName\"?" },

    // Validation
    validationInvalidNumber = "Número inválido",
    validationMinValue = { minValue -> "O valor deve ser pelo menos $minValue" },
    validationMaxValue = { maxValue -> "O valor deve ser no máximo $maxValue" },

    // Info dialog - Index of refraction
    infoIndexOfRefractionTitle = "Índice de refração",
    infoIndexOfRefractionDesc = "O índice de refração é um valor que caracteriza o poder de refração de um meio transparente. Quanto maior o índice de refração, mais fina é a lente. Para lentes oftálmicas, os valores geralmente variam de 1.5 a 1.9.",

    // Info dialog - Sphere power
    infoSpherePowerTitle = "Grau Esférico",
    infoSpherePowerDesc = "Esfera é a potência óptica da lente, medida em dioptrias (D), usada para corrigir erros de refração. Para miopia, usam-se lentes divergentes com sinal «−». Para hipermetropia, usam-se lentes convergentes com sinal «+». Os valores podem variar de −28 a +28 D.",

    // Info dialog - Cylinder power
    infoCylinderPowerTitle = "Grau Cilíndrico",
    infoCylinderPowerDesc = "Cilindro é a potência óptica da lente para correção do astigmatismo, medida em dioptrias (D). O astigmatismo ocorre quando a córnea ou o cristalino têm forma não esférica. O cilindro pode ter sinal «−» ou «+». Os valores podem variar de −6 a +6 D.",

    // Info dialog - Axis
    infoAxisTitle = "Eixo",
    infoAxisDesc = "Indica o ângulo em que o eixo do cilindro está posicionado. A posição do eixo é medida em graus de 1° a 180° (igual a 0°).",

    // Info dialog - Real base curve
    infoBaseCurveTitle = "Curva Base Real",
    infoBaseCurveDesc = "A superfície frontal da lente é medida com um esferômetro, e o valor é convertido em curvatura expressa em milímetros ou dioptrias (neste aplicativo). Valores para lentes «−» começam em 0.001 (superfície plana), para lentes «+» podem chegar a 10.5.",

    // Info dialog - Center thickness
    infoCenterThicknessTitle = "Espessura central",
    infoCenterThicknessDesc = "Espessura da lente no seu centro óptico. Para lentes «+», a maior espessura está no centro óptico, enquanto para lentes «−» é o oposto: a menor espessura está no centro óptico. A espessura é medida com um medidor de espessura.",

    // Info dialog - Edge thickness
    infoEdgeThicknessTitle = "Espessura da borda",
    infoEdgeThicknessDesc = "Espessura da borda da lente em milímetros. Para armações de nylon e sem aro (para lentes «+»), a espessura da borda será em média 1.5 mm maior do que em armações de aro completo. Para armações sem aro, é melhor usar policarbonato ou materiais de alto índice (1.61, 1.67, 1.74), pois são mais resistentes. Em lentes astigmáticas com cilindro «−», a espessura máxima está em 90°, com cilindro «+» — em 0° (180°). Se a espessura mínima é importante — escolha uma armação de aro completo pequena e alto índice de refração.",

    // Info dialog - Lens diameter
    infoLensDiameterTitle = "Diâmetro da lente",
    infoLensDiameterDesc = "Diâmetro da lente em milímetros. Quanto maior o diâmetro — maior a espessura da lente. Você pode calcular o diâmetro necessário na aba «Diâmetro».",

    // Info dialog - Effective diameter
    infoEffectiveDiameterTitle = "Diâmetro Efetivo",
    infoEffectiveDiameterDesc = "Diâmetro efetivo é a distância em milímetros entre os dois pontos mais distantes da abertura da lente (a diagonal mais longa). Para a maioria das armações corretivas, este valor excede o tamanho horizontal (A) em 2–3 mm.",

    // Info dialog - Distance between lenses
    infoDistanceBetweenLensesTitle = "Distância entre as lentes",
    infoDistanceBetweenLensesDesc = "Distância entre lentes (DBL) é a menor distância em milímetros entre as bordas internas das lentes. Também conhecida como tamanho da ponte (bridge). Valores típicos: 14–24 mm.",

    // Info dialog - Pupil distance
    infoPupilDistanceTitle = "Distância Pupilar",
    infoPupilDistanceDesc = "Distância pupilar (PD) é a distância entre os centros das pupilas em milímetros. Valores típicos: para adultos 50–75 mm, para crianças 43–58 mm.",

    // Diameter calculation
    diameterCalculationResult = { result -> "ED x 2 + DBL - PD = $result mm" },

    // Result dialog
    resultDialogTitle = "Resultado",
    resultIndexOfRefraction = { index -> "Índice de refração: $index" },
    resultSpherePower = { sphere -> "Grau Esférico: $sphere" },
    resultCylinderPower = { cylinder -> "Grau Cilíndrico: $cylinder" },
    resultAxis = { axis -> "Eixo: $axis°" },
    resultOnAxisThickness = { axis, thickness -> "Espessura no eixo $axis°: $thickness mm" },
    resultCenterThickness = { thickness -> "Espessura central: $thickness mm" },
    resultMinEdgeThickness = { thickness -> "Espessura mínima: $thickness mm" },
    resultMaxEdgeThickness = { thickness -> "Espessura máxima: $thickness mm" },
    resultBaseCurve = { curve -> "Curva base: $curve" },
    resultDiameter = { diameter -> "Diâmetro: $diameter mm" },
    resultAddToList = "Adicionar à lista de comparação",
    resultRemoveFromList = "Remover da lista de comparação",
    resultShare = "Compartilhar",

    // Share
    shareSubject = "Dados da lente",
    shareWith = "Compartilhar com…",
    shareTextOnlySphere = { index, sphere, centerThickness, edgeThickness, baseCurve, diameter ->
        "Calculadora de Espessura de Lente\n\nÍndice de Refração = $index\nGrau Esférico = $sphere\nEspessura central = $centerThickness mm\nEspessura da borda = $edgeThickness mm\nCurva base Real = $baseCurve\nDiâmetro = $diameter mm"
    },
    shareTextFull = { index, sphere, cylinder, axis, axisValue, thicknessOnAxis, centerThickness, edgeThickness, maxThickness, baseCurve, diameter ->
        "Calculadora de Espessura de Lente\n\nÍndice de Refração = $index\nGrau Esférico = $sphere\nGrau Cilíndrico = $cylinder\nEixo = $axis°\nEspessura no eixo $axisValue° = $thicknessOnAxis mm\nEspessura central = $centerThickness mm\nEspessura da borda = $edgeThickness mm\nEspessura Max = $maxThickness mm\nCurva Base Real = $baseCurve\nDiâmetro = $diameter mm"
    },

    // Settings
    settingsTitle = "Configurações",

    // Settings - Language
    settingsLanguageTitle = "Língua",
    settingsLanguageEnglish = "English",
    settingsLanguagePortuguese = "Portuguese",
    settingsLanguageUkrainian = "Українська",
    settingsLanguageHindi = "हिन्दी",

    // Settings - Theme
    settingsThemeTitle = "Tema",
    settingsThemeAuto = "Auto",
    settingsThemeLight = "Dia",
    settingsThemeDark = "Noite",

    // Compare list
    compareListTitle = "Lista de comparação",
    compareListMaterialColumn = "Material",
    compareListIndexColumn = "Índice",
    compareListSphereColumn = "Esférico",
    compareListCylinderColumn = "Cilíndrico",
    compareListAxisColumn = "Eixo",
    compareListThicknessAxisColumn = "Espessura\neixo",
    compareListThicknessCenterColumn = "Espessura\ncentro",
    compareListThicknessMinColumn = "Espessura\nmín",
    compareListThicknessMaxColumn = "Espessura\nmáx",
    compareListBaseCurveColumn = "Curva",
    compareListDiameterColumn = "Diâmetro",
    compareListEmptyDescription = "Você pode adicionar lentes na lista de comparação após o cálculo",
    compareListClearButton = "Limpe a lista",

    // Info dialog - Compare
    infoCompareTitle = "Comparar lentes",
    infoCompareDesc = "Compare as características de diferentes lentes lado a lado. Adicione lentes à lista após o cálculo tocando no botão «Adicionar à lista de comparação» na tela de resultado.",

    // Info dialog - Transpose
    infoTransposeTitle = "Transposição",
    infoTransposeDesc = "Converte uma prescrição entre as formas de notação de cilindro «+» e «−». Os valores de esfera, cilindro e eixo mudam, mas a correção óptica permanece inalterada.",

    // Content descriptions for accessibility
    contentDescriptionClearField = "Limpar campo de entrada",
    contentDescriptionInfo = "Mostrar informações",
    contentDescriptionCompare = "Abrir lista de comparação",
    contentDescriptionTranspose = "Transpor valores da lente",
    contentDescriptionSettings = "Abrir configurações",
    contentDescriptionClose = "Fechar diálogo",
    contentDescriptionBack = "Voltar",

    // iOS promo dialog
    iosPromoTitle = "Disponível no iOS",
    iosPromoMessage = "A Calculadora de Espessura de Lente agora está disponível no iOS! Compartilhe com seus amigos que usam iPhone.",
)
