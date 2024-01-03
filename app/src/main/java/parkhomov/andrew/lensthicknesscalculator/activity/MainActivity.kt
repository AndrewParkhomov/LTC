package parkhomov.andrew.lensthicknesscalculator.activity

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import org.koin.android.ext.android.inject
import parkhomov.andrew.lensthicknesscalculator.BuildConfig
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
        val adsView = AdView(this).apply {
            layoutParams = CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.TOP
            }
        }
        binding.containerParent.addView(adsView)
        adsView.setAdSize(AdSize.BANNER)
        adsView.adUnitId = if (BuildConfig.DEBUG) {
            "ca-app-pub-3940256099942544/6300978111"
        } else {
            "ca-app-pub-1114161483134089/5582467309"
        }
        adsView.loadAd(AdRequest.Builder().build())

        binding.navView.background = null
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
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
