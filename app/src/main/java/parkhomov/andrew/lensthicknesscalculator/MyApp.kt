package parkhomov.andrew.lensthicknesscalculator

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import androidx.preference.PreferenceManager
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import parkhomov.andrew.lensthicknesscalculator.helper.PreferencesHelper
import parkhomov.andrew.lensthicknesscalculator.interactor.Interactor
import parkhomov.andrew.lensthicknesscalculator.utils.prefs.AppPreferencesHelper
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelCompareList
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelCompareListImpl
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelDiameter
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelDiameterImpl
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelLanguage
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelLanguageImpl
import parkhomov.andrew.lensthicknesscalculator.activity.viewmodel.ViewModelActivity
import parkhomov.andrew.lensthicknesscalculator.activity.viewmodel.ViewModelActivityImpl
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelResult
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelResultImpl
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelThickness
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelThicknessImpl
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelTransposition
import parkhomov.andrew.lensthicknesscalculator.viewmodel.ViewModelTranspositionImpl
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router


class MyApp : MultiDexApplication() {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    val appModule = module {

        single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
        single<PreferencesHelper> { AppPreferencesHelper(get()) }
        single { Interactor(get()) }
        single { cicerone.router }
        single { cicerone.navigatorHolder }

        viewModel<ViewModelActivity> { ViewModelActivityImpl(get()) }
        viewModel<ViewModelLanguage> { ViewModelLanguageImpl(get()) }
        viewModel<ViewModelThickness> { ViewModelThicknessImpl(get()) }
        viewModel<ViewModelResult> { ViewModelResultImpl(get()) }
        viewModel<ViewModelDiameter> { ViewModelDiameterImpl() }
        viewModel<ViewModelCompareList> { ViewModelCompareListImpl(get()) }
        viewModel<ViewModelTransposition> { ViewModelTranspositionImpl() }
    }

    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.DEBUG) {
            FirebaseAnalytics.getInstance(this)
        }
        startKoin {
            androidContext(this@MyApp)
            modules(appModule)
        }
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
