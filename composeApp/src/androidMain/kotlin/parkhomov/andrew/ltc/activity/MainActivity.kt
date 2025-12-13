package parkhomov.andrew.ltc.activity

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.PopupMenu
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.databinding.ActivityBinding
import parkhomov.andrew.ltc.extencions.MyContextWrapper
import parkhomov.andrew.ltc.preferences.APP_LANGUAGE
import parkhomov.andrew.ltc.preferences.APP_THEME
import parkhomov.andrew.ltc.preferences.AppPreferences
import parkhomov.andrew.ltc.view.Settings
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
        addInsetPaddings()
        initViews()
    }

    private fun addInsetPaddings() {
        val rootView: CoordinatorLayout = findViewById(R.id.container_parent)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, windowInsets ->
            val insets: Insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                topMargin = insets.top
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }
            WindowInsetsCompat.CONSUMED
        }
        val isIconsLight: Boolean = isIconsLight()
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = isIconsLight
            isAppearanceLightNavigationBars = isIconsLight
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun isIconsLight(): Boolean {
        val theme: Int = appPreferences.getIntValue(
            key = APP_THEME,
            default = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )
        return theme != AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM &&
                theme != AppCompatDelegate.MODE_NIGHT_YES
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
        binding.settingsIV.setOnClickListener(::showPopupMenu)
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
}
