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
    thicknessAddCylinderForTransposition = "Adicionar valor do cilindro para transposição",

    // Validation
    validationInvalidNumber = "Número inválido",
    validationMinValue = { minValue -> "O valor deve ser pelo menos $minValue" },
    validationMaxValue = { maxValue -> "O valor deve ser no máximo $maxValue" },

    // Info dialog - Index of refraction
    infoIndexOfRefractionTitle = "Índice de refração",
    infoIndexOfRefractionDesc = "Na óptica, o índice de refração ou índice de refração de um material é um número sem dimensão que descreve como a luz se propaga através desse meio. É definido como n = c / v, onde c é a velocidade da luz no vácuo e v é a velocidade da fase da luz no meio. \n\tPor exemplo, o índice de refração da água é 1,333, o que significa que a luz viaja 1,333 vezes mais rápido no vácuo do que na água © Wikipedia",

    // Info dialog - Sphere power
    infoSpherePowerTitle = "Grau Esférico",
    infoSpherePowerDesc = "A correção esférica corrige o erro de refração do olho com um único poder de refração convergente ou divergente em todos os meridianos",

    // Info dialog - Cylinder power
    infoCylinderPowerTitle = "Grau Cilíndrico",
    infoCylinderPowerDesc = "A correção cilíndrica corrige um erro refrativo astigmático do olho, adicionando ou subtraindo poder cilíndrico em um meridiano especificado pelo eixo prescrito",

    // Info dialog - Axis
    infoAxisTitle = "Eixo",
    infoAxisDesc = "O eixo está presente apenas se houver um valor para o cilindro. Isso indica o ângulo em graus de um dos dois principais meridianos em que a potência cilíndrica prescrita está. Qual o principal meridiano referenciado é indicado pela correção cilíndrica em notação de mais ou de menos",

    // Info dialog - Real base curve
    infoBaseCurveTitle = "Real Curva Base",
    infoBaseCurveDesc = "A curva base (geralmente determinada a partir do perfil da superfície frontal de uma lente oftálmica) pode ser alterada para resultar nas melhores características ópticas e cosméticas em toda a superfície da lente. Os optometristas podem optar por especificar uma curva de base específica ao prescrever uma lente corretiva por um desses motivos. Uma infinidade de fórmulas matemáticas e experiência clínica profissional permitiu que optometristas e projetistas de lentes determinassem curvas de base padrão ideais para a maioria das pessoas. Como resultado, a curva da superfície frontal é mais padronizada e as características que geram a prescrição exclusiva de uma pessoa geralmente são derivadas da geometria da superfície traseira da lente",

    // Info dialog - Center thickness
    infoCenterThicknessTitle = "Espessura central",
    infoCenterThicknessDesc = "Espessura no centro óptico da lente em milímetros",

    // Info dialog - Edge thickness
    infoEdgeThicknessTitle = "Espessura da borda",
    infoEdgeThicknessDesc = "Espessura da lente na borda expressa em milímetros",

    // Info dialog - Lens diameter
    infoLensDiameterTitle = "Diâmetro da lente",
    infoLensDiameterDesc = "Diâmetro da lente em milímetros",

    // Info dialog - Effective diameter
    infoEffectiveDiameterTitle = "Diâmetro Efetivo",
    infoEffectiveDiameterDesc = "O diâmetro efetivo é a diagonal mais longa na lente , também conhecido por diagonal maior (DM)",

    // Info dialog - Distance between lenses
    infoDistanceBetweenLensesTitle = "Distância entre as lentes",
    infoDistanceBetweenLensesDesc = "A menor distância em milímetros entre as bordas nasais de cada lente. DBL também é conhecido como tamanho da ponte",

    // Info dialog - Pupil distance
    infoPupilDistanceTitle = "Distância Pupilar",
    infoPupilDistanceDesc = "Distância da pupila é a distância (expressa em milímetros) entre os centros das pupilas. Conhecido como DP",

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
    shareTextOnlySphere = { appName, fullName, index, sphere, centerThickness, edgeThickness, baseCurve, diameter ->
        "Calculado pela $appName\n$fullName\nÍndice de Refração = $index\nGrau Esférico = $sphere\nEspessura central = $centerThickness mm\nEspessura da borda = $edgeThickness mm\nCurva base Real = $baseCurve\nDiâmetro = $diameter mm"
    },
    shareTextFull = { appName, fullName, index, sphere, cylinder, axis, axisValue, thicknessOnAxis, centerThickness, edgeThickness, maxThickness, baseCurve, diameter ->
        "Calculado pela $appName\n$fullName\nÍndice de Refração = $index\nGrau Esférico = $sphere\nGrau Cilíndrico = $cylinder\nEixo = $axis°\nEspessura no eixo $axisValue° = $thicknessOnAxis mm\nEspessura central = $centerThickness mm\nEspessura da borda = $edgeThickness mm\nEspessura Max = $maxThickness mm\nCurva Base Real = $baseCurve\nDiâmetro = $diameter mm"
    },

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
    compareListThicknessAxisColumn = "Esp eixo",
    compareListThicknessCenterColumn = "Esp cntr",
    compareListThicknessMinColumn = "Esp min",
    compareListThicknessMaxColumn = "Esp max",
    compareListBaseCurveColumn = "Curva",
    compareListDiameterColumn = "Diâmetro",
    compareListEmptyDescription = "Você pode adicionar lentes na lista de comparação após o cálculo",
    compareListClearButton = "Limpe a lista",

    // Info dialog - Compare
    infoCompareTitle = "Comparar lentes",
    infoCompareDesc = "Compare as características de diferentes lentes lado a lado. Adicione lentes à lista após o cálculo tocando no botão \"Adicionar à lista de comparação\" na tela de resultado.",

    // Content descriptions for accessibility
    contentDescriptionClearField = "Limpar campo de entrada",
    contentDescriptionInfo = "Mostrar informações",
    contentDescriptionCompare = "Abrir lista de comparação",
    contentDescriptionTranspose = "Transpor valores da lente",
    contentDescriptionSettings = "Abrir configurações",
    contentDescriptionClose = "Fechar diálogo",
    contentDescriptionBack = "Voltar",
)
