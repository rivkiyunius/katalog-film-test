package com.rivki.katalogfilm.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rivki.katalogfilm.base.DiffCallback
import com.rivki.katalogfilm.databinding.ItemViewReviewBinding
import com.rivki.katalogfilm.model.response.ReviewsResponse
import com.rivki.katalogfilm.utils.CommonUtils.showImage
import com.rivki.katalogfilm.utils.CommonUtils.showImageReview

class ReviewAdapter(private val diffCallback: DiffCallback = DiffCallback()) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    private val listReview = mutableListOf<ReviewsResponse>()

    fun setReview(review: List<ReviewsResponse>) {
        calculateDiff(review)
    }

    private fun calculateDiff(newData: List<ReviewsResponse>) {
        diffCallback.setList(listReview, newData)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(listReview) {
            clear()
            addAll(newData)
        }
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val itemViewReviewBinding =
            ItemViewReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(itemViewReviewBinding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bindItem(listReview[position])
    }

    override fun getItemCount(): Int = listReview.size

    class ReviewViewHolder(private val binding: ItemViewReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(review: ReviewsResponse) {
            with(binding) {
                review.authorDetails?.avatarPath?.let { imgAuthor.showImageReview(it) }
                tvAuthorReview.text = review.author
                tvItemReview.text = review.content
            }
        }
    }
}