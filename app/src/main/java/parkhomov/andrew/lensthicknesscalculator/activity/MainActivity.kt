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
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import parkhomov.andrew.lensthicknesscalculator.BuildConfig
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.CalculatedData
import parkhomov.andrew.lensthicknesscalculator.data.GlossaryItem
import parkhomov.andrew.lensthicknesscalculator.preferences.AppPreferences
import parkhomov.andrew.lensthicknesscalculator.extencions.MyContextWrapper
import parkhomov.andrew.lensthicknesscalculator.preferences.APP_LANGUAGE
import parkhomov.andrew.lensthicknesscalculator.extencions.shortCollect
import parkhomov.andrew.lensthicknesscalculator.view.Glossary
import parkhomov.andrew.lensthicknesscalculator.view.Settings
import java.util.*


class MainActivity : AppCompatActivity(R.layout.activity) {

    private val appPreferences: AppPreferences by inject()
    private val viewModel: MainActivityViewModel by viewModel()

    private val glossary: List<GlossaryItem> by lazy(LazyThreadSafetyMode.NONE){
        val titles = resources.getStringArray(R.array.titles)
        val descriptions = resources.getStringArray(R.array.descriptions)
        titles.zip(descriptions).mapIndexed { index, pair ->
            GlossaryItem(
                title = pair.first,
                description = pair.second,
                imageId = getImageId(index)
            )
        }
    }

    private fun getImageId(position: Int): Int {
        return when (position) {
            1 -> R.drawable.sphere_img
            2 -> R.drawable.cylinder_img
            3 -> R.drawable.axis_img
            4 -> R.drawable.front_curve_img
            5 -> R.drawable.thickness_gauge_img
            6 -> R.drawable.edge_thickness_img
            7 -> R.drawable.diam_img
            8 -> R.drawable.ed_img
            9 -> R.drawable.dbl_img
            10 -> R.drawable.pd_img
            11 -> R.drawable.transposition_img
            else -> R.drawable.index_of_refraction_img
        }
    }

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
        supportActionBar?.title = getString(R.string.app_name)

        initViews()
        setFlowListeners()
        fab.setOnClickListener {
            viewModel.onFabClicked()
        }
    }

    private fun updateFabIcon(imageId: Int){
        if(imageId == -1){
            fab.hide()
        }else{
            fab.show()
            fab.setImageResource(imageId)
        }
    }

    private fun setFlowListeners() {
        viewModel.imageFab.onEach(::updateFabIcon).shortCollect(lifecycleScope)
        viewModel.showGlossary.onEach(::showGlossaryModal).shortCollect(lifecycleScope)
        viewModel.showMessage.filterNotNull().onEach(::showSnackbar).shortCollect(lifecycleScope)
        viewModel.shareResult.filterNotNull().onEach(::shareResult).shortCollect(lifecycleScope)
        viewModel.shareResult.filterNotNull().onEach(::shareResult).shortCollect(lifecycleScope)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        return true
    }

    private fun showGlossaryModal(imageId: Int){
        val targetGlossaryItem = glossary.first { it.imageId == imageId }
        Glossary.getInstance(targetGlossaryItem).show(supportFragmentManager)
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
        nav_view.setupWithNavController(navController)
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
