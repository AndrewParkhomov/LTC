package parkhomov.andrew.lensthicknesscalculator.base

/**
 * View
 */
interface MvpView<out T : MvpPresenter<*>> {

    val presenter: T
}