package parkhomov.andrew.ltc.strings

import cafe.adriel.lyricist.LyricistStrings

@LyricistStrings(languageTag = Locales.PT)
internal val PtStrings = Strings(
    // App name
    appName = "LTC",
    appNameFull = "Espessura da Lente",
    buttonOk = "OK",

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
    infoIndexOfRefractionDesc = "Descreve como a luz se curva através do material da lente. Índice maior = lentes mais finas. Comum: 1.50 (CR-39), 1.59 (policarbonato), 1.67, 1.74.",

    // Info dialog - Sphere power
    infoSpherePowerTitle = "Grau Esférico",
    infoSpherePowerDesc = "Corrige miopia (−) ou hipermetropia (+). Medido em dioptrias (D).",

    // Info dialog - Cylinder power
    infoCylinderPowerTitle = "Grau Cilíndrico",
    infoCylinderPowerDesc = "Corrige astigmatismo adicionando poder em um meridiano específico. Pode ser positivo ou negativo.",

    // Info dialog - Axis
    infoAxisTitle = "Eixo",
    infoAxisDesc = "O ângulo (0–180°) para correção cilíndrica. Combinado com cilindro para corrigir astigmatismo.",

    // Info dialog - Real base curve
    infoBaseCurveTitle = "Curva Base Real",
    infoBaseCurveDesc = "Curvatura da superfície frontal da lente em dioptrias. Afeta espessura e estética. Faixa: 0–10.5 D.",

    // Info dialog - Center thickness
    infoCenterThicknessTitle = "Espessura central",
    infoCenterThicknessDesc = "Espessura no centro óptico (mm). Ponto mais fino para lentes negativas, mais grosso para positivas.",

    // Info dialog - Edge thickness
    infoEdgeThicknessTitle = "Espessura da borda",
    infoEdgeThicknessDesc = "Espessura na borda da lente (mm). Ponto mais grosso para lentes negativas. Mín ~2mm para sem aro.",

    // Info dialog - Lens diameter
    infoLensDiameterTitle = "Diâmetro da lente",
    infoLensDiameterDesc = "Diâmetro do bloco de lente não cortado (mm). Maior diâmetro = lente mais grossa.",

    // Info dialog - Effective diameter
    infoEffectiveDiameterTitle = "Diâmetro Efetivo",
    infoEffectiveDiameterDesc = "Diagonal mais longa da abertura da armação (ED). Usado para calcular tamanho mínimo do bloco.",

    // Info dialog - Distance between lenses
    infoDistanceBetweenLensesTitle = "Distância entre as lentes",
    infoDistanceBetweenLensesDesc = "Tamanho da ponte (DBL) — distância entre bordas nasais das aberturas da armação (mm).",

    // Info dialog - Pupil distance
    infoPupilDistanceTitle = "Distância Pupilar",
    infoPupilDistanceDesc = "Distância entre os centros das pupilas (mm). Essencial para centragem. Média adulta: 54–74mm.",

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
    infoCompareDesc = "Compare as características de diferentes lentes lado a lado. Adicione lentes à lista após o cálculo tocando no botão \"Adicionar à lista de comparação\" na tela de resultado.",

    // Info dialog - Transpose
    infoTransposeTitle = "Transposição",
    infoTransposeDesc = "Converte uma prescrição entre formas de cilindro positivo e negativo. A transposição altera o sinal do cilindro e ajusta a esfera e o eixo de acordo, mantendo a mesma correção óptica.",

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
