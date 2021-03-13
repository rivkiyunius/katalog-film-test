package com.rivki.katalogfilm.modules.data

import com.rivki.katalogfilm.di.BaseModule
import com.rivki.katalogfilm.repository.DataRepository
import com.rivki.katalogfilm.repository.localdatasource.LocalDataSource
import com.rivki.katalogfilm.repository.remotedatasource.RemoteDataSources
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModules: BaseModule {
    override val modules: List<Module>
        get() = listOf(dataModule)

    private val dataModule = module {
        single { RemoteDataSources(get()) }
        single { LocalDataSource(get())}
        single { DataRepository(get(), get()) }
    }
}