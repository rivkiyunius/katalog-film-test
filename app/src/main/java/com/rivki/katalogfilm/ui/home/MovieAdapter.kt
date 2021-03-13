package com.rivki.katalogfilm.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rivki.katalogfilm.base.DiffCallback
import com.rivki.katalogfilm.databinding.ItemViewContentBinding
import com.rivki.katalogfilm.model.response.MovieResponse
import com.rivki.katalogfilm.ui.detail.DetailActivity
import com.rivki.katalogfilm.utils.CommonUtils.showImage
import com.rivki.katalogfilm.utils.CommonUtils.substringText

class MovieAdapter(private val diffCallback: DiffCallback = DiffCallback()) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val listMovie = mutableListOf<MovieResponse>()

    fun setMovie(movies: List<MovieResponse>) {
        calculateDIff(movies)
    }

    private fun calculateDIff(newData: List<MovieResponse>){
        diffCallback.setList(listMovie, newData)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(listMovie){
            clear()
            addAll(newData)
        }
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val itemViewContentBinding =
            ItemViewContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemViewContentBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovie.size

    inner class MovieViewHolder(private val binding: ItemViewContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieResponse) {
            with(binding) {
                imageItem.showImage(movie.posterPath.toString())
                tvTitleItem.text = movie.title
                tvDateItem.text = movie.releaseDate
                tvDescItem.text = movie.overview?.substringText()
                rbItemView.rating = movie.voteAverage?.div(2)!!.toFloat()
                tvVoteDetail.text = StringBuilder("${movie.voteCount} votes")
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_ID, movie.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}