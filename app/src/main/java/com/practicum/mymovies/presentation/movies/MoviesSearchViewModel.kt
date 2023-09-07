package com.practicum.mymovies.presentation.movies

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.mymovies.domain.api.MoviesInteractor
import com.practicum.mymovies.domain.models.Movie
import com.practicum.mymovies.ui.movies.models.MoviesState

class MoviesSearchViewModel(private val moviesInteractor: MoviesInteractor) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()

        private val SOMETHING_WENT_WRONG = "something_went_wrong"
        private val NOTHING_FOUND = "nothing_found"
    }

    private val handler = Handler(Looper.getMainLooper())

    //private val mediatorStateLiveData = MediatorLiveData<MoviesState>().also { liveData ->
        // 1 добавили подписку на stateLiveData. Можно добавлять несколько подписок на различные LiveData и для каждой настроить определённое поведение.
    //    liveData.addSource(stateLiveData) { movieState ->
    //        liveData.value = when (movieState) {
    //            // 2 проверили состояние MoviesState.Content и отсортировали так, чтобы модели с флагом inFavorite оказались в начале списка.
    //            is MoviesState.Content -> MoviesState.Content(movieState.movies) //movieState.movies.sortedByDescending { it.inFavorite }
    //            is MoviesState.Empty -> movieState
    //            is MoviesState.Error -> movieState
    //            is MoviesState.Loading -> movieState
    //        }
     //   }
    //}


    private val stateLiveData = MutableLiveData<MoviesState>()

    //3 заменили stateLiveData на mediatorStateLiveData, потому что нам не нужно, чтобы отсортированный список отрисовывался
    fun observeState(): LiveData<MoviesState> = stateLiveData

    private val showToast = SingleLiveEvent<String?>()
    fun observeShowToast(): SingleLiveEvent<String?> = showToast

    private var latestSearchText: String? = null

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable { searchRequest(changedText) }

        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime,
        )
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(MoviesState.Loading)

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    val movies = mutableListOf<Movie>()
                    if (foundMovies != null) {
                        movies.addAll(foundMovies)
                    }

                    when {
                            errorMessage != null -> {
                                renderState(
                                    MoviesState.Error(
                                        message = SOMETHING_WENT_WRONG,
                                    )
                                )
                                showToast.postValue(errorMessage)
                            }

                            movies.isEmpty() -> {
                                renderState(
                                    MoviesState.Empty(
                                        errorMessage = NOTHING_FOUND,
                                    )
                                )
                            }

                            else -> {
                                renderState(
                                    MoviesState.Content(
                                        movies = movies,
                                    )
                                )
                            }
                    }

                }
            })
        }
    }


    private fun renderState(state: MoviesState) {
        stateLiveData.postValue(state)
    }

    //fun toggleFavorite(movie: Movie) {
    //    if (movie.inFavorite) {
    //        moviesInteractor.removeMovieFromFavorites(movie)
    //    } else {
    //        moviesInteractor.addMovieToFavorites(movie)
    //    }

        // Добавьте изменения текущего состояния экрана сразу при вызове toggleFavorite,
        // не дожидаясь, пока пользователь повторит запрос
    //    updateMovieContent(movie.id, movie.copy(inFavorite = !movie.inFavorite))
    //}

    //функция переноса избранных фильмов в начало списка
    //private fun updateMovieContent(movieId: String, newMovie: Movie) {
    //    val currentState = stateLiveData.value

        // 2
    //    if (currentState is MoviesState.Content) {
            // 3
    //        val movieIndex = currentState.movies.indexOfFirst { it.id == movieId }

            // 4
    //        if (movieIndex != -1) {
                // 5
    //            stateLiveData.value = MoviesState.Content(
    //                currentState.movies.toMutableList().also {
    //                    it[movieIndex] = newMovie
    //                }
    //            )
    //        }
    //    }
    //}
}




