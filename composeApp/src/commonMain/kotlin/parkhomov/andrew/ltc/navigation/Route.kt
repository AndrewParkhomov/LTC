package parkhomov.andrew.ltc.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {

    @Serializable
    data object MainScreen : Route, NavKey

    @Serializable
    data object CompareScreen : Route, NavKey
}
