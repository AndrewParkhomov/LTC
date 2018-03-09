package parkhomov.andrew.lensthicknesscalculator.ui.settings

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import kotlinx.android.synthetic.main.activity_about_dialog.*
import parkhomov.andrew.lensthicknesscalculator.BuildConfig
import parkhomov.andrew.lensthicknesscalculator.R


class AboutDialogActivity : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_about_dialog, container, false)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window.setBackgroundDrawableResource(R.drawable.selector_background_rounded_corners_white)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textViewAboutDialog.text = String.format("%s\n\n%s\n\n%s\n\n%s %s %s",
                resources.getString(R.string.version) + BuildConfig.VERSION_NAME,
                resources.getString(R.string.author_name),
                resources.getString(R.string.author_email),
                resources.getString(R.string.copyright),
                resources.getString(R.string.year),
                resources.getString(R.string.programing_language))
    }

    companion object {
        val instance = AboutDialogActivity()
    }
}