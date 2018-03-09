package parkhomov.andrew.lensthicknesscalculator.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.utils.MyContextWrapper
import parkhomov.andrew.lensthicknesscalculator.utils.const
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*

abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        val sharedPreferences = newBase.getSharedPreferences(const.SHARED_PREF, Context.MODE_PRIVATE)
        val languageIso2 = sharedPreferences?.getString(const.SAVE_LANGUAGE_ISO2, "") ?: ""
        super.attachBaseContext(CalligraphyContextWrapper.wrap(MyContextWrapper.wrap(newBase, languageIso2)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createListWithData()
    }

    val headers = ArrayList<String>(12)
    val description = ArrayList<String>(12)
    val images = ArrayList<Int>(12)

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    }

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

    private fun createListWithData() {
        //add headers
        headers.add(0, getString(R.string.index_of_refraction))
        headers.add(1, getString(R.string.sphere_power))
        headers.add(2, getString(R.string.cylinder_power))
        headers.add(3, getString(R.string.axis))
        headers.add(4, getString(R.string.real_base_curve))
        headers.add(5, getString(R.string.center_thickness))
        headers.add(6, getString(R.string.edge_thickness))
        headers.add(7, getString(R.string.diameter))
        headers.add(8, getString(R.string.effective_diameter))
        headers.add(9, getString(R.string.distance_between_lenses))
        headers.add(10, getString(R.string.pupil_distance))
        headers.add(11, getString(R.string.transposition))
        // add description
        description.add(0, getString(R.string.description_index_of_refraction))
        description.add(1, getString(R.string.description_sphere_power))
        description.add(2, getString(R.string.description_cylinder_power))
        description.add(3, getString(R.string.description_axis))
        description.add(4, getString(R.string.description_real_base_curve))
        description.add(5, getString(R.string.description_center_thickness))
        description.add(6, getString(R.string.description_edge_thickness))
        description.add(7, getString(R.string.description_diameter))
        description.add(8, getString(R.string.description_effective_diameter))
        description.add(9, getString(R.string.description_distance_between_lenses))
        description.add(10, getString(R.string.description_pupil_distance))
        description.add(11, getString(R.string.description_transposition))
        // images for each item
        images.add(0, R.drawable.index_of_refraction_img)
        images.add(1, R.drawable.sphere_img)
        images.add(2, R.drawable.cylinder_img)
        images.add(3, R.drawable.axis_img)
        images.add(4, R.drawable.front_curve_img)
        images.add(5, R.drawable.thickness_gauge_img)
        images.add(6, R.drawable.edge_thickness_img)
        images.add(7, R.drawable.diam_img)
        images.add(8, R.drawable.ed_img)
        images.add(9, R.drawable.dbl_img)
        images.add(10, R.drawable.pd_img)
        images.add(11, R.drawable.transposition_img)
    }

    open fun changeLanguage() {

    }

}