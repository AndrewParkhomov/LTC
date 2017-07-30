package parkhomov.andrew.lensthicknesscalculator.activities.tabs;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.List;


public abstract class AbstractTabFragment extends Fragment {

    protected String title;
    protected Activity activity;

    protected List<String> headers;
    protected List<String> description;
    protected List<Integer> images;

    public String getTitle() {
        return title;
    }
}
