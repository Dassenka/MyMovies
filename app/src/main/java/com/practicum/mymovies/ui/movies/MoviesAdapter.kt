package com.practicum.mymovies.ui.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.mymovies.domain.models.Movie
import com.practicum.mymovies.domain.models.MovieDetails

class MoviesAdapter(private val clickListener: MovieClickListener) : RecyclerView.Adapter<MovieViewHolder>() {

    var movies = ArrayList<Movie>()
    //var movieDetails = ArrayList<MovieDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(parent, clickListener) // добавляем clickListener после перноса обработчика нажатия во вью холдер

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies.get(position))

        // удаляем строку после перноса обработчика нажатия во вью холдер
        //holder.itemView.setOnClickListener { clickListener.onMovieClick(movies.get(position)) }
    }

    override fun getItemCount(): Int = movies.size

    interface MovieClickListener {
        fun onMovieClick(movie: Movie)

        //метод для обработки нажатия "новую фичу — добавлять отдельный фильм в избранное"
        //fun onFavoriteToggleClick(movie: Movie)
    }
}