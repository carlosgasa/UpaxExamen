package com.examen.carlosgs.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.examen.carlosgs.adapter.PopularMovieAdapter
import com.examen.carlosgs.databinding.FragmentMoviesBinding
import com.examen.carlosgs.data.model.MovieModel
import com.examen.carlosgs.ui.movies.detail.MovieDetailDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesFragment : Fragment(), PopularMovieAdapter.PopularMovieAdapterListener {

    private val moviesViewModel: MoviesViewModel by viewModels()
    private lateinit var mAdapter: PopularMovieAdapter

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initComponents()
        initObservers()

        CoroutineScope(Dispatchers.IO).launch {
            moviesViewModel.loadPopularMovies()
        }

        return root
    }

    private fun initComponents() {
        //Configurar recycler
        mAdapter = PopularMovieAdapter(this)
        binding.rvMovies.setHasFixedSize(true)
        binding.rvMovies.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.rvMovies.adapter = mAdapter
    }

    private fun initObservers() {
        moviesViewModel.moviesListData.observe(requireActivity()) {
            binding.cpLoading.isVisible = false

            //Validaciones para datos vacios
            if (it.isNullOrEmpty()) {
                binding.tvEmptyData.visibility = View.VISIBLE
                binding.rvMovies.visibility = View.GONE
                mAdapter.setList(emptyList())
            } else {
                binding.tvEmptyData.visibility = View.GONE
                binding.rvMovies.visibility = View.VISIBLE
                mAdapter.setList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickMovie(movie: MovieModel) {


        val bottomSheetDialogFragment = MovieDetailDialogFragment.newInstance(movie)
        bottomSheetDialogFragment.show(requireActivity().supportFragmentManager, "BOTTOM_DETAIL_FRAGMENT")
    }
}