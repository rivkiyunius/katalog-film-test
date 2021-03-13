package com.rivki.katalogfilm.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivki.katalogfilm.R
import com.rivki.katalogfilm.base.BaseActivity
import com.rivki.katalogfilm.base.DiffCallback
import com.rivki.katalogfilm.databinding.ActivityFavoriteBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : BaseActivity() {
    private val favoriteBinding by lazy { ActivityFavoriteBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<FavoriteViewModel>()
    private val diffCallback by inject<DiffCallback>()
    private val favoriteAdapter by lazy { FavoriteAdapter(diffCallback) }

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_favorite)
        setContentView(favoriteBinding.root)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        favoriteBinding.let {
            it.rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            it.rvFavorite.adapter = favoriteAdapter
        }
    }

    override fun observeData() {
        viewModel.let { vm ->
            vm.getFavoriteList()
            vm.favoriteList.observe(this) { favoriteAdapter.setMovie(it) }
            vm.observeLoading().observe(this) { showLoading(it) }
        }
    }

    fun showLoading(isLoading: Boolean) {
        with(favoriteBinding) {
            if (isLoading) {
                loadingFavorite.visibility = View.VISIBLE
                rvFavorite.visibility = View.GONE
            } else {
                loadingFavorite.visibility = View.GONE
                rvFavorite.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        viewModel.getFavoriteList()
        super.onResume()
    }
}