package com.rivki.katalogfilm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rivki.katalogfilm.databinding.FragmentBottomSheetBinding

class BottomSheetbarFragment(private val bottomSheetListener: BottomSheetListener): BottomSheetDialogFragment() {
    companion object{
        val NOW_PLAYING = "now_playing"
        val POPULAR = "popular"
        val TOP_RATED = "top_rated"
    }
    private lateinit var bottomSheetBinding: FragmentBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bottomSheetBinding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return bottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(bottomSheetBinding){
            tvNowPlaying.setOnClickListener {
                bottomSheetListener.changeCategory(NOW_PLAYING)
                dismiss()
            }
            tvPopularMovie.setOnClickListener {
                bottomSheetListener.changeCategory(POPULAR)
                dismiss()
            }
            tvTopRated.setOnClickListener {
                bottomSheetListener.changeCategory(TOP_RATED)
                dismiss()
            }
        }
    }

    interface BottomSheetListener{
        fun changeCategory(category: String)
    }
}