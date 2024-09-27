package de.dias.dominik.model.database.repository

import de.dias.dominik.model.database.dao.PantryItemDao
import de.dias.dominik.model.database.data.PantryItem
import kotlinx.coroutines.flow.Flow

class PantryRepository(private val pantryDao: PantryItemDao) : PantryItemDao {
    override suspend fun insert(pantryItem: PantryItem) {
        pantryDao.insert(pantryItem)
    }

    override suspend fun delete(pantryItem: PantryItem) {
        pantryDao.delete(pantryItem)
    }

    override fun getAll(): Flow<List<PantryItem>> {
        return pantryDao.getAll()
    }

    override suspend fun getById(id: Int): PantryItem {
        return pantryDao.getById(id)
    }
}
