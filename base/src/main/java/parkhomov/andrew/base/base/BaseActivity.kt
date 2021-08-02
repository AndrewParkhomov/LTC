package parkhomov.andrew.base.base

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import parkhomov.andrew.base.R
import parkhomov.andrew.base.data.glossary.GlossaryList
import parkhomov.andrew.base.helper.PreferencesHelper
import parkhomov.andrew.base.utils.MyContextWrapper
import parkhomov.andrew.base.utils.appLanguage
import java.util.*


abstract class BaseActivity : AppCompatActivity() {

    lateinit var language: String
    var glossaryItem: GlossaryList? = null
    private val preferencesHelper: PreferencesHelper by inject()

    override fun attachBaseContext(context: Context) {
        language = preferencesHelper.getStringValue(appLanguage, "")
        if (language.isEmpty()) {
            language = Locale.getDefault().isO3Language.substring(0, 2)
            preferencesHelper.putStringValue(appLanguage, language)
        }
        super.attachBaseContext(MyContextWrapper.wrap(context, language))
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
            "po" -> R.raw.glossary_pt
            "uk" -> R.raw.glossary_ukr
            "ru" -> R.raw.glossary_rus
            else -> R.raw.glossary_eng
        }
        val text = resources.openRawResource(jsonId).bufferedReader().use { it.readText() }

        glossaryItem = Gson().fromJson(text, GlossaryList::class.java)
        if (glossaryItem == null) {
            throw RuntimeException("Some error occurred while parsing json file")
        }

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