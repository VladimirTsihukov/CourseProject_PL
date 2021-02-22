package com.adnroidapp.muvieapp.mvp.presenter

import android.util.Log
import com.adnroidapp.muvieapp.ClassKey
import com.adnroidapp.muvieapp.mvp.model.retrofit.ILoadMoviesDetail
import com.adnroidapp.muvieapp.mvp.view.MovieDetailView
import io.reactivex.rxjava3.core.Scheduler
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class PresenterMovieDetail(val movieId: Long): MvpPresenter<MovieDetailView>() {

    @Inject
    lateinit var  retrofitLoadDetail: ILoadMoviesDetail

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initView()
        viewState.initAdapterActor()
        loadMovieDetail()
        loadMovieActors()
    }

   private fun loadMovieDetail() {
        retrofitLoadDetail.retrofitLoadMoviesDetail(movieId)
            .observeOn(mainThreadScheduler)
            .subscribe( {movieDetail ->
                Log.v(ClassKey.LOG_KEY, "PresenterMovieDetail: loadMovieDetail movie name = ${movieDetail.title}")
                viewState.initViewMovieDetail(movieDetail)
                viewState.invisibleLoader()
        }, { error ->
                Log.v(ClassKey.LOG_KEY, "PresenterMovieDetail: error in loadMovieDetail: ${error.message}")
            })
    }

   private fun loadMovieActors() {
        retrofitLoadDetail.retrofitLoadMovieActors(movieId)
            .observeOn(mainThreadScheduler)
            .subscribe({cast ->
                Log.v(ClassKey.LOG_KEY, "PresenterMovieDetail: loadMovieActors actors.size = ${cast.size}")
                viewState.updateAdapterActor(cast)
                viewState.invisibleLoader()
            }, {error ->
                Log.v(ClassKey.LOG_KEY, "PresenterMovieDetail: error in loadMovieActors: ${error.message}")
            })
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}