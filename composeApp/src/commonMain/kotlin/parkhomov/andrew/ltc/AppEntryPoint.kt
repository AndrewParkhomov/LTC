package parkhomov.andrew.ltc

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import cafe.adriel.lyricist.LocalStrings
import cafe.adriel.lyricist.rememberStrings
import game.dice.storage.repository.SettingsProvider
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.koinInject
import parkhomov.andrew.ltc.compositionlocal.LocalDependencies
import parkhomov.andrew.ltc.compositionlocal.LocalToast
import parkhomov.andrew.ltc.compositionlocal.LocalTopScreenToast
import parkhomov.andrew.ltc.compositionlocal.getDependencies
import parkhomov.andrew.ltc.navigation.Route
import parkhomov.andrew.ltc.provider.ShowToast
import parkhomov.andrew.ltc.strings.Locales
import parkhomov.andrew.ltc.strings.Strings
import parkhomov.andrew.ltc.theme.AppTheme
import parkhomov.andrew.ltc.theme.LocalThemeMode
import parkhomov.andrew.ltc.theme.ThemeMode
import parkhomov.andrew.ltc.theme.toAppTheme
import parkhomov.andrew.ltc.toast.AppToast
import parkhomov.andrew.ltc.toast.ToastMessage
import parkhomov.andrew.ltc.toast.ToastProvider
import parkhomov.andrew.ltc.toast.ToastState
import parkhomov.andrew.ltc.ui.compare.CompareLensScreen
import parkhomov.andrew.ltc.ui.main.MainScreen


@Composable
fun AppEntryPoint(
    toastProvider: ToastProvider = koinInject(),
    settingsProvider: SettingsProvider = koinInject()
) {
    val toastState: MutableState<ToastState> = remember { mutableStateOf(ToastState.Init) }
    val dependencies: LocalDependencies = remember { getDependencies(toastState) }
    val toastHelper = remember { ShowToast() }

    val currentLanguage: String by settingsProvider.getLanguageFlow()
        .collectAsState(initial = Locales.EN)
    val themeId by settingsProvider.getThemeFlow()
        .collectAsState(initial = ThemeMode.SYSTEM.id)

    val strings: Strings = rememberStrings(currentLanguageTag = currentLanguage).strings

    LaunchedEffect(Unit) {
        toastProvider.toast.collect { message: String ->
            toastHelper.showNativeToast(message)
        }
    }

    LaunchedEffect(strings) {
        toastProvider.showTopToast.collect { message: ToastMessage ->
            toastState.value = ToastState.Shown(message.resolve(strings))
        }
    }

    val themeMode: ThemeMode = themeId.toAppTheme()

    Box(modifier = Modifier.fillMaxSize()) {
        CompositionLocalProvider(
            LocalTopScreenToast provides dependencies.notification,
            LocalToast provides dependencies.toast,
            LocalThemeMode provides themeMode,
            LocalStrings provides strings
        ) {
            AppTheme(themeMode = themeMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { _: PaddingValues ->
                    NavigationRoot(modifier = Modifier)
                }
            }
        }
        AppToast(toastState)
    }
}

@Composable
private fun NavigationRoot(
    modifier: Modifier = Modifier,
) {
    val backStack: NavBackStack<NavKey> = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.MainScreen::class, Route.MainScreen.serializer())
                    subclass(Route.CompareScreen::class, Route.CompareScreen.serializer())
                }
            }
        },
        Route.MainScreen
    )

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        transitionSpec = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300, easing = EaseInOut)
            ) togetherWith slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300, easing = EaseInOut)
            )
        },
        popTransitionSpec = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300, easing = EaseInOut)
            ) togetherWith slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300, easing = EaseInOut)
            )
        },
        predictivePopTransitionSpec = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300, easing = EaseInOut)
            ) togetherWith slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300, easing = EaseInOut)
            )
        },
        entryProvider = { key: NavKey ->
            when (key) {
                is Route.MainScreen -> {
                    NavEntry(key) {
                        MainScreen(onCompareClick = { backStack.add(Route.CompareScreen) })
                    }
                }

                is Route.CompareScreen -> {
                    NavEntry(key) {
                        CompareLensScreen(closeScreen = { backStack.remove(it) })
                    }
                }

                else -> error("Unknown NavKey: $key")
            }
        }
    )
}
