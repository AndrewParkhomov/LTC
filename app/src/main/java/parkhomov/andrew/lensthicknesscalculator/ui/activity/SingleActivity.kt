package parkhomov.andrew.lensthicknesscalculator.ui.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.MenuCompat
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import kotlinx.android.synthetic.main.single_activity.*
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.BuildConfig
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseActivity
import parkhomov.andrew.lensthicknesscalculator.data.result.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.language.Language
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter.Diameter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.Glossary
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness.Thickness
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.transposition.Transposition
import parkhomov.andrew.lensthicknesscalculator.utils.diameter
import parkhomov.andrew.lensthicknesscalculator.utils.glossary
import parkhomov.andrew.lensthicknesscalculator.utils.navigation.BackButtonListener
import parkhomov.andrew.lensthicknesscalculator.utils.navigation.Screens
import parkhomov.andrew.lensthicknesscalculator.utils.thickness
import parkhomov.andrew.lensthicknesscalculator.utils.transposition
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command


class SingleActivity : BaseActivity(),
        SingleActivityI.View {

    private val navigatorHolder: NavigatorHolder by inject()
    override val presenter: SingleActivityI.Presenter  by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_activity)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        supportActionBar?.title = getString(R.string.app_name)

        presenter.onAttach(this)

        createListWithData()

        presenter.initViews()
        if (savedInstanceState == null) {
            bottom_navigation_bar.selectTab(thickness, true)
        }
    }

    override fun onResumeFragments() {
        navigatorHolder.setNavigator(navigator)
        super.onResumeFragments()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_language -> presenter.onLanguageClicked()
            R.id.menu_item_rate -> presenter.onRateThisAppClicked()
            R.id.menu_item_share -> presenter.onShareResultClicked()
            R.id.menu_item_about -> presenter.onAboutClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initViews() {
        bottom_navigation_bar
                .addItem(BottomNavigationItem(R.drawable.ic_thickness, R.string.fragment_thickness))
                .addItem(BottomNavigationItem(R.drawable.ic_diameter, R.string.fragment_diameter))
                .addItem(BottomNavigationItem(R.drawable.ic_transposition, R.string.fragment_transposition))
                .addItem(BottomNavigationItem(R.drawable.ic_glossary, R.string.fragment_glossary))
                .initialise()
        bottom_navigation_bar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                val tabId = when (position) {
                    diameter -> Diameter.TAG
                    transposition -> Transposition.TAG
                    glossary -> Glossary.TAG
                    else -> Thickness.TAG
                }
                presenter.selectTab(tabId, position)
            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabReselected(position: Int) {
                onTabSelected(position)
            }
        })

    }

    override fun selectTabPosition(position: Int) {
        bottom_navigation_bar.selectTab(position, false)
    }

    override fun selectTab(tabId: String) {
        var currentFragment: Fragment? = null
        val fragments = supportFragmentManager.fragments
        for (f in fragments) {
            if (f.isVisible) {
                currentFragment = f
                break
            }
        }
        val newFragment = supportFragmentManager.findFragmentByTag(tabId)

        if (currentFragment != null && newFragment != null && currentFragment === newFragment) return

        val transaction = supportFragmentManager.beginTransaction()
        if (newFragment == null) {
            transaction.add(R.id.frame_layout_tab_container, Screens.GetBottomTabFragment(tabId).fragment, tabId)
        }

        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }

        if (newFragment != null) {
            transaction.show(newFragment)
        }
        transaction.commitNow()
    }

    private val navigator = object : SupportAppNavigator(
            this,
            R.id.container_parent
    ) {

        override fun applyCommands(commands: Array<Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }

    }

    override fun showLanguageDialog() {
        Language.instance.show(supportFragmentManager)
    }

    override fun showRateThisAppDialog() {
        AlertDialog.Builder(this, R.style.AlertDialogCustom)
                .setTitle(R.string.rate_app_header)
                .setMessage(R.string.rate_app_body)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    presenter.onRateAppClicked()
                }
                .create()
                .show()
    }

    override fun openGooglePlay() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.app_google_play_link) + packageName)))
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.app_google_play_link) + packageName)))
        }
    }

    override fun createStringForSharing(calculatedData: CalculatedData) {
        val linkInPlayStore = "https://play.google.com/store/apps/details?id=" +
                BuildConfig.APPLICATION_ID

        val sharedText = if (calculatedData.cylinderPower == null) {
            getString(R.string.share_text_only_sphere,
                    getString(R.string.app_name),
                    linkInPlayStore,
                    calculatedData.refractionIndex,
                    calculatedData.spherePower,
                    calculatedData.thicknessCenter,
                    calculatedData.thicknessEdge,
                    calculatedData.realBaseCurve,
                    calculatedData.diameter
            )
        } else {
            getString(R.string.share_text_full,
                    getString(R.string.app_name),
                    linkInPlayStore,
                    calculatedData.refractionIndex,
                    calculatedData.spherePower,
                    calculatedData.cylinderPower,
                    calculatedData.axis,
                    calculatedData.axis,
                    calculatedData.thicknessOnAxis,
                    calculatedData.thicknessCenter,
                    calculatedData.thicknessEdge,
                    calculatedData.thicknessMax,
                    calculatedData.realBaseCurve,
                    calculatedData.diameter
            )
        }
        presenter.setTextForSharing(sharedText)
    }

    override fun shareResult(sharedText: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, "")
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_result_subject))
            putExtra(Intent.EXTRA_TEXT, sharedText)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share_with)))
    }

    override fun showSnackbar(resId: Int) {
        Snackbar.make(frame_layout_tab_container, resId, Snackbar.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        var fragment: Fragment? = null
        val fragments = supportFragmentManager.fragments
        for (f in fragments) {
            if (f.isVisible) {
                fragment = f
                break
            }
        }
        if (fragment != null
                && fragment is BackButtonListener
                && (fragment as BackButtonListener).onBackPressed()) {
            return
        } else {
            AlertDialog.Builder(this, R.style.AlertDialogCustom)
                    .setTitle(R.string.exit_question_title)
                    .setMessage(R.string.exit_question)
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes) { _, _ ->
                        android.os.Process.killProcess(android.os.Process.myPid())
                    }.create().show()
        }
    }

    override fun showAboutDialog() {
        val version = getString(R.string.version, BuildConfig.VERSION_NAME)
        AlertDialog.Builder(this, R.style.AlertDialogCustom)
                .setMessage(version)
                .setPositiveButton(android.R.string.yes) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
    }

}
