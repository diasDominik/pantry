package de.dias.dominik.model.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import de.dias.dominik.model.database.dao.PantryItemDao
import de.dias.dominik.model.database.data.PantryItem
import kotlinx.coroutines.Dispatchers

@Database(entities = [PantryItem::class], version = 1)
@ConstructedBy(PantryDatabaseConstructor::class)
abstract class PantryDatabase : RoomDatabase() {
    abstract fun getPantryDao(): PantryItemDao

    companion object {
        fun getPantryDatabase(builder: Builder<PantryDatabase>): PantryDatabase {
            return builder
                .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
        }
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object PantryDatabaseConstructor : RoomDatabaseConstructor<PantryDatabase>
