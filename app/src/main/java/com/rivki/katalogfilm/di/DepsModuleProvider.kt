package com.rivki.katalogfilm.di

import com.rivki.katalogfilm.modules.data.DataModules
import com.rivki.katalogfilm.modules.network.NetworkModules
import com.rivki.katalogfilm.modules.room.RoomModules
import com.rivki.katalogfilm.modules.viewModel.ViewModelModules
import org.koin.core.module.Module

object DepsModuleProvider: BaseModule {
    override val modules: List<Module>
        get(){
            return ArrayList<Module>().apply {
                addAll(DataModules.modules)
                addAll(NetworkModules.modules)
                addAll(ViewModelModules.modules)
                addAll(RoomModules.modules)
            }
        }
}