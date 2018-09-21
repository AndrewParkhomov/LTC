package parkhomov.andrew.lensthicknesscalculator.base

/**
 * Presenter
 */
interface MvpPresenter<V> {

    fun onAttach(mvpView: V)

    fun onDetach()

}