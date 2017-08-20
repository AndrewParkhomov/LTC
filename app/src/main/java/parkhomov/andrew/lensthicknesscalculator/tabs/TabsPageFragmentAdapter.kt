package parkhomov.andrew.lensthicknesscalculator.tabs

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.SparseArray


class TabsPageFragmentAdapter(fm: FragmentManager, headers: MutableList<String>, description: MutableList<String>, images: MutableList<Int>) : FragmentPagerAdapter(fm) {

    private val tabs: SparseArray<AbstractTabFragment>?

    init {
        this.tabs = SparseArray()
        tabs.put(0, Thickness.getInstance(headers, description, images))
        tabs.put(1, Diameter.getInstance(headers, description, images))
    }

    override fun getItem(position: Int): Fragment? = tabs?.get(position)

    override fun getCount(): Int = tabs?.size() ?: 0

    override fun getPageTitle(position: Int): CharSequence? = tabs!!.get(position).title

    fun getFragment(key: Int): AbstractTabFragment = tabs!!.get(key)

}
