package parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.result

import parkhomov.andrew.lensthicknesscalculator.base.BasePresenter
import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData
import javax.inject.Inject

class ResultPresenter
@Inject
constructor(

) : BasePresenter<ResultI.View>(), ResultI.Presenter {

    override fun setResult(data: CalculatedData) {
        val refractionIndex = data.refractionIndex
        val spherePower = data.spherePower
        val cylinderPower = data.cylinderPower
        val axis = data.axis
        val thicknessOnAxis = data.thicknessOnAxis
        val thicknessCenter = data.thicknessCenter
        val thicknessEdge = data.thicknessEdge
        val thicknessMax = data.thicknessMax
        val realBaseCurve = data.realBaseCurve
        val diameter = data.diameter

        if (cylinderPower != null) {
            mvpView?.setCylinderPower(cylinderPower)
            mvpView?.setAxis(axis)
            mvpView?.setThicknessOnAxis(axis, thicknessOnAxis)
            mvpView?.setMaxThickness(thicknessMax)
        } else {
            mvpView?.showCylinderViews(false)
        }
        mvpView?.setRefractionIndex(refractionIndex)
        mvpView?.setSpherePower(spherePower)
        mvpView?.setCenterThickness(thicknessCenter)
        mvpView?.setEdgeThickness(thicknessEdge)
        mvpView?.setBaseCurve(realBaseCurve)
        mvpView?.setDiameter(diameter)
    }
}

