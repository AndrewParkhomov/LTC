package parkhomov.andrew.lensthicknesscalculator.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_about_dialog.*
import parkhomov.andrew.lensthicknesscalculator.BuildConfig
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseDialog


class AboutDialogActivity : BaseDialog() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_about_dialog, container, false)


        return view
    }

    override fun setUp(view: View) {
        textViewAboutDialog.text = String.format("%s\n\n%s\n\n%s\n\n%s %s %s",
                resources.getString(R.string.version) + BuildConfig.VERSION_NAME,
                resources.getString(R.string.author_name),
                resources.getString(R.string.author_email),
                resources.getString(R.string.copyright),
                resources.getString(R.string.year),
                resources.getString(R.string.programing_language))
    }

    companion object {
        val TAG: String = AboutDialogActivity::class.java.simpleName
        val instance = AboutDialogActivity()
    }
}
