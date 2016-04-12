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
    private FrameLayout mFrameLayout_Home, mFrameLayout_Explore,
            mFrameLayout_HelpAndFeedback, mFrameLayout_About;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        startActivity(new Intent(this, CalculatorActivity.class));

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        setUpIcons();

        mFrameLayout_Explore = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_list_linearLayout_explore);

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

        mFrameLayout_Explore.setOnClickListener(this);

        // Set the first item as selected for the first time
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.toolbar_title_home);
        }

//        mFrameLayout_Home.setSelected(true);
    }

    @Override
    public void onClick(View view) {
        if (view == mFrameLayout_AccountView) {

            mDrawerLayout.closeDrawer(GravityCompat.START);

            // SignUp/SignIn/Profile
            //TODO on account pressed
        }else {
            if (!view.isSelected())
            {
                //onRowPressed((FrameLayout) view);

                if (view == mFrameLayout_Explore) {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(getString(R.string.toolbar_title_explore));
                    }
                }
                view.setSelected(true);
                startActivity(new Intent(view.getContext(), CalculatorActivity.class));
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
                    currentView.setSelected(currentView == pressedRow);
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
        DrawableCompat.setTintList
                (
                        homeDrawable.mutate(),
                        ContextCompat.getColorStateList(this, R.color.nav_drawer_icon)
                );

        homeImageView.setImageDrawable(homeDrawable);

        final ImageView exploreImageView =
                (ImageView) findViewById(R.id.navigation_drawer_items_list_icon_explore);
        final Drawable exploreDrawable = DrawableCompat.wrap(exploreImageView.getDrawable());
        DrawableCompat.setTintList
                (
                        exploreDrawable.mutate(),
                        ContextCompat.getColorStateList(this, R.color.nav_drawer_icon)
                );

        exploreImageView.setImageDrawable(exploreDrawable);
    }
}
