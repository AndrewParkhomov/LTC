package parkhomov.andrew.lensthicknesscalculator.activity

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.extencions.MyContextWrapper
import parkhomov.andrew.lensthicknesscalculator.preferences.APP_LANGUAGE
import parkhomov.andrew.lensthicknesscalculator.preferences.AppPreferences
import parkhomov.andrew.lensthicknesscalculator.view.About
import parkhomov.andrew.lensthicknesscalculator.view.Settings
import java.util.*


class MainActivity : AppCompatActivity(R.layout.activity) {

    private val appPreferences: AppPreferences by inject()
    private val viewModel: MainActivityViewModel by viewModel()

    override fun attachBaseContext(context: Context) {
        var language = appPreferences.getStringValue(APP_LANGUAGE, "")
        if (language.isEmpty()) {
            language = Locale.getDefault().isO3Language.substring(0, 2)
            appPreferences.putStringValue(APP_LANGUAGE, language)
        }
        super.attachBaseContext(MyContextWrapper.wrap(context, language))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        updateFabIcon(R.drawable.calculate)
    }

    private fun updateFabIcon(imageId: Int?) {
        if (imageId == null) {
            fab.hide()
        } else {
            fab.show()
            fab.setImageResource(imageId)
        }
        fab.setOnClickListener {
            when (imageId) {
                R.drawable.calculate -> viewModel.onCalculateClicked()
                R.drawable.ic_outline_delete_24 -> viewModel.onClearClicked()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_settings -> Settings.instance.show(supportFragmentManager)
            R.id.menu_item_about -> About.instance.show(supportFragmentManager)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews() {
        nav_view.background = null
        nav_view.menu.getItem(4).isEnabled = false

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_thickness,
                R.id.navigation_compare,
                R.id.navigation_diameter,
                R.id.navigation_transposition
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setOnItemSelectedListener { menuItem ->
            val imageId = when (menuItem.itemId) {
                R.id.navigation_thickness -> R.drawable.calculate
                R.id.navigation_compare -> R.drawable.ic_outline_delete_24
                else -> null
            }
            updateFabIcon(imageId)
            NavigationUI.onNavDestinationSelected(
                menuItem,
                navController
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
