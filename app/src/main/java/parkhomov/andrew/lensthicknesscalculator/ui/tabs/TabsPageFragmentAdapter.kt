package parkhomov.andrew.lensthicknesscalculator.ui.tabs

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import parkhomov.andrew.lensthicknesscalculator.base.BaseFragment


class TabsPageFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val tabs: SparseArray<BaseFragment> = SparseArray()

   internal fun createTabs(){
       tabs.put(0, Thickness.instance)
       tabs.put(1, Diameter.instance)
   }

    override fun getItem(position: Int): Fragment? = tabs.get(position)

    override fun getCount(): Int = tabs.size()

    override fun getItemPosition(someObject: Any): Int = PagerAdapter.POSITION_NONE

}
