package parkhomov.andrew.lensthicknesscalculator.base

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.glossary.GlossaryItem
import parkhomov.andrew.lensthicknesscalculator.utils.MyContextWrapper
import parkhomov.andrew.lensthicknesscalculator.utils.localeIso2
import parkhomov.andrew.lensthicknesscalculator.utils.shaderPref
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


abstract class BaseActivity : AppCompatActivity() {

    lateinit var language: String

    override fun attachBaseContext(context: Context) {
        val sharedPreferences = context.getSharedPreferences(shaderPref, Context.MODE_PRIVATE)
        language = sharedPreferences?.getString(localeIso2, "en") ?: ""
        super.attachBaseContext(CalligraphyContextWrapper.wrap(MyContextWrapper.wrap(context, language)))
    }

    var glossaryItem: GlossaryItem? = null

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0)
            supportFragmentManager.popBackStack()
        else
            AlertDialog.Builder(this)
                    .setTitle(R.string.exit_question_title)
                    .setMessage(R.string.exit_question)
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes) { _, _ ->
                        android.os.Process.killProcess(android.os.Process.myPid())
                    }.create().show()
    }

    protected fun createListWithData() {
        println(language)
        val jsonId = when (language) {
            "en" -> R.raw.glossary_eng
            "uk" -> R.raw.glossary_ukr
            "ru" -> R.raw.glossary_rus
            else -> throw RuntimeException("No valid language provided")
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