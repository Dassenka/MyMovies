package com.practicum.mymovies.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practicum.mymovies.R
import com.practicum.mymovies.domain.models.Movie
import com.practicum.mymovies.domain.models.MovieDetails

class MovieViewHolder(
    parent: ViewGroup,
    // переносим обработчик нажатия из MoviesAdapter из onBindViewHolder  1
    private val clickListener: MoviesAdapter.MovieClickListener,
) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
    ) {

    var cover: ImageView = itemView.findViewById(R.id.cover)
    var title: TextView = itemView.findViewById(R.id.title)
    var description: TextView = itemView.findViewById(R.id.description)
    lateinit var movieDetails: MovieDetails

    // добавления в избранное 1
    //var inFavoriteToggle: ImageView = itemView.findViewById(R.id.favorite)

    fun bind(movie: Movie) {
        Glide.with(itemView)
            .load(movie.image)
            .into(cover)

        title.text = movie.title
        description.text = movie.description

        // добавления в избранное 2
        //inFavoriteToggle.setImageDrawable(getFavoriteToggleDrawable(movie.inFavorite))

        // переносим обработчик нажатия из MoviesAdapter из onBindViewHolder 2
        itemView.setOnClickListener {

            clickListener.onMovieClick(movie)
        }
        // переносим обработчик нажатия из MoviesAdapter из onBindViewHolder 3
        //inFavoriteToggle.setOnClickListener { clickListener.onFavoriteToggleClick(movie) }

    }

    //  добавления в избранное 3
    //private fun getFavoriteToggleDrawable(inFavorite: Boolean): Drawable? {
    //    return itemView.context.getDrawable(
    //        if(inFavorite) R.drawable.active_favorire else R.drawable.inactive_favorire
    //    )
    //}
}