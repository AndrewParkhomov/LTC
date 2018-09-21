package parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter

import parkhomov.andrew.lensthicknesscalculator.base.MvpView
import parkhomov.andrew.lensthicknesscalculator.base.MvpPresenter

interface DiameterI {

    interface Presenter : MvpPresenter<View> {

        fun onCalculateButtonClicked()

    }

    interface View : MvpView<Presenter> {

        fun onCalculateButtonClicked()

    }

}





