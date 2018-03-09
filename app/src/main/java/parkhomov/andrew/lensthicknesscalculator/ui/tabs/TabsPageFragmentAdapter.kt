package parkhomov.andrew.lensthicknesscalculator.ui.tabs

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.SparseArray
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment


class TabsPageFragmentAdapter(
        fm: FragmentManager,
        headers: MutableList<String>,
        description: MutableList<String>,
        images: MutableList<Int>
) : FragmentPagerAdapter(fm) {

    private val tabs: SparseArray<BaseFragment> = SparseArray()

    init {
        tabs.put(0, Thickness.getInstance(headers, description, images))
        tabs.put(1, Diameter.getInstance(headers, description, images))
    }

    override fun getItem(position: Int): Fragment? = tabs.get(position)

    override fun getCount(): Int = tabs.size()


}