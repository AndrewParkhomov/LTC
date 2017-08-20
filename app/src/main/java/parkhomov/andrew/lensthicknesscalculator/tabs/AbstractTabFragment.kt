package parkhomov.andrew.lensthicknesscalculator.tabs

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity


abstract class AbstractTabFragment : Fragment() {

    var title: String? = null
        protected set

    var activity: Activity? = null
        protected set

    var headers: MutableList<String>? = null
        protected set

    var description: MutableList<String>? = null
        protected set

    var images: MutableList<Int>? = null
        protected set

}
