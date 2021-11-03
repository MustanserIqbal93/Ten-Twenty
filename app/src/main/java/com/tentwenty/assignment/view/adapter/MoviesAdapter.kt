package com.tentwenty.assignment.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tentwenty.assignment.R
import com.tentwenty.assignment.databinding.MovieListItemBinding
import com.tentwenty.assignment.model.Movie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesAdapter  @Inject constructor()
    : RecyclerView.Adapter<MoviesAdapter.ProductViewHolder>() {

    private var mMoviesList: ArrayList<Movie> = ArrayList()
    private lateinit var mListener: MovieItemClickListener

    fun setOnClickListener(mListener: MovieItemClickListener) {
        this.mListener = mListener
    }

    fun setData(data: ArrayList<Movie>){
        this.mMoviesList = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder = ProductViewHolder.from(parent)

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(mMoviesList[position], mListener)

    override fun getItemCount(): Int = mMoviesList.size

    class ProductViewHolder(val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(movie: Movie, listener : MovieItemClickListener){
            binding.movie = movie
            binding.movieItemClick = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: MovieListItemBinding = DataBindingUtil
                    .inflate(layoutInflater, R.layout.movie_list_item,
                        parent, false)
                return ProductViewHolder(binding)
            }
        }
    }

    interface MovieItemClickListener {
        fun onBookingClicked(movie: Movie)
        fun onMovieItemClicked(movie: Movie)
    }
}