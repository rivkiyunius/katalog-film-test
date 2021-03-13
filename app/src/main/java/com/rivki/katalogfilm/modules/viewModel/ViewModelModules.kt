package com.rivki.katalogfilm.modules.viewModel

import com.rivki.katalogfilm.base.DiffCallback
import com.rivki.katalogfilm.di.BaseModule
import com.rivki.katalogfilm.ui.detail.DetailViewModel
import com.rivki.katalogfilm.ui.favorite.FavoriteViewModel
import com.rivki.katalogfilm.ui.home.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object ViewModelModules : BaseModule {
    override val modules: List<Module>
        get() = listOf(viewModels)

    private val viewModels = module {
        viewModel { MovieViewModel(get()) }
        viewModel { DetailViewModel(get()) }
        viewModel { FavoriteViewModel(get()) }
        factory { DiffCallback() }
    }
}