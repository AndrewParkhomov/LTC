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
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Locale;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.customViews.ScrimInsetsFrameLayout;
import parkhomov.andrew.lensthicknesscalculator.glossaryDatabase.GlossaryDatabase;
import parkhomov.andrew.lensthicknesscalculator.utils.UtilsDevice;
import parkhomov.andrew.lensthicknesscalculator.utils.UtilsMiscellaneous;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String currentLanguage;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mNavDrawerEntriesRootView;
    private FrameLayout mFrameLayoutHome, mFrameLayoutThknsCalc, mFrameLayoutDiamCalc,
            mFrameLayout_Settings, mFrameLayout_About,mFrameLayout_Glossary;

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
        //get name of language from database(by default English)
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
        }catch (SQLiteException e){
            Toast.makeText(this, "data wrong", Toast.LENGTH_LONG).show();
        }
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
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        setUpIcons();

        mNavDrawerEntriesRootView = (LinearLayout) findViewById
                (R.id.navigation_drawer_linearLayout_entries_root_view);

        mFrameLayoutHome = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_list_linearLayout_home);

        mFrameLayoutThknsCalc = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_list_linearLayout_thkns_calc);

        mFrameLayoutDiamCalc = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_list_linearLayout_diamCalc);

        mFrameLayout_About = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_list_linearLayout_about);

        mFrameLayout_Settings = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_list_linearLayout_settings);

        mFrameLayout_Glossary = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_linearLayout_glossary);

        // Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);

        final ScrimInsetsFrameLayout mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout)
                findViewById(R.id.main_activity_navigation_drawer_rootLayout);

        final ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.navigation_drawer_opened,
                R.string.navigation_drawer_closed) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Disables the burger/arrow animation by default
                super.onDrawerSlide(drawerView, 0);
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

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

        mFrameLayoutThknsCalc.setOnClickListener(this);
        mFrameLayoutDiamCalc.setOnClickListener(this);
        mFrameLayoutHome.setOnClickListener(this);
        mFrameLayout_Glossary.setOnClickListener(this);
        mFrameLayout_About.setOnClickListener(this);
        mFrameLayout_Settings.setOnClickListener(this);

        // Set the first item as selected for the first time
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.toolbar_title_home);
        }
        mFrameLayoutHome.setSelected(true);
    }

    @Override
    public void onClick(View view) {
        if (!view.isSelected()) {
            onRowPressed((FrameLayout) view);
            if (view == mFrameLayoutHome) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else if (view == mFrameLayoutThknsCalc) {
                startActivity(new Intent(view.getContext(), ThknsCalculatorActivity.class));
            } else if (view == mFrameLayoutDiamCalc) {
                startActivity(new Intent(view.getContext(), DiamCalculatorActivity.class));
            } else if (view == mFrameLayout_Glossary) {
                startActivity(new Intent(view.getContext(), GlossaryListActivity.class));
            } else if (view == mFrameLayout_About) {
                startActivity(new Intent(view.getContext(), AboutDialogActivity.class));
            } else if (view == mFrameLayout_Settings) {
                startActivity(new Intent(view.getContext(), SettingsActivity.class));
            }
        } else {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void onRowPressed(@NonNull final FrameLayout pressedRow) {
        if (pressedRow.getTag() != getResources().getString(R.string.tag_nav_drawer_special_entry)) {
            for (int i = 0; i < mNavDrawerEntriesRootView.getChildCount(); i++) {
                final View currentView = mNavDrawerEntriesRootView.getChildAt(i);

                final boolean currentViewIsMainEntry = currentView.getTag() ==
                        getResources().getString(R.string.tag_nav_drawer_main_entry);

                if (currentViewIsMainEntry) {
                    mFrameLayoutHome.setSelected(true);
                }
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void setUpIcons() {
        // Icons tint list
        final ImageView homeImageView =
                (ImageView) findViewById(R.id.navigation_drawer_items_list_icon_home);
        final Drawable homeDrawable = DrawableCompat.wrap(homeImageView.getDrawable());
        DrawableCompat.setTintList(
                homeDrawable.mutate(),
                ContextCompat.getColorStateList(this, R.color.nav_drawer_icon)
        );

        homeImageView.setImageDrawable(homeDrawable);

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
