package com.rivki.katalogfilm.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.rivki.katalogfilm.R
import com.rivki.katalogfilm.base.BaseActivity
import com.rivki.katalogfilm.base.DiffCallback
import com.rivki.katalogfilm.databinding.ActivityDetailBinding
import com.rivki.katalogfilm.model.response.MovieResponse
import com.rivki.katalogfilm.utils.CommonUtils.showImage
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_view_content.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private val detailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<DetailViewModel>()
    private val diffCallback by inject<DiffCallback>()
    private val reviewAdapter by lazy { ReviewAdapter(diffCallback) }
    private lateinit var movie: MovieResponse
    private var id: Int = 0
    private lateinit var title: String
    private var favoriteStatus = false

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_detail)
        setContentView(detailBinding.root)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        val extras = intent.extras
        id = extras!!.getInt(EXTRA_ID)
        var isShow = true
        var scrollRange = -1
        with(detailBinding) {
            setSupportActionBar(toolbarDetail)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowHomeEnabled(true)
            }
            ctDetail.title = " "
            appbarDetail.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (scrollRange == -1) scrollRange = appBarLayout?.totalScrollRange!!
                if (scrollRange + verticalOffset == 0) {
                    ctDetail.title = title
                    ctDetail.setCollapsedTitleTextColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.white
                        )
                    )
                    isShow = true
                } else if (isShow) {
                    ctDetail.title = " "
                    isShow = false
                }
            })
            rvReviews.layoutManager = LinearLayoutManager(this@DetailActivity)
            rvReviews.adapter = reviewAdapter
        }
    }

    override fun observeData() {
        viewModel.let { vm ->
            vm.getMovie(id)
            vm.getReviewList(id)
            vm.getFavoriteStatus(id)
            vm.detailMovie.observe(this) {
                movie = it
                title = it?.title.toString()
                with(detailBinding) {
                    it.backdropPath?.let { image -> imgDetail.showImage(image) }
                    tvTitleDetail.text = it.title
                    tvDateDetail.text = it.releaseDate
                    tvDescDetail.text = it.overview
                    tvVoteDetail.text = StringBuilder("${it.voteCount} votes")
                    rbDetail.rating = it.voteAverage?.div(2)!!.toFloat()
                }
            }
            vm.favoriteStatus.observe(this) {
                favoriteStatus = it
                if (it) detailBinding.imageDetailFavorite.setBackgroundResource(R.drawable.ic_favorite)
            }
            vm.listReview.observe(this) { reviewAdapter.setReview(it) }
            detailBinding.let { bind ->
                bind.imageDetailFavorite.setOnClickListener {
                    if (favoriteStatus) {
                        vm.deleteFavorite(movie)
                        bind.imageDetailFavorite.setBackgroundResource(R.drawable.ic_favorite_border)
                    } else {
                        movie.isFavorite = true
                        vm.addFavorite(movie)
                        bind.imageDetailFavorite.setBackgroundResource(R.drawable.ic_favorite)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}