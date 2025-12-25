@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package game.dice.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class DataStoreProvider : KoinComponent {
    private val context: Context by inject()

    actual fun get(): DataStore<Preferences> {
        return createDataStore(
            producePath = { context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath },
        )
    }
}
