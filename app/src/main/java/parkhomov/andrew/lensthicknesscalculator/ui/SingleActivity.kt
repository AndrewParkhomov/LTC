package parkhomov.andrew.lensthicknesscalculator.ui

import android.os.Bundle
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.single_activity.*
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseActivity
import parkhomov.andrew.lensthicknesscalculator.ui.glossary.GlossaryList
import parkhomov.andrew.lensthicknesscalculator.ui.settings.Settings
import parkhomov.andrew.lensthicknesscalculator.ui.tabs.TabsPageFragmentAdapter
import timber.log.Timber

class SingleActivity : BaseActivity() {

    private var adapter = TabsPageFragmentAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_activity)
        openGlossary.setOnClickListener { onGlossaryClicked() }
        openSettings.setOnClickListener { onSettingsClicked() }
        createListWithData()
        createTabs()
    }

    private fun createTabs() {
        adapter.createTabs()
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        viewPager.setPageTransformer(false, pageTransformer)
        tabLayout.getTabAt(0)?.text = getString(R.string.tab_lens_thickness)
        tabLayout.getTabAt(1)?.text = getString(R.string.tab_diameter)
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

    private val pageTransformer = ViewPager.PageTransformer { view, position ->
        when {
            position < 0 -> view.scrollX = (view.width.toFloat() * position).toInt()
            position > 0 -> view.scrollX = -(view.width.toFloat() * -position).toInt()
            else -> view.scrollX = 0
        }
    }

    private fun onGlossaryClicked() {
        try {
            supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.mainContainerConstr, GlossaryList.instance, GlossaryList.TAG)
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
                    .add(R.id.mainContainerConstr, Settings.instance, Settings.TAG)
                    .commit()
        } catch (e: IllegalStateException) {
            Timber.i(e.toString())
        }
        hideKeyboard()
    }

    override fun changeLanguage() {
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        startActivity(intent)
    }
}
