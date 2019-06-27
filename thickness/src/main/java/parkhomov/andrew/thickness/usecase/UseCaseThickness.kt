package parkhomov.andrew.thickness.usecase

import parkhomov.andrew.base.data.result.CalculatedData
import parkhomov.andrew.base.usecase.UseCase

interface UseCaseThickness : UseCase<UseCaseThickness.Result> {

    sealed class Result {
        data class HighlightSpherePower(val isShowError: Boolean) : Result()
        data class HighlightDiameter(val isShowError: Boolean) : Result()
        data class HighlightCenterThickness(val isShowError: Boolean) : Result()
        data class SetCurrentBaseCurve(val curveValue: String) : Result()
        class ShowResultDialog(val calculatedData: CalculatedData) : Result()
    }

    fun calculateThickness(
            lensIndex: Triple<Double, Double, String>,
            spherePowerString: String,
            cylinderPowerString: String,
            axisString: String,
            curveString: String,
            centerThicknessString: String,
            edgeThicknessString: String,
            diameterString: String
    )

}
