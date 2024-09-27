package de.dias.dominik.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import de.dias.dominik.model.database.data.PantryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PantryItemDao {
    @Upsert
    suspend fun insert(pantryItem: PantryItem)

    @Delete
    suspend fun delete(pantryItem: PantryItem)

    @Query("SELECT * FROM pantryitem")
    fun getAll(): Flow<List<PantryItem>>

    @Query("SELECT * FROM pantryitem WHERE id = :id")
    suspend fun getById(id: Int): PantryItem
}
