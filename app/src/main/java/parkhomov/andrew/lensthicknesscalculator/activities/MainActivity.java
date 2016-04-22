package parkhomov.andrew.lensthicknesscalculator.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.customViews.ScrimInsetsFrameLayout;
import parkhomov.andrew.lensthicknesscalculator.utils.UtilsDevice;
import parkhomov.andrew.lensthicknesscalculator.utils.UtilsMiscellaneous;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawerLayout mDrawerLayout;
    private LinearLayout mNavDrawerEntriesRootView;
    private PercentRelativeLayout mFrameLayout_AccountView;
    private FrameLayout mFrameLayoutHome, mFrameLayoutThknsCalc, mFrameLayoutDiamCalc,
            mFrameLayout_Settings, mFrameLayout_About;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        setUpIcons();

        mNavDrawerEntriesRootView = (LinearLayout)findViewById
                (R.id.navigation_drawer_linearLayout_entries_root_view);

//        mFrameLayout_AccountView = (PercentRelativeLayout) findViewById
//        (R.id.navigation_drawer_account_view);

        mFrameLayoutHome = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_list_linearLayout_home);

        mFrameLayoutThknsCalc = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_list_linearLayout_thkns_calc);

        mFrameLayoutDiamCalc = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_list_linearLayout_diamCalc);

        mFrameLayout_About = (FrameLayout)findViewById
                (R.id.navigation_drawer_items_list_linearLayout_about);

        mFrameLayout_Settings = (FrameLayout)findViewById
                (R.id.navigation_drawer_items_list_linearLayout_settings);

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
        if (view == mFrameLayout_AccountView) {

            mDrawerLayout.closeDrawer(GravityCompat.START);

            // SignUp/SignIn/Profile
            // TODO on account pressed
        }else {
            if (!view.isSelected()) {
                onRowPressed((FrameLayout) view);
                if (view == mFrameLayoutHome) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }else if (view == mFrameLayoutThknsCalc) {
                    startActivity(new Intent(view.getContext(), ThknsCalculatorActivity.class));
                }else if (view == mFrameLayoutDiamCalc) {
                    startActivity(new Intent(view.getContext(), DiamCalculatorActivity.class));
                }else if (view == mFrameLayout_About) {
                    startActivity(new Intent(view.getContext(), AboutDialogActivity.class));
                }else if (view == mFrameLayout_Settings) {
                    startActivity(new Intent(view.getContext(), SettingsActivity.class));
                }
            }else{
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        }
    }

    private void onRowPressed(@NonNull final FrameLayout pressedRow)
    {
        if (pressedRow.getTag() != getResources().getString(R.string.tag_nav_drawer_special_entry))
        {
            for (int i = 0; i < mNavDrawerEntriesRootView.getChildCount(); i++)
            {
                final View currentView = mNavDrawerEntriesRootView.getChildAt(i);

                final boolean currentViewIsMainEntry = currentView.getTag() ==
                        getResources().getString(R.string.tag_nav_drawer_main_entry);

                if (currentViewIsMainEntry)
                {
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
    }
}
