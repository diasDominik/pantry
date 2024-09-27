package de.dias.dominik.model.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDataBaseBuilder(context: Context): RoomDatabase.Builder<PantryDatabase> {
    val applicationContext = context.applicationContext
    val dbFile = applicationContext.getDatabasePath("pantry.db")
    return Room.databaseBuilder(
        context = applicationContext,
        name = dbFile.absolutePath,
    )
}
