package com.examen.carlosgs.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.examen.carlosgs.R
import com.examen.carlosgs.databinding.ItemPopularMovieBinding
import com.examen.carlosgs.data.model.MovieModel

class PopularMovieAdapter(private val listener : PopularMovieAdapterListener, private var listPopularMovieModels: List<MovieModel> = emptyList()) : RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_popular_movie, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPopularMovieModels[position], context)
        holder.binding.cvMovie.setOnClickListener { listener.onClickMovie(listPopularMovieModels[position]) }
    }

    override fun getItemCount(): Int {
        return listPopularMovieModels.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = ItemPopularMovieBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(popularMovieModel: MovieModel, context : Context) {
            Glide.with(context).load(popularMovieModel.getUrl()).thumbnail(0.5f).into(binding.ivPoster)
            binding.tvTitle.text = if(popularMovieModel.original_title.isNullOrEmpty()) context.getString(R.string.txt_no_title) else popularMovieModel.original_title
            binding.tvAverage.text = " ${popularMovieModel.vote_average}/10 "
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listPopularMovieModels: List<MovieModel> ){
        this.listPopularMovieModels = listPopularMovieModels
        notifyDataSetChanged()
    }

    interface PopularMovieAdapterListener {
        fun onClickMovie(movie : MovieModel)
    }

}