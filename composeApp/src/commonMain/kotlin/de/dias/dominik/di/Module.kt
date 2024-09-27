package de.dias.dominik.di

import de.dias.dominik.model.database.PantryDatabase
import de.dias.dominik.model.database.dao.PantryItemDao
import de.dias.dominik.model.database.repository.PantryRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule =
    module {
        singleOf(::PantryRepository)
        single {
            PantryDatabase.getPantryDatabase(get()).getPantryDao()
        }.bind<PantryItemDao>()
    }
