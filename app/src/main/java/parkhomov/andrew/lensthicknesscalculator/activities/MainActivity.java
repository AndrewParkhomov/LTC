package parkhomov.andrew.lensthicknesscalculator.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Locale;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.customViews.ScrimInsetsFrameLayout;
import parkhomov.andrew.lensthicknesscalculator.fragments.DiamCalculatorFragment;
import parkhomov.andrew.lensthicknesscalculator.fragments.ThknsCalculatorFragment;
import parkhomov.andrew.lensthicknesscalculator.utils.UtilsDevice;
import parkhomov.andrew.lensthicknesscalculator.utils.UtilsMiscellaneous;

/**
 * Main activity class. Customize drawers, toolbar, fragment behaviour ect.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static boolean isTextLinkInDatabaseClicked;
    private DrawerLayout drawerLayout;
    private LinearLayout navDrawerEntriesRootView;
    private FrameLayout frameLayoutThknsCalc, frameLayoutDiamCalc,
            frameLayoutSettings, frameLayoutAbout,frameLayoutGlossary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLanguage();
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void setUpLanguage() {
        LanguageManagerActivity languageManager = new LanguageManagerActivity(this);
        languageManager.setCurrentLanguage();
    }

    private void initialize() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.string_thkns_calc));
        }

        setUpIcons(R.id.navigation_drawer_items_list_icon_thkns_calc);
        setUpIcons(R.id.navigation_drawer_items_list_icon_diam_calc);
        setUpIcons(R.id.navigation_drawer_items_list_icon_glossary);

        navDrawerEntriesRootView = (LinearLayout) findViewById
                (R.id.navigation_drawer_linearLayout_entries_root_view);

        frameLayoutThknsCalc = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_list_linearLayout_thkns_calc);

        frameLayoutDiamCalc = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_list_linearLayout_diamCalc);

        frameLayoutAbout = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_list_linearLayout_about);

        frameLayoutSettings = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_list_linearLayout_settings);

        frameLayoutGlossary = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_linearLayout_glossary);

        // Navigation Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);

        final ScrimInsetsFrameLayout mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout)
                findViewById(R.id.main_activity_navigation_drawer_rootLayout);

        final ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_opened,
                R.string.navigation_drawer_closed) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Disables the burger/arrow animation by default
                super.onDrawerSlide(drawerView, 0);
            }
        };
        drawerLayout.openDrawer(Gravity.LEFT);
        drawerLayout.setDrawerListener(mActionBarDrawerToggle);

        // Navigation Drawer layout width
        final int possibleMinDrawerWidth =
                UtilsDevice.getScreenWidth(this) -
                        UtilsMiscellaneous
                                .getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);

        final int maxDrawerWidth =
                getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width =
                Math.min(possibleMinDrawerWidth, maxDrawerWidth);

        // Nav Drawer item click listener

        frameLayoutThknsCalc.setOnClickListener(this);
        frameLayoutDiamCalc.setOnClickListener(this);
        frameLayoutGlossary.setOnClickListener(this);
        frameLayoutAbout.setOnClickListener(this);
        frameLayoutSettings.setOnClickListener(this);

        Fragment fragment;
        String title;
        if(isTextLinkInDatabaseClicked){
            fragment = new DiamCalculatorFragment();
            title = getString(R.string.string_diam_calc);
            if(drawerLayout.isDrawerOpen(GravityCompat.START))drawerLayout.closeDrawer(Gravity.LEFT);
            frameLayoutDiamCalc.setSelected(true);
            isTextLinkInDatabaseClicked = false;
        }else{
            fragment = new ThknsCalculatorFragment();
            title = getString(R.string.string_thkns_calc);
            frameLayoutThknsCalc.setSelected(true);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        mActionBarDrawerToggle.syncState();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }


    @Override
    public void onClick(View view) {
        if (!view.isSelected()) {
            onRowPressed((FrameLayout) view);
            Fragment fragment = null;
            String title = null;
            if (view == frameLayoutThknsCalc) {
                fragment = new ThknsCalculatorFragment();
                title = getString(R.string.string_thkns_calc);
            } else if (view == frameLayoutDiamCalc) {
                fragment = new DiamCalculatorFragment();
                title = getString(R.string.string_diam_calc);
            }
            if (fragment != null){
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_activity_content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                view.setSelected(true);
                if (getSupportActionBar() != null){
                    getSupportActionBar().setTitle(title);
                }
            }
            if (view == frameLayoutGlossary) {
                startActivity(new Intent(view.getContext(), GlossaryListActivity.class));
            } else if (view == frameLayoutAbout) {
                startActivity(new Intent(view.getContext(), AboutDialogActivity.class));
            } else if (view == frameLayoutSettings) {
                startActivity(new Intent(view.getContext(), SettingsActivity.class));
            }
        } else {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void onRowPressed(@NonNull final FrameLayout pressedRow) {
        if (pressedRow.getTag() != getResources().getString(R.string.tag_nav_drawer_special_entry))
        {
            for (int i = 0; i < navDrawerEntriesRootView.getChildCount(); i++)
            {
                final View currentView = navDrawerEntriesRootView.getChildAt(i);

                final boolean currentViewIsMainEntry = currentView.getTag() ==
                        getResources().getString(R.string.tag_nav_drawer_main_entry);

                if (currentViewIsMainEntry) {
                    currentView.setSelected(currentView == pressedRow);
                }
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void setUpIcons(int imageId) {
        // Icons tint list
        final ImageView imageView =
                (ImageView) findViewById(imageId);
        if(imageView != null){
            final Drawable drawable = DrawableCompat.wrap(imageView.getDrawable());
            DrawableCompat.setTintList(
                    drawable.mutate(),
                    ContextCompat.getColorStateList(this, R.color.nav_drawer_icon)
            );
            imageView.setImageDrawable(drawable);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(Gravity.LEFT);
        }else{
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
}
