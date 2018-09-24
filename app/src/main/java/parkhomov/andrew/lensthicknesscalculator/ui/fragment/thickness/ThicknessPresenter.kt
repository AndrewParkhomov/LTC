package parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness

import parkhomov.andrew.lensthicknesscalculator.base.BasePresenter
import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.result.Result
import parkhomov.andrew.lensthicknesscalculator.utils.interactor.Interactor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class ThicknessPresenter
@Inject
constructor(
        private val router: Router,
        private val interactor: Interactor
) : BasePresenter<ThicknessI.View>(), ThicknessI.Presenter {


    override fun showResultDialog(
            refractionIndex: String,
            spherePower: String,
            thicknessCenter: String,
            thicknessEdge: String,
            realBaseCurve: String,
            diameter: String
    ) {
        interactor.calculatedData = CalculatedData(
                refractionIndex = refractionIndex,
                spherePower = spherePower,
                cylinderPower = null,
                axis = null,
                thicknessOnAxis = null,
                thicknessCenter = thicknessCenter,
                thicknessEdge = thicknessEdge,
                thicknessMax = null,
                realBaseCurve = realBaseCurve,
                diameter = diameter
        )
        router.navigateTo(Result.TAG, interactor.calculatedData!!)
    }

    override fun showResultDialog(
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
    ) {
        interactor.calculatedData = CalculatedData(
                refractionIndex = refractionIndex,
                spherePower = spherePower,
                cylinderPower = cylinderPower,
                axis = axis,
                thicknessCenter = thicknessCenter,
                thicknessEdge = thicknessEdge,
                thicknessMax = thicknessMax,
                thicknessOnAxis = thicknessOnAxis,
                realBaseCurve = realBaseCurve,
                diameter = diameter
        )
        router.navigateTo(Result.TAG, interactor.calculatedData!!)
    }

    override fun clearResultData() {
        interactor.calculatedData = null
    }
}
