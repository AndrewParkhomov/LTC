package parkhomov.andrew.ltc.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object AndroidDatabaseBuilder : KoinComponent {
    private val context: Context by inject()

    fun getBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        return Room.databaseBuilder<AppDatabase>(
            context = context.applicationContext,
            name = dbFile.absolutePath,
        )
    }
}

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> = AndroidDatabaseBuilder.getBuilder()
