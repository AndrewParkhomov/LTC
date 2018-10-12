package parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness

import parkhomov.andrew.lensthicknesscalculator.base.MvpPresenter
import parkhomov.andrew.lensthicknesscalculator.base.MvpView
import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData

interface ThicknessI {

    interface Presenter : MvpPresenter<View> {
        fun showResultDialog(
                refractionIndex: String,
                spherePower: String,
                thicknessCenter: String,
                thicknessEdge: String,
                realBaseCurve: String,
                diameter: String
        )

        fun showResultDialog(
                refractionIndex: String,
                spherePower: String,
                cylinderPower: String,
                axis: String,
                thicknessOnAxis: String,
                thicknessCenter: String,
                thicknessEdge: String,
                thicknessMax: String,
                realBaseCurve: String,
                diameter: String
        )

        fun onCalculateBtnClicked(
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

    interface View : MvpView<Presenter> {
        fun highlightSpherePower(isShowError: Boolean)
        fun highlightDiameter(isShowError: Boolean)
        fun highlightCenterThickness(isShowError: Boolean)
        fun setCurrentBaseCurve(curveValue: String)
        fun showResultDialog(calculatedData: CalculatedData)


    }

}





