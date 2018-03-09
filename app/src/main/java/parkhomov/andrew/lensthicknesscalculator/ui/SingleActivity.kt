package parkhomov.andrew.lensthicknesscalculator.ui

import android.os.Bundle
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.single_activity.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseActivity
import parkhomov.andrew.lensthicknesscalculator.ui.glossary.GlossaryList
import parkhomov.andrew.lensthicknesscalculator.ui.settings.Language
import parkhomov.andrew.lensthicknesscalculator.ui.settings.Settings
import parkhomov.andrew.lensthicknesscalculator.ui.tabs.TabsPageFragmentAdapter
import parkhomov.andrew.lensthicknesscalculator.utils.Utils
import parkhomov.andrew.lensthicknesscalculator.utils.const
import timber.log.Timber

class SingleActivity : BaseActivity(), Language.LanguageChangedI {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_activity)
        createTabs()
        openGlossary.setOnClickListener { onGlossaryClicked() }
        openSettings.setOnClickListener { onSettingsClicked() }
        setUpListeners()
    }


    private fun createTabs() {
        val tabsPageFragmentAdapter = TabsPageFragmentAdapter(
                supportFragmentManager,
                headers,
                description,
                images
        )
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = tabsPageFragmentAdapter
        tabLayout.setupWithViewPager(viewPager)

        viewPager.setPageTransformer(false, Utils.NoPageTransformer())
        tabLayout.getTabAt(0)?.text = getString(R.string.tab_lens_thickness)
        tabLayout.getTabAt(1)?.text = getString(R.string.tab_diameter)
    }

    private fun setUpListeners() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                hideKeyboard()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun onGlossaryClicked() {
        try {
            supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.mainContainerConstr, GlossaryList.getInstance(headers, description, images), const.GLOSSARY_LIST)
                    .commit()
        } catch (e: IllegalStateException) {
            Timber.i(e.toString())
        }
        hideKeyboard()
    }

    private fun onSettingsClicked() {
        try {
            supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.mainContainerConstr, Settings.getInstance(this), const.SETTINGS)
                    .commit()
        } catch (e: IllegalStateException) {
            Timber.i(e.toString())
        }
        hideKeyboard()
    }

    // this code reload activity when user change language
    override fun languageChanged() {
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        startActivity(intent)
    }


}