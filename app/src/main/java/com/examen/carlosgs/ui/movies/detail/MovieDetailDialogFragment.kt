package com.examen.carlosgs.ui.movies.detail

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.examen.carlosgs.R
import com.examen.carlosgs.databinding.FragmentMovieDetailDialogDialogBinding
import com.examen.carlosgs.data.model.MovieModel

const val ARG_ITEM_MOVIE = "item_movie"

class MovieDetailDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMovieDetailDialogDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailDialogDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieModel = arguments?.getSerializable(ARG_ITEM_MOVIE) as MovieModel

        Glide.with(this).load(movieModel.getUrlBanner()).into(binding.ivBanner)
        binding.tvTitle.text = if(movieModel.original_title.isNullOrEmpty()) getString(R.string.txt_no_title) else movieModel.original_title
        binding.tvOverview.text = if(movieModel.overview.isNullOrEmpty()) getString(R.string.txt_no_overview) else movieModel.overview
    }


    companion object {
        fun newInstance(movie: MovieModel): MovieDetailDialogFragment =
            MovieDetailDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ITEM_MOVIE, movie)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}