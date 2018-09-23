package parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness

import parkhomov.andrew.lensthicknesscalculator.base.BasePresenter
import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.utils.interactor.Interactor
import javax.inject.Inject

class ThicknessPresenter
@Inject
constructor(
        private val interactor: Interactor
) : BasePresenter<ThicknessI.View>(), ThicknessI.Presenter {


    override fun showResultDialog(thicknessCenter: String, thicknessEdge: String) {
        interactor.calculatedData = CalculatedData(
                thicknessCenter = thicknessCenter,
                thicknessEdge = thicknessEdge,
                thicknessMax = null,
                thicknessOnAxis = null,
                axis = null
        )
        mvpView?.showResultDialog(interactor.calculatedData!!)
    }

    override fun showResultDialog(
            thicknessCenter: String,
            thicknessEdge: String,
            thicknessMax: String,
            thicknessOnAxis: String,
            axis: String
    ) {
        interactor.calculatedData = CalculatedData(
                thicknessCenter = thicknessCenter,
                thicknessEdge = thicknessEdge,
                thicknessMax = thicknessMax,
                thicknessOnAxis = thicknessOnAxis,
                axis = axis
        )
        mvpView?.showResultDialog(interactor.calculatedData!!)
    }
}
