package parkhomov.andrew.lensthicknesscalculator.activities.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.glossary.GlossaryDetails;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.glossary.GlossaryList;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.settings.Language;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.settings.Settings;
import parkhomov.andrew.lensthicknesscalculator.activities.tabs.TabsPageFragmentAdapter;

/**
 * Main activity class. Customize drawers, toolbar, fragment behaviour ect.
 */
public class MainActivity extends FragmentActivity {

    @BindView(R.id.MyvViewPager)
    ViewPager viewPager;
    @BindView(R.id.MyTabLayout)
    TabLayout tabLayout;

    private List<String> headers = new ArrayList<>(12);
    private List<String> description = new ArrayList<>(12);
    private List<Integer> images = new ArrayList<>(12);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        createListWithData();
        createTabs();
    }

    private void createTabs() {
        TabsPageFragmentAdapter tabsPageFragmentAdapter = new TabsPageFragmentAdapter(
                getSupportFragmentManager(),
                headers,
                description,
                images
        );
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(tabsPageFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        GlossaryList glossaryList = (GlossaryList) getSupportFragmentManager().findFragmentByTag(CONSTANT.GLOSSARY_LIST);
        GlossaryDetails glossaryDetails = (GlossaryDetails) getSupportFragmentManager().findFragmentByTag(CONSTANT.GLOSSARY_DETAILS);
        Language language = (Language) getSupportFragmentManager().findFragmentByTag(CONSTANT.LANGUAGE);
        Settings settings = (Settings) getSupportFragmentManager().findFragmentByTag(CONSTANT.SETTINGS);

        backPressedBehaviour(
                glossaryList,
                glossaryDetails,
                language,
                settings
        );

    }

    private void backPressedBehaviour(Fragment... fragment) {
        boolean isFragmentAdded = false;
        // check if one of one fragment is added
        for (Fragment oneFragment : fragment) {
            if (oneFragment != null && oneFragment.isAdded()) {
                isFragmentAdded = true;
                break;
            }
        }

        if (isFragmentAdded) {
            getSupportFragmentManager().popBackStack();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.exit_question_title)
                    .setMessage(R.string.exit_question)
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    }).create().show();
        }
    }

    @OnClick(R.id.openGlossary)
    public void onGlossaryClicked() {
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.mainContainerConstr, GlossaryList.getInstance(headers, description, images), CONSTANT.GLOSSARY_LIST)
                    .commit();
        } catch (IllegalStateException e) {
        }
    }

    @OnClick(R.id.openSettings)
    public void onStatisticClicked() {
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.mainContainerConstr, Settings.getInstance(), CONSTANT.SETTINGS)
                    .commit();
        } catch (IllegalStateException e) {
        }
    }

    private void createListWithData() {
        //add headers
        headers.add(getString(R.string.index_of_refraction));
        headers.add(getString(R.string.sphere_power));
        headers.add(getString(R.string.cylinder_power));
        headers.add(getString(R.string.axis));
        headers.add(getString(R.string.real_base_curve));
        headers.add(getString(R.string.center_thickness));
        headers.add(getString(R.string.edge_thickness));
        headers.add(getString(R.string.diameter));
        headers.add(getString(R.string.effective_diameter));
        headers.add(getString(R.string.distance_between_lenses));
        headers.add(getString(R.string.pupil_distance));
        headers.add(getString(R.string.transposition));
        // add description
        description.add(getString(R.string.description_index_of_refraction));
        description.add(getString(R.string.description_sphere_power));
        description.add(getString(R.string.description_cylinder_power));
        description.add(getString(R.string.description_axis));
        description.add(getString(R.string.description_real_base_curve));
        description.add(getString(R.string.description_center_thickness));
        description.add(getString(R.string.description_edge_thickness));
        description.add(getString(R.string.description_diameter));
        description.add(getString(R.string.description_effective_diameter));
        description.add(getString(R.string.description_distance_between_lenses));
        description.add(getString(R.string.description_pupil_distance));
        description.add(getString(R.string.description_transposition));
        // images for each item
        images.add(R.drawable.index_of_refraction_img);
        images.add(R.drawable.sphere_img);
        images.add(R.drawable.cylinder_img);
        images.add(R.drawable.axis_img);
        images.add(R.drawable.front_curve_img);
        images.add(R.drawable.thickness_gauge_img);
        images.add(R.drawable.edge_thickness_img);
        images.add(R.drawable.diam_img);
        images.add(R.drawable.edge_thickness_img);
        images.add(R.drawable.dbl_img);
        images.add(R.drawable.pd_img);
        images.add(R.drawable.transposition_img);
    }
}
