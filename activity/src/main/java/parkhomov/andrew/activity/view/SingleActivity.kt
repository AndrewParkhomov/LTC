package parkhomov.andrew.activity.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuCompat
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.single_activity.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.activity.BuildConfig
import parkhomov.andrew.activity.R
import parkhomov.andrew.activity.viewmodel.ViewModelActivity
import parkhomov.andrew.base.base.BaseActivity
import parkhomov.andrew.base.data.result.CalculatedData
import parkhomov.andrew.base.extension.observe
import parkhomov.andrew.base.utils.compare
import parkhomov.andrew.base.utils.diameter
import parkhomov.andrew.base.utils.navigation.BackButtonListener
import parkhomov.andrew.base.utils.thickness
import parkhomov.andrew.base.utils.transposition
import parkhomov.andrew.comparelist.view.CompareList
import parkhomov.andrew.language.view.Language
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command


class SingleActivity : BaseActivity() {

    private val navigatorHolder: NavigatorHolder by inject()
    private val viewModel: ViewModelActivity by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_activity)
        supportActionBar?.title = getString(R.string.app_name)

        observe(viewModel.state) { onStateChanged(it) }
        createListWithData()
        initViews()
    }

    private fun onStateChanged(event: ViewModelActivity.State) {
        when (event) {
            is ViewModelActivity.State.ShowSnackbar -> showSnackbar(event.id)
            is ViewModelActivity.State.CreateStringForSharing -> createStringForSharing(event.data)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_language -> Language.instance.show(supportFragmentManager)
            R.id.menu_item_rate -> showRateThisAppDialog()
            R.id.menu_item_share -> viewModel.onShareResultClicked()
            R.id.menu_item_about -> showAboutDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews() {
        bottom_navigation_bar
                .addItem(BottomNavigationItem(R.drawable.ic_thickness, R.string.fragment_thickness))
                .addItem(BottomNavigationItem(R.drawable.ic_compare_arrows, R.string.fragment_compare))
                .addItem(BottomNavigationItem(R.drawable.ic_diameter, R.string.fragment_diameter))
                .addItem(BottomNavigationItem(R.drawable.ic_transposition, R.string.fragment_transposition))
                .addItem(BottomNavigationItem(R.drawable.ic_glossary, R.string.fragment_glossary))
                .setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
                    override fun onTabSelected(position: Int) {
                        val tabId = when (position) {
                            thickness -> parkhomov.andrew.thickness.navigation.Screens.ScreenThickness.fragment
                            compare -> parkhomov.andrew.comparelist.navigation.Screens.ScreenCompareList.fragment
                            diameter -> parkhomov.andrew.diameter.navigation.Screens.ScreenDiameter.fragment
                            transposition -> parkhomov.andrew.transposition.navigation.Screens.ScreenTransposition.fragment
                            else -> parkhomov.andrew.glossary.navigation.Screens.ScreenGlossary.fragment
                        }
                        bottom_navigation_bar.selectTab(position, false)
                        openNewTab(tabId)
                    }

                    override fun onTabUnselected(position: Int) {

                    }

                    override fun onTabReselected(position: Int) {
                    }
                }).initialise()
        bottom_navigation_bar.selectTab(thickness, true)
    }

    private fun openNewTab(newFragment: Fragment?) {
        if (newFragment == null) {
            throw RuntimeException("Tab screen is null")
        }
        var currentFragment: Fragment? = null
        val fragments = supportFragmentManager.fragments
        for (f in fragments) {
            if (f.isVisible) {
                currentFragment = f
                break
            }
        }

        if (currentFragment != null && currentFragment === newFragment) return
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout_tab_container, newFragment)
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }
        transaction.show(newFragment)
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

    private fun showRateThisAppDialog() {
        AlertDialog.Builder(this, R.style.AlertDialogCustom)
                .setTitle(R.string.rate_app_header)
                .setMessage(R.string.rate_app_body)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    openGooglePlay()
                }
                .create()
                .show()
    }

    private fun openGooglePlay() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.app_google_play_link) + packageName)))
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.app_google_play_link) + packageName)))
        }
    }

    private fun createStringForSharing(calculatedData: CalculatedData?) {
        calculatedData?.let {
            val linkInPlayStore = "https://play.google.com/store/apps/details?id=$packageName"
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
            shareResult(sharedText)
        }
    }

    private fun shareResult(sharedText: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, "")
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_result_subject))
            putExtra(Intent.EXTRA_TEXT, sharedText)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share_with)))
    }

    private fun showSnackbar(resId: Int) {
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

    private fun showAboutDialog() {
        val version = getString(R.string.version, BuildConfig.VERSION_NAME)
        AlertDialog.Builder(this, R.style.AlertDialogCustom)
                .setMessage(version)
                .setPositiveButton(android.R.string.yes) { dialog, _ -> }
                .create()
                .show()
    }

}
