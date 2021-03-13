package com.rivki.katalogfilm.modules.room

import androidx.room.Room
import com.rivki.katalogfilm.di.BaseModule
import com.rivki.katalogfilm.repository.localdatasource.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

object RoomModules: BaseModule {
    override val modules: List<Module>
        get() = listOf(roomModule)

    private val roomModule = module {
        single {
            Room.databaseBuilder(get(), AppDatabase::class.java, "kitabisa-test")
                .build()
        }
        single { get<AppDatabase>().movieDao() }
    }
}