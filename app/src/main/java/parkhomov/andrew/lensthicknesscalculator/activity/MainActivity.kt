package parkhomov.andrew.lensthicknesscalculator.activity

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.databinding.ActivityBinding
import parkhomov.andrew.lensthicknesscalculator.extencions.MyContextWrapper
import parkhomov.andrew.lensthicknesscalculator.preferences.APP_LANGUAGE
import parkhomov.andrew.lensthicknesscalculator.preferences.AppPreferences
import parkhomov.andrew.lensthicknesscalculator.preferences.SHOW_NEW_APP
import parkhomov.andrew.lensthicknesscalculator.view.NewApp
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
        binding.navView.setOnItemSelectedListener { menuItem: MenuItem ->
            NavigationUI.onNavDestinationSelected(
                menuItem,
                navController
            )
        }
        binding.menuIV.setOnClickListener(::showPopupMenu)
        binding.newAppB.setOnClickListener {
            NewApp.instance.show(supportFragmentManager)
        }
        if (appPreferences.getBooleanValue(SHOW_NEW_APP, true)) {
            lifecycleScope.launchWhenStarted {
                delay(2000)
                NewApp.instance.show(supportFragmentManager)
                appPreferences.putBooleanValue(SHOW_NEW_APP, false)
            }
        }
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(this, v)
        popupMenu.inflate(R.menu.menu)
        popupMenu.setForceShowIcon(true)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_settings -> {
                    Settings.instance.show(supportFragmentManager)
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
