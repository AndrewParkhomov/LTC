package parkhomov.andrew.lensthicknesscalculator.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import parkhomov.andrew.lensthicknesscalculator.glossaryDatabase.GlossaryDatabase;
import parkhomov.andrew.lensthicknesscalculator.utils.UtilsDevice;
import parkhomov.andrew.lensthicknesscalculator.utils.UtilsMiscellaneous;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static boolean isTextLinkInDatabaseClicked;
    String currentLanguage;
    private DrawerLayout drawerLayout;
    private LinearLayout navDrawerEntriesRootView;
    private FrameLayout frameLayoutThknsCalc, frameLayoutDiamCalc,
            frameLayoutSettings, frameLayoutAbout,frameLayoutGlossary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void setLanguage() {
        Cursor cursor;
        Locale locale;
        Configuration config;
        //get language from database(by default English)
        SQLiteOpenHelper glossaryDatabase = new GlossaryDatabase(this);
        SQLiteDatabase db = glossaryDatabase.getReadableDatabase();
        try {
            cursor = db.query("LANGUAGE",
                    new String[]{"CURRENT_LANGUAGE"},
                    null,
                    null,
                    null, null, null);
            if (cursor.moveToFirst()) {
                currentLanguage = cursor.getString(0);
                cursor.close();
                db.close();
            }
        }catch (SQLiteException e){}
        // set language
        switch (currentLanguage) {
            case "English":
                locale = new Locale("en-gb");
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
                break;
            case "русский":
                locale = new Locale("ru");
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
                break;
            case "українська":
                locale = new Locale("uk");
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getApplicationContext().getResources().updateConfiguration(config, null);
                break;
        }
    }

    private void initialize() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.toolbar_title_lens_thkns_calc));
        }

        setUpIcons();

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
        mActionBarDrawerToggle.syncState();

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

        if(isTextLinkInDatabaseClicked){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DiamCalculatorFragment df = new DiamCalculatorFragment();
            fragmentTransaction.replace(R.id.main_activity_content_frame, df);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            if(getSupportActionBar() != null) getSupportActionBar().setTitle(getString(R.string.toolbar_title_diam_calc));
            if(drawerLayout.isDrawerOpen(GravityCompat.START))drawerLayout.closeDrawer(Gravity.LEFT);
            frameLayoutDiamCalc.setSelected(true);
            mActionBarDrawerToggle.syncState();
            isTextLinkInDatabaseClicked = false;
        }else{
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ThknsCalculatorFragment tc = new ThknsCalculatorFragment();
            fragmentTransaction.replace(R.id.main_activity_content_frame, tc);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            // Set the first item as selected for the first time
            frameLayoutThknsCalc.setSelected(true);
        }
    }


    @Override
    public void onClick(View view) {
        if (!view.isSelected()) {
            onRowPressed((FrameLayout) view);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (view == frameLayoutThknsCalc) {
                ThknsCalculatorFragment tc = new ThknsCalculatorFragment();
                fragmentTransaction.replace(R.id.main_activity_content_frame, tc);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                if (getSupportActionBar() != null){
                    getSupportActionBar().setTitle(getString(R.string.toolbar_title_lens_thkns_calc));
                }
                view.setSelected(true);
            } else if (view == frameLayoutDiamCalc) {
                DiamCalculatorFragment df = new DiamCalculatorFragment();
                fragmentTransaction.replace(R.id.main_activity_content_frame, df);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                if (getSupportActionBar() != null){
                    getSupportActionBar().setTitle(getString(R.string.toolbar_title_diam_calc));
                }
                view.setSelected(true);
            } else if (view == frameLayoutGlossary) {
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

                //Always true?
                final boolean currentViewIsMainEntry = currentView.getTag() ==
                        getResources().getString(R.string.tag_nav_drawer_main_entry);

                if (currentViewIsMainEntry) {
                    currentView.setSelected(currentView == pressedRow);
                }
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void setUpIcons() {
        // Icons tint list

        final ImageView thknsImageView =
                (ImageView) findViewById(R.id.navigation_drawer_items_list_icon_thkns_calc);
        final Drawable thknsDrawable = DrawableCompat.wrap(thknsImageView.getDrawable());
        DrawableCompat.setTintList(
                thknsDrawable.mutate(),
                ContextCompat.getColorStateList(this, R.color.nav_drawer_icon)
        );
        thknsImageView.setImageDrawable(thknsDrawable);

        final ImageView diamImageView =
                (ImageView) findViewById(R.id.navigation_drawer_items_list_icon_diam_calc);
        final Drawable diamDrawable = DrawableCompat.wrap(diamImageView.getDrawable());
        DrawableCompat.setTintList(
                diamDrawable.mutate(),
                ContextCompat.getColorStateList(this, R.color.nav_drawer_icon)
        );
        diamImageView.setImageDrawable(diamDrawable);

        final ImageView glossaryImageView =
                (ImageView) findViewById(R.id.navigation_drawer_items_list_icon_glossary);
        final Drawable glossaryDrawable = DrawableCompat.wrap(glossaryImageView.getDrawable());
        DrawableCompat.setTintList(
                glossaryDrawable.mutate(),
                ContextCompat.getColorStateList(this, R.color.nav_drawer_icon)
        );
        glossaryImageView.setImageDrawable(glossaryDrawable);
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
                            MainActivity.super.finish();
                        }
                    }).create().show();
        }
    }
}
