package com.practicum.mymovies.data

import androidx.lifecycle.Transformations.map
import com.practicum.mymovies.data.dto.MovieDetailsRequest
import com.practicum.mymovies.data.dto.MovieDetailsResponse
import com.practicum.mymovies.data.dto.MoviesSearchRequest
import com.practicum.mymovies.data.dto.MoviesSearchResponse
import com.practicum.mymovies.domain.api.MoviesRepository
import com.practicum.mymovies.domain.models.Movie
import com.practicum.mymovies.domain.models.MovieDetails
import com.practicum.mymovies.ui.movies.LocalStorage
import com.practicum.mymovies.util.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    // 1 получить значение флага inFavorite в модели Movie
    //private val localStorage: LocalStorage,
) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                //val stored = localStorage.getSavedFavorites()

                with(response as MoviesSearchResponse) {
                    Resource.Success(results.map {
                        Movie(
                            it.id, it.resultType, it.image, it.title, it.description,
                        )
                    })
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    //override fun addMovieToFavorites(movie: Movie) {
    //    localStorage.addToFavorites(movie.id)
    //}

    //override fun removeMovieFromFavorites(movie: Movie) {
    //    localStorage.removeFromFavorites(movie.id)
    //}
    override fun getMovieDetails(movieId: String): Resource<MovieDetails> {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                with(response as MovieDetailsResponse) {
                    Resource.Success(
                        MovieDetails(
                            id, title, imDbRating, year,
                            countries, genres, directors, writers, stars, plot
                        )
                    )
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")

            }
        }
    }
}