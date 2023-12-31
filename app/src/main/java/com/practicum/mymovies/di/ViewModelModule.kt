package com.practicum.mymovies.di

import com.practicum.mymovies.presentation.movies.MoviesSearchViewModel
import com.practicum.mymovies.presentation.poster.AboutViewModel
import com.practicum.mymovies.presentation.poster.PosterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MoviesSearchViewModel(get()) //androidContext(), get()
    }

    //viewModel {
    //    PosterViewModel()
    //}

    viewModel {(movieId: String) ->
        AboutViewModel(movieId, get())
    }

    viewModel {(posterUrl: String) ->
        PosterViewModel(posterUrl)
    }

}