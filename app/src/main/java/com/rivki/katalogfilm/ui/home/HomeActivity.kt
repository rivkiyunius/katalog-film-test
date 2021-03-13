package com.rivki.katalogfilm.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivki.katalogfilm.R
import com.rivki.katalogfilm.base.BaseActivity
import com.rivki.katalogfilm.base.DiffCallback
import com.rivki.katalogfilm.databinding.ActivityHomeBinding
import com.rivki.katalogfilm.ui.favorite.FavoriteActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity(), BottomSheetbarFragment.BottomSheetListener {
    private val homeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val diffCallback by inject<DiffCallback>()
    private val movieAdapter by lazy { MovieAdapter(diffCallback) }
    private val movieViewModel by viewModel<MovieViewModel>()

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_home)
        setContentView(homeBinding.root)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        homeBinding.let {
            it.rvMovie.layoutManager = LinearLayoutManager(this@HomeActivity)
            it.rvMovie.adapter = movieAdapter
            it.btnCategory.setOnClickListener {
                val modalBottomDialog = BottomSheetbarFragment(this)
                modalBottomDialog.show(supportFragmentManager, modalBottomDialog.tag)
            }
            it.toolbar.btnFavorite.setOnClickListener {
                val intent = Intent(this@HomeActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun observeData() {
        movieViewModel.let { vm ->
            vm.getMovieList()
            vm.nowPlayingMovie.observe(this) { movieAdapter.setMovie(it) }
            vm.observeLoading().observe(this) { showLoading(it) }
            vm.observeError().observe(this){ showError(it) }
        }
    }

    fun showLoading(isLoading: Boolean){
        with(homeBinding){
            if (isLoading){
                loadingMovie.visibility = View.VISIBLE
                rvMovie.visibility = View.GONE
            } else {
                loadingMovie.visibility = View.GONE
                rvMovie.visibility = View.VISIBLE
            }
        }
    }

    override fun changeCategory(category: String) {
        when(category){
            BottomSheetbarFragment.POPULAR -> {
                movieViewModel.let { vm ->
                    vm.getPopularMovie()
                    vm.popularMovie.observe(this) { movieAdapter.setMovie(it) }
                    vm.observeLoading().observe(this) { showLoading(it) }
                    vm.observeError().observe(this){ showError(it) }
                }
            }
            BottomSheetbarFragment.NOW_PLAYING -> {
                movieViewModel.let { vm ->
                    vm.getMovieList()
                    vm.nowPlayingMovie.observe(this) { movieAdapter.setMovie(it) }
                    vm.observeLoading().observe(this) { showLoading(it) }
                    vm.observeError().observe(this){ showError(it) }
                }
            }
            BottomSheetbarFragment.TOP_RATED -> {
                movieViewModel.let { vm ->
                    vm.getTopRatedMovie()
                    vm.topRatedMovie.observe(this) { movieAdapter.setMovie(it) }
                    vm.observeLoading().observe(this) { showLoading(it) }
                    vm.observeError().observe(this){ showError(it) }
                }
            }
        }
    }

    private fun showError(error: String){
        if(!error.isEmpty()){
            with(homeBinding){
                rvMovie.visibility = View.GONE
                tvError.visibility = View.VISIBLE
            }
        }
    }
}