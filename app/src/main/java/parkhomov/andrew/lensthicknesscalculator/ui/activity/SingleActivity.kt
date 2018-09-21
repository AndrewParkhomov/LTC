package parkhomov.andrew.lensthicknesscalculator.ui.activity

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import kotlinx.android.synthetic.main.single_activity.*
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.base.BaseActivity
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.diameter.Diameter
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.glossary.Glossary
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.settings.AboutDialogActivity
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.settings.Language
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.thickness.Thickness
import parkhomov.andrew.lensthicknesscalculator.ui.fragment.transposition.Transposition
import parkhomov.andrew.lensthicknesscalculator.utils.diameter
import parkhomov.andrew.lensthicknesscalculator.utils.transposition
import parkhomov.andrew.lensthicknesscalculator.utils.glossary
import parkhomov.andrew.lensthicknesscalculator.utils.thickness
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
    private var fragmentGlossaryList: Glossary? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_activity)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        presenter.onAttach(this)

        createListWithData()
//        createTabs()

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
        val fm = supportFragmentManager!!
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
        fragmentGlossaryList = fm.findFragmentByTag(Glossary.TAG) as Glossary?
        if (fragmentGlossaryList == null) {
            fragmentGlossaryList = Glossary.instance
            fm.beginTransaction()
                    .add(R.id.frame_layout_tab_container, fragmentGlossaryList!!, Glossary.TAG)
                    .detach(fragmentGlossaryList!!).commitNow()
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
            return when (screenKey) {
                AboutDialogActivity.TAG -> AboutDialogActivity.instance
                else -> throw RuntimeException("No screen key provided")
            }
        }


        override fun exit() {

        }

        override fun showSystemMessage(message: String) {

        }

        override fun back() {
            presenter.onBackPressed()
        }

        override fun replace(command: Replace?) {
            val fm = supportFragmentManager!!
            when (command?.screenKey) {
                Thickness.TAG -> fm.beginTransaction()
                        .detach(fragmentDiameter!!)
                        .detach(fragmentTransposition!!)
                        .detach(fragmentGlossaryList!!)
                        .attach(fragmentThickness!!)
                        .commitNow()
                Diameter.TAG -> fm.beginTransaction()
                        .detach(fragmentThickness!!)
                        .detach(fragmentTransposition!!)
                        .detach(fragmentGlossaryList!!)
                        .attach(fragmentDiameter!!)
                        .commitNow()
                Transposition.TAG -> fm.beginTransaction()
                        .detach(fragmentThickness!!)
                        .detach(fragmentDiameter!!)
                        .detach(fragmentGlossaryList!!)
                        .attach(fragmentTransposition!!)
                        .commitNow()
                Glossary.TAG -> fm.beginTransaction()
                        .detach(fragmentThickness!!)
                        .detach(fragmentDiameter!!)
                        .detach(fragmentTransposition!!)
                        .attach(fragmentGlossaryList!!)
                        .commitNow()
            }
        }

        override fun forward(command: Forward) {
            when (command.screenKey) {
//                DialogInfo.TAG -> DialogInfo.getInstance(command.transitionData as DialogTransporter)
//                        .show(supportFragmentManager)
                else -> super.forward(command)
            }
        }


        override fun applyCommands(commands: Array<Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }

    }

    override fun showRateThisAppDialog() {
        val dialog = AlertDialog.Builder(this)
                .setTitle(R.string.rate_app_header)
                .setMessage(R.string.rate_app_body)
                .setNegativeButton(R.string.rate_app_dialog_no, null)
                .setPositiveButton(R.string.rate_app_dialog_ok) { _, _ ->
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.app_google_play_link) + packageName)))
                    } catch (e: ActivityNotFoundException) {
                        startActivity(Intent(Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.app_google_play_link) + packageName)))
                    }
                }.create()
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setBackgroundColor(Color.TRANSPARENT)
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundColor(Color.TRANSPARENT)
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setPadding(30, 0, 10, 0)
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.blue_700))
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.blue_700))
    }

    override fun showLanguageDialog() {
        Language.instance.showDialog(supportFragmentManager, Language.TAG)
    }

    override fun showAboutDialog() {
        AboutDialogActivity.instance.showDialog(supportFragmentManager, AboutDialogActivity.TAG)
    }

}
