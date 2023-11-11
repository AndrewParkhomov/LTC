package parkhomov.andrew.lensthicknesscalculator.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.databinding.ActivityBinding
import parkhomov.andrew.lensthicknesscalculator.extencions.MyContextWrapper
import parkhomov.andrew.lensthicknesscalculator.extencions.openWebsite
import parkhomov.andrew.lensthicknesscalculator.preferences.APP_LANGUAGE
import parkhomov.andrew.lensthicknesscalculator.preferences.AppPreferences
import parkhomov.andrew.lensthicknesscalculator.view.Settings
import java.util.Locale


class MainActivity : AppCompatActivity(R.layout.activity) {

    private val appPreferences: AppPreferences by inject()
    private val binding by viewBinding(ActivityBinding::bind)

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
    }

    private fun initViews() {
        binding.navView.background = null
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_thickness,
                R.id.navigation_compare,
                R.id.navigation_diameter,
                R.id.navigation_transposition
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setOnItemSelectedListener { menuItem ->
            NavigationUI.onNavDestinationSelected(
                menuItem,
                navController
            )
        }
        binding.supportUkraineACTV.setOnClickListener {
            it.context.openWebsite("https://savelife.in.ua/en/")
        }
        binding.settingsIV.setOnClickListener {
            Settings.instance.show(supportFragmentManager)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
