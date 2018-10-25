package parkhomov.andrew.lensthicknesscalculator.base

import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.glossary.GlossaryItem
import parkhomov.andrew.lensthicknesscalculator.utils.MyContextWrapper
import parkhomov.andrew.lensthicknesscalculator.utils.appLanguage
import parkhomov.andrew.lensthicknesscalculator.utils.prefs.PreferencesHelper
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*


abstract class BaseActivity : AppCompatActivity() {

    lateinit var language: String
    var glossaryItem: GlossaryItem? = null
    private val preferencesHelper: PreferencesHelper by inject()

    override fun attachBaseContext(context: Context) {
        language = preferencesHelper.getStringValue(appLanguage, "")
        if (language.isEmpty()) {
            language = Locale.getDefault().isO3Language.substring(0, 2)
            preferencesHelper.putStringValue(appLanguage, language)
        }
        super.attachBaseContext(CalligraphyContextWrapper.wrap(MyContextWrapper.wrap(context, language)))
    }

    fun showMessage(message: String?) {
        Toast.makeText(this, message
                ?: getString(R.string.error_unknown), Toast.LENGTH_SHORT).show()
    }

    fun showMessage(@StringRes resId: Int) {
        showMessage(getString(resId))
    }

    protected fun createListWithData() {
        val jsonId = when (language) {
            "uk" -> R.raw.glossary_ukr
            "ru" -> R.raw.glossary_rus
            else -> R.raw.glossary_eng
        }
        val text = resources.openRawResource(jsonId).bufferedReader().use { it.readText() }

        glossaryItem = Gson().fromJson(text, GlossaryItem::class.java)
        if (glossaryItem == null)
            throw RuntimeException("Some error occurred while parsing json file")


        for (item in glossaryItem!!.data) {
            val imageId = when (item.id) {
                0 -> R.drawable.index_of_refraction_img
                1 -> R.drawable.sphere_img
                2 -> R.drawable.cylinder_img
                3 -> R.drawable.axis_img
                4 -> R.drawable.front_curve_img
                5 -> R.drawable.thickness_gauge_img
                6 -> R.drawable.edge_thickness_img
                7 -> R.drawable.diam_img
                8 -> R.drawable.ed_img
                9 -> R.drawable.dbl_img
                10 -> R.drawable.pd_img
                11 -> R.drawable.transposition_img
                else -> throw RuntimeException("No resource provided for key " + item.id)
            }
            item.imageId = imageId
        }
    }
}