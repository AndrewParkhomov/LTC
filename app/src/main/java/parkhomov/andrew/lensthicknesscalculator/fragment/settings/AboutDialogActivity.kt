package parkhomov.andrew.lensthicknesscalculator.fragment.settings

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView

import butterknife.BindView
import butterknife.ButterKnife
import parkhomov.andrew.lensthicknesscalculator.BuildConfig
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.R2


class AboutDialogActivity : DialogFragment() {

    @BindView(R2.id.textViewAboutDialog)
    lateinit var tv: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_about_dialog, container, false)
        ButterKnife.bind(this, view)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (dialog.window != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog.window!!.setBackgroundDrawableResource(R.drawable.selector_background_rounded_corners_white)
        }

        val text = String.format("%s\n\n%s\n\n%s\n\n%s %s",
                resources.getString(R.string.version) + BuildConfig.VERSION_NAME,
                resources.getString(R.string.author_name),
                resources.getString(R.string.author_email),
                resources.getString(R.string.copyright),
                resources.getString(R.string.year))
        tv!!.text = text
        return view
    }

    companion object {

        val instance: AboutDialogActivity
            get() {
                val bundle = Bundle()
                val aboutDialogActivity = AboutDialogActivity()
                aboutDialogActivity.arguments = bundle
                return aboutDialogActivity
            }
    }

}
