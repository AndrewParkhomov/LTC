package parkhomov.andrew.lensthicknesscalculator.tabs

import android.support.v4.app.Fragment


abstract class AbstractTabFragment : Fragment() {

    var title: String? = null
        protected set

    var headers: MutableList<String>? = null
        protected set

    var description: MutableList<String>? = null
        protected set

    var images: MutableList<Int>? = null
        protected set

}
