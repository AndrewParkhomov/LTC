package parkhomov.andrew.lensthicknesscalculator.activities.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import java.util.List;

import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.Utils;

public class TabsPageFragmentAdapter extends FragmentPagerAdapter {

    private SparseArray<AbstractTabFragment> tabs;

    public TabsPageFragmentAdapter(FragmentManager fm, List<String> headers, List<String> description, List<Integer> images) {
        super(fm);
        this.tabs = new SparseArray<>();
        tabs.put(0, Thickness.getInstance(headers, description, images));
        tabs.put(1, Diameter.getInstance(headers, description, images));
    }

    @Override
    public Fragment getItem(int position) {
        return tabs != null ? tabs.get(position) : null;
    }

    @Override
    public int getCount() {
        return tabs != null ? tabs.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = tabs.get(position).getTitle();
        return title != null ? title : null;
    }

}
