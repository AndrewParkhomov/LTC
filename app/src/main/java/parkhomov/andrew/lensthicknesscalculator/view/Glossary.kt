package parkhomov.andrew.lensthicknesscalculator.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.glossary.*
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.adapter.AdapterGlossary
import parkhomov.andrew.lensthicknesscalculator.data.glossary.GlossaryList
import parkhomov.andrew.lensthicknesscalculator.helper.PreferencesHelper
import parkhomov.andrew.lensthicknesscalculator.utils.appLanguage

/**
 * Class glossary list display lists with parameters titles, witch present in program.
 */

class Glossary : Fragment(R.layout.glossary) {

    private val preferencesHelper: PreferencesHelper by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view_expandable.layoutManager = LinearLayoutManager(requireContext())
        recycler_view_expandable.adapter = AdapterGlossary(createListWithData().data)
    }

    private fun createListWithData(): GlossaryList {
        val jsonId = when (preferencesHelper.getStringValue(appLanguage, "en")) {
            "po" -> R.raw.glossary_pt
            "uk" -> R.raw.glossary_ukr
            "ru" -> R.raw.glossary_rus
            else -> R.raw.glossary_eng
        }
        val text = resources.openRawResource(jsonId).bufferedReader().use { it.readText() }

        val glossaryItem = Gson().fromJson(text, GlossaryList::class.java)
        for (item in glossaryItem.data) {
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
        return glossaryItem
    }
}
