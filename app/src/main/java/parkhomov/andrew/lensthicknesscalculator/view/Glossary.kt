package parkhomov.andrew.lensthicknesscalculator.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.glossary.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.adapter.AdapterGlossary
import parkhomov.andrew.lensthicknesscalculator.data.GlossaryItem

/**
 * Class glossary list display lists with parameters titles, witch present in program.
 */

class Glossary : Fragment(R.layout.glossary) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view_expandable.layoutManager = LinearLayoutManager(requireContext())
        recycler_view_expandable.adapter = AdapterGlossary(createListWithData())
    }

    private fun createListWithData(): List<GlossaryItem> {
        val titles = resources.getStringArray(R.array.titles)
        val descriptions = resources.getStringArray(R.array.descriptions)
        return titles.zip(descriptions).mapIndexed { index, pair ->
            GlossaryItem(
                title = pair.first,
                description = pair.second,
                imageId = getImageId(index)
            )
        }
    }

    private fun getImageId(position: Int): Int {
        return when (position) {
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
            else -> R.drawable.index_of_refraction_img
        }
    }
}
