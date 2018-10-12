package parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.result

import parkhomov.andrew.lensthicknesscalculator.base.MvpView
import parkhomov.andrew.lensthicknesscalculator.base.MvpPresenter
import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData

interface ResultI {

    interface Presenter : MvpPresenter<View> {
        fun setResult(data: CalculatedData)


    }

    interface View : MvpView<Presenter> {
        fun showCylinderViews(isShow: Boolean)
        fun setRefractionIndex(refractionIndex: String)
        fun setSpherePower(spherePower: String)
        fun setCenterThickness(thicknessCenter: String)
        fun setEdgeThickness(thicknessEdge: String)
        fun setDiameter(diameter: String)
        fun setBaseCurve(realBaseCurve: String)
        fun setCylinderPower(cylinderPower: String)
        fun setAxis(axis: String?)
        fun setThicknessOnAxis(axis: String?, thicknessOnAxis: String?)
        fun setMaxThickness(thicknessMax: String?)


    }

}





