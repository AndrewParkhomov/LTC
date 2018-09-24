package parkhomov.andrew.lensthicknesscalculator.ui.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
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
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.dialog.result.Result
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter.Diameter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.Glossary
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness.Thickness
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.transposition.Transposition
import parkhomov.andrew.lensthicknesscalculator.utils.*
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace


class SingleActivity : BaseActivity(),
        SingleActivityI.View {

    private val navigatorHolder: NavigatorHolder by inject()
    override val presenter: SingleActivityI.Presenter  by inject()

    private var fragmentThickness: Thickness? = null
    private var fragmentDiameter: Diameter? = null
    private var fragmentTransposition: Transposition? = null
    private var fragmentGlossary: Glossary? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_activity)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        supportActionBar?.title = getString(R.string.app_name)

        presenter.onAttach(this)

        createListWithData()

        initViews()
        initContainers()
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

    private fun initViews() {
        bottom_navigation_bar
                .addItem(BottomNavigationItem(R.drawable.ic_thickness, R.string.fragment_thickness))
                .addItem(BottomNavigationItem(R.drawable.ic_diameter, R.string.fragment_diameter))
                .addItem(BottomNavigationItem(R.drawable.ic_transposition, R.string.fragment_transposition))
                .addItem(BottomNavigationItem(R.drawable.ic_glossary, R.string.fragment_glossary))
                .initialise()
        bottom_navigation_bar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                when (position) {
                    thickness -> presenter.onThicknessClicked()
                    diameter -> presenter.onDiameterClicked()
                    transposition -> presenter.onTranspositionClicked()
                    glossary -> presenter.onGlossaryClicked()
                }
            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabReselected(position: Int) {
                onTabSelected(position)
            }
        })

    }

    private fun initContainers() {
        val fm = supportFragmentManager

        fragmentThickness = fm.findFragmentByTag(Thickness.TAG) as Thickness?
        if (fragmentThickness == null) {
            fragmentThickness = Thickness.instance
            fm.beginTransaction()
                    .add(R.id.frame_layout_tab_container, fragmentThickness!!, Thickness.TAG)
                    .detach(fragmentThickness!!).commitNow()
        }

        fragmentDiameter = fm.findFragmentByTag(Diameter.TAG) as Diameter?
        if (fragmentDiameter == null) {
            fragmentDiameter = Diameter.instance
            fm.beginTransaction()
                    .add(R.id.frame_layout_tab_container, fragmentDiameter!!, Diameter.TAG)
                    .detach(fragmentDiameter!!).commitNow()
        }

        fragmentTransposition = fm.findFragmentByTag(Transposition.TAG) as Transposition?
        if (fragmentTransposition == null) {
            fragmentTransposition = Transposition.instance
            fm.beginTransaction()
                    .add(R.id.frame_layout_tab_container, fragmentTransposition!!, Transposition.TAG)
                    .detach(fragmentTransposition!!).commitNow()
        }

        fragmentGlossary = fm.findFragmentByTag(Glossary.TAG) as Glossary?
        if (fragmentGlossary == null) {
            fragmentGlossary = Glossary.instance
            fm.beginTransaction()
                    .add(R.id.frame_layout_tab_container, fragmentGlossary!!, Glossary.TAG)
                    .detach(fragmentGlossary!!).commitNow()
        }
    }

    override fun highlightTab(position: Int) {
        bottom_navigation_bar.selectTab(position, false)
    }

    private val navigator = object : SupportFragmentNavigator(
            supportFragmentManager,
            R.id.mainContainerConstr
    ) {
        override fun createFragment(screenKey: String, data: Any?): Fragment {
            return throw RuntimeException("No screen key provided")
        }


        override fun exit() {

        }

        override fun showSystemMessage(message: String) {

        }

        override fun back() {
            presenter.onBackPressed()
        }

        override fun replace(command: Replace?) {
            val fm = supportFragmentManager
            when (command?.screenKey) {
                Thickness.TAG -> fm.beginTransaction()
                        .detach(fragmentDiameter!!)
                        .detach(fragmentTransposition!!)
                        .detach(fragmentGlossary!!)
                        .attach(fragmentThickness!!)
                        .commitNow()
                Diameter.TAG -> fm.beginTransaction()
                        .detach(fragmentThickness!!)
                        .detach(fragmentTransposition!!)
                        .detach(fragmentGlossary!!)
                        .attach(fragmentDiameter!!)
                        .commitNow()
                Transposition.TAG -> fm.beginTransaction()
                        .detach(fragmentThickness!!)
                        .detach(fragmentDiameter!!)
                        .detach(fragmentGlossary!!)
                        .attach(fragmentTransposition!!)
                        .commitNow()
                Glossary.TAG -> fm.beginTransaction()
                        .detach(fragmentThickness!!)
                        .detach(fragmentDiameter!!)
                        .detach(fragmentTransposition!!)
                        .attach(fragmentGlossary!!)
                        .commitNow()
            }
        }

        override fun forward(command: Forward) {
            when (command.screenKey) {
                Result.TAG -> Result.getInstance(command.transitionData as CalculatedData)
                        .show(supportFragmentManager)
                Language.TAG -> Language.instance.show(supportFragmentManager)
                else -> super.forward(command)
            }
        }

        override fun applyCommands(commands: Array<Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }

    }

    override fun showRateThisAppDialog() {
        AlertDialog.Builder(this)
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
        presenter.setTestForSharing(sharedText)
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

    override fun showToast(resId: Int) {
        showMessage(resId)
    }

    override fun showAboutDialog() {
        val version = getString(R.string.version, BuildConfig.VERSION_NAME)
        AlertDialog.Builder(this)
                .setMessage(version)
                .setPositiveButton(android.R.string.yes) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
    }

}
