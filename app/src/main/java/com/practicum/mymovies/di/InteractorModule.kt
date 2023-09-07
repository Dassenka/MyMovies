package com.practicum.mymovies.di

import com.practicum.mymovies.domain.api.MoviesInteractor
import com.practicum.mymovies.domain.impl.MoviesInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }

    //single<SearchHistoryInteractor> {
    //    SearchHistoryInteractorImpl(get())
    //}

}