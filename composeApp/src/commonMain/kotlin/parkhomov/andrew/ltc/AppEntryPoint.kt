package parkhomov.andrew.ltc

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import parkhomov.andrew.ltc.navigation.Route
import parkhomov.andrew.ltc.theme.AppTheme
import parkhomov.andrew.ltc.ui.main.MainScreen


@Composable
fun AppEntryPoint() {
    AppTheme {
        Scaffold(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) { _: PaddingValues ->
            NavigationRoot(modifier = Modifier)
        }
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
            when(key) {
                is Route.MainScreen -> {
                    NavEntry(key) {
                        MainScreen()
                    }
                }
                is Route.CompareScreen -> {
                    NavEntry(key) {

                    }
                }
                else -> error("Unknown NavKey: $key")
            }
        }
    )
}
