package parkhomov.andrew.lensthicknesscalculator.base

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.dto.GlossaryItem
import parkhomov.andrew.lensthicknesscalculator.utils.MyContextWrapper
import parkhomov.andrew.lensthicknesscalculator.utils.localeIso2
import parkhomov.andrew.lensthicknesscalculator.utils.shaderPref
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        val sharedPreferences = newBase.getSharedPreferences(shaderPref, Context.MODE_PRIVATE)
        val languageIso2 = sharedPreferences?.getString(localeIso2, "") ?: ""
        super.attachBaseContext(CalligraphyContextWrapper.wrap(MyContextWrapper.wrap(newBase, languageIso2)))
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
        val text = resources.openRawResource(R.raw.glossary)
                .bufferedReader().use { it.readText() }

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
                else -> R.drawable.transposition_img
            }
            item.imageId = imageId
        }
    }
}