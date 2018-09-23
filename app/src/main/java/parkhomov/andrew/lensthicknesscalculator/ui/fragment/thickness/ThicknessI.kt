package parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness

import parkhomov.andrew.lensthicknesscalculator.base.MvpPresenter
import parkhomov.andrew.lensthicknesscalculator.base.MvpView
import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData

interface ThicknessI {

    interface Presenter : MvpPresenter<View> {
        fun showResultDialog(
                thicknessCenter: String,
                thicknessEdge: String
        )
        fun showResultDialog(
                thicknessCenter: String,
                thicknessEdge: String,
                thicknessMax: String,
                thicknessOnAxis: String,
                axis: String
        )

    }

    interface View : MvpView<Presenter> {
        fun showResultDialog(calculatedData: CalculatedData)


    }

}





