package parkhomov.andrew.lensthicknesscalculator.base

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.v4.app.Fragment
import timber.log.Timber


abstract class BaseFragment : Fragment() {

    var headers: List<String>? = null
        get() = baseActivity?.headers
    var description: List<String>? = null
        get() = baseActivity?.description
    var images: List<Int>? = null
        get() = baseActivity?.images
    var baseActivity: BaseActivity? = null
        private set


    @TargetApi(23)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onAttachToContext(context)
    }

    @Suppress("DEPRECATION", "OverridingDeprecatedMember")
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            onAttachToContext(activity)
    }

    private fun onAttachToContext(context: Context) {
        if (context is BaseActivity) {
            try {
                this.baseActivity = context
            } catch (e: NullPointerException) {
                Timber.i(e.toString())
            }
        }
    }

    protected fun hideKeyboard() {
        baseActivity?.hideKeyboard()
    }
}
