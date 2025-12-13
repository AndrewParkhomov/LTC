package parkhomov.andrew.ltc.view

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.ltc.data.GlossaryItemOld

abstract class BaseFragment(@LayoutRes layoutId: Int): Fragment(layoutId) {

    private val glossary: List<GlossaryItemOld> by lazy(LazyThreadSafetyMode.NONE){
        val titles = resources.getStringArray(R.array.titles)
        val descriptions = resources.getStringArray(R.array.descriptions)
        titles.zip(descriptions).mapIndexed { index, pair ->
            GlossaryItemOld(
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

    protected fun showGlossaryModal(imageId: Int){
        val targetGlossaryItem = glossary.first { it.imageId == imageId }
        Glossary.getInstance(targetGlossaryItem).show(childFragmentManager)
    }

}