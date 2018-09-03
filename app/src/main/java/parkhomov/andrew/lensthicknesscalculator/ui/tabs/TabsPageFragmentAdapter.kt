package parkhomov.andrew.lensthicknesscalculator.ui.tabs


import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment


class TabsPageFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabs: SparseArray<BaseFragment>  = SparseArray(2)

    init {
        tabs.put(0, Thickness.instance)
        tabs.put(1, Diameter.instance)
    }

    override fun getItem(position: Int): Fragment? = tabs.get(position)

    override fun getCount(): Int = tabs.size()

    override fun getItemPosition(someObject: Any): Int = PagerAdapter.POSITION_NONE

}
