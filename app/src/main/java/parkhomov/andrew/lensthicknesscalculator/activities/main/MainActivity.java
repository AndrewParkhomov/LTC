package parkhomov.andrew.lensthicknesscalculator.activities.main;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.glossary.GlossaryDetails;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.glossary.GlossaryList;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.settings.Language;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.settings.Settings;
import parkhomov.andrew.lensthicknesscalculator.activities.interfaces.LanguageChangedI;
import parkhomov.andrew.lensthicknesscalculator.activities.tabs.TabsPageFragmentAdapter;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.Utils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Main activity class. Customize drawers, toolbar, fragment behaviour ect.
 */
public class MainActivity extends FragmentActivity implements LanguageChangedI {

    @BindView(R.id.MyvViewPager)
    ViewPager viewPager;
    @BindView(R.id.MyTabLayout)
    TabLayout tabLayout;
    @BindView(R.id.header)
    TextView header;

    private List<String> headers = new ArrayList<>(12);
    private List<String> description = new ArrayList<>(12);
    private List<Integer> images = new ArrayList<>(12);

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

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
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(tabsPageFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setPageTransformer(false, new Utils.NoPageTransformer());
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
                    .add(R.id.mainContainerConstr, Settings.getInstance(this), CONSTANT.SETTINGS)
                    .commit();
        } catch (IllegalStateException e) {
        }
    }

    // this code reload activity when user change language
    @Override
    public void languageChanged() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        startActivity(getIntent());
    }

    private void createListWithData() {
        //add headers
        headers.add(0, getString(R.string.index_of_refraction));
        headers.add(1, getString(R.string.sphere_power));
        headers.add(2, getString(R.string.cylinder_power));
        headers.add(3, getString(R.string.axis));
        headers.add(4, getString(R.string.real_base_curve));
        headers.add(5, getString(R.string.center_thickness));
        headers.add(6, getString(R.string.edge_thickness));
        headers.add(7, getString(R.string.diameter));
        headers.add(8, getString(R.string.effective_diameter));
        headers.add(9, getString(R.string.distance_between_lenses));
        headers.add(10, getString(R.string.pupil_distance));
        headers.add(11, getString(R.string.transposition));
        // add description
        description.add(0, getString(R.string.description_index_of_refraction));
        description.add(1, getString(R.string.description_sphere_power));
        description.add(2, getString(R.string.description_cylinder_power));
        description.add(3, getString(R.string.description_axis));
        description.add(4, getString(R.string.description_real_base_curve));
        description.add(5, getString(R.string.description_center_thickness));
        description.add(6, getString(R.string.description_edge_thickness));
        description.add(7, getString(R.string.description_diameter));
        description.add(8, getString(R.string.description_effective_diameter));
        description.add(9, getString(R.string.description_distance_between_lenses));
        description.add(10, getString(R.string.description_pupil_distance));
        description.add(11,getString(R.string.description_transposition));
        // images for each item
        images.add(0, R.drawable.index_of_refraction_img);
        images.add(1, R.drawable.sphere_img);
        images.add(2, R.drawable.cylinder_img);
        images.add(3, R.drawable.axis_img);
        images.add(4, R.drawable.front_curve_img);
        images.add(5, R.drawable.thickness_gauge_img);
        images.add(6, R.drawable.edge_thickness_img);
        images.add(7, R.drawable.diam_img);
        images.add(8, R.drawable.ed_img);
        images.add(9, R.drawable.dbl_img);
        images.add(10, R.drawable.pd_img);
        images.add(11, R.drawable.transposition_img);
    }
}
