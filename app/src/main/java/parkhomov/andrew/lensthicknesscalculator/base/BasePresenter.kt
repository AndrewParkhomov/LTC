package parkhomov.andrew.lensthicknesscalculator.base

import javax.inject.Inject


open class BasePresenter<V>
@Inject
constructor(

) : MvpPresenter<V> {

    var mvpView: V? = null
        private set

    override fun onAttach(mvpView: V) {
        this.mvpView = mvpView
    }

    override fun onDetach() {
        mvpView = null
    }

}