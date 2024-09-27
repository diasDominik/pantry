package de.dias.dominik.di

import androidx.room.RoomDatabase
import de.dias.dominik.MainActivityViewModel
import de.dias.dominik.model.database.PantryDatabase
import de.dias.dominik.model.database.getDataBaseBuilder
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule =
    module {
        singleOf(::getDataBaseBuilder).bind<RoomDatabase.Builder<PantryDatabase>>()
        viewModelOf(::MainActivityViewModel)
    }
