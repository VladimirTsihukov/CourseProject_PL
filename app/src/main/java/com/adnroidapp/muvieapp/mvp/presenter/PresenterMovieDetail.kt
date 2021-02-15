package com.adnroidapp.muvieapp.mvp.presenter

import android.util.Log
import com.adnroidapp.muvieapp.ClassKey
import com.adnroidapp.muvieapp.mvp.model.retrofit.ILoadMoviesList
import com.adnroidapp.muvieapp.mvp.view.MovieDetailView
import io.reactivex.rxjava3.core.Scheduler
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class PresenterMovieDetail(
    val movieId: Long,
    private val mainThreadScheduler: Scheduler,
    private val retrofitLoadMovies: ILoadMoviesList
): MvpPresenter<MovieDetailView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initAdapterActor()
        loadMovieDetail()
        loadMovieActors()
    }

   private fun loadMovieDetail() {
        retrofitLoadMovies.retrofitLoadMoviesDetail(movieId)
            .observeOn(mainThreadScheduler)
            .subscribe( {movieDetail ->
                Log.v(ClassKey.LOG_KEY, "PresenterMovieDetail: loadMovieDetail movie name = ${movieDetail.title}")
                viewState.initViewMovieDetail(movieDetail)
        }, { error ->
                Log.v(ClassKey.LOG_KEY, "PresenterMovieDetail: error in loadMovieDetail: ${error.message}")
            })
    }

   private fun loadMovieActors() {
        retrofitLoadMovies.retrofitLoadMovieActors(movieId)
            .observeOn(mainThreadScheduler)
            .subscribe({movieActor ->
                Log.v(ClassKey.LOG_KEY, "PresenterMovieDetail: loadMovieActors actors.size = ${movieActor.cast.size}")
                viewState.updateAdapterActor(movieActor.cast)
                viewState.invisibleLoader()
            }, {error ->
                Log.v(ClassKey.LOG_KEY, "PresenterMovieDetail: error in loadMovieActors: ${error.message}")
            })
    }
}