package parkhomov.andrew.lensthicknesscalculator.activity

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity.*
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.BuildConfig
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.extencions.MyContextWrapper
import parkhomov.andrew.lensthicknesscalculator.extencions.shortCollect
import parkhomov.andrew.lensthicknesscalculator.preferences.APP_LANGUAGE
import parkhomov.andrew.lensthicknesscalculator.preferences.AppPreferences
import parkhomov.andrew.lensthicknesscalculator.view.Settings
import java.util.*


class MainActivity : AppCompatActivity(R.layout.activity) {

    private val appPreferences: AppPreferences by inject()
    private val viewModel: MainActivityViewModel by viewModel()

    override fun attachBaseContext(context: Context) {
        var language = appPreferences.getStringValue(APP_LANGUAGE, "en")
        if (language.isEmpty()) {
            language = Locale.getDefault().isO3Language.substring(0, 2)
            appPreferences.putStringValue(APP_LANGUAGE, language)
        }
        super.attachBaseContext(MyContextWrapper.wrap(context, language))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setFlowListeners()
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

    private fun setFlowListeners() {
        viewModel.showMessage.filterNotNull().onEach(::showSnackbar).shortCollect(lifecycleScope)
        viewModel.shareResult.filterNotNull().onEach(::shareResult).shortCollect(lifecycleScope)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_language -> Settings.instance.show(supportFragmentManager)
            R.id.menu_item_rate -> showRateThisAppDialog()
            R.id.menu_item_share -> viewModel.onShareResultClicked()
            R.id.menu_item_about -> showAboutDialog()
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

    private fun showRateThisAppDialog() {
        AlertDialog.Builder(this)
                .setTitle(R.string.rate_app_header)
                .setMessage(R.string.rate_app_body)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok) { _, _ ->
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

    private fun shareResult(calculatedData: CalculatedData) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, "")
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_result_subject))
            putExtra(Intent.EXTRA_TEXT, createTextForSharing(calculatedData))
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share_with)))
    }

    private fun createTextForSharing(calculatedData: CalculatedData): String {
        val linkInPlayStore = "https://play.google.com/store/apps/details?id=$packageName"
        return if (calculatedData.cylinderPower == null) {
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
    }

    private fun showSnackbar(resId: Int) {
        Snackbar.make(container_parent, resId, Snackbar.LENGTH_LONG).show()
    }

    private fun showAboutDialog() {
        val version = StringBuilder(getString(R.string.version, BuildConfig.VERSION_NAME))
        version.append("\n\n")
        version.append(getString(R.string.translate_to_brazilian_portuguese))
        AlertDialog.Builder(this)
                .setMessage(version)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .create()
                .show()
    }

}
