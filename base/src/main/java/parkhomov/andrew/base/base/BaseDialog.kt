package parkhomov.andrew.base.base

import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import parkhomov.andrew.base.R

abstract class BaseDialog : DialogFragment() {

    var baseActivity: BaseActivity? = null
        private set

    private var isDialogCancelable = true


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
                e.printStackTrace()
            }
        }
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    protected abstract fun setUp(view: View)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the content
        val root = ConstraintLayout(requireContext())
        root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        // creating the fullscreen dialog
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)

        if (dialog.window != null) {
            dialog.window?.setBackgroundDrawableResource(R.drawable.background_rounded_corners_white)
            dialog.window?.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
        } else {
            dismiss()
        }

        dialog.setCancelable(isDialogCancelable)
        dialog.setCanceledOnTouchOutside(isDialogCancelable)

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(view)
    }

}