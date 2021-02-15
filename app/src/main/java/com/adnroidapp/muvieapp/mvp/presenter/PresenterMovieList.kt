package com.adnroidapp.muvieapp.mvp.presenter

import android.util.Log
import com.adnroidapp.muvieapp.ClassKey
import com.adnroidapp.muvieapp.mvp.model.retrofit.ILoadMoviesList
import com.adnroidapp.muvieapp.mvp.navigator.Screen
import com.adnroidapp.muvieapp.mvp.view.MovieListView
import com.adnroidapp.muvieapp.mvp.view.preenterView.PresenterDetailViewClick
import io.reactivex.rxjava3.core.Scheduler
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

@InjectViewState
class PresenterMovieList(
    private val router: Router,
    private val mainThreadScheduler: Scheduler,
    private val retrofitLoadMovies: ILoadMoviesList
) : MvpPresenter<MovieListView>(), PresenterDetailViewClick {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.v(ClassKey.LOG_KEY, "PresenterMovieList: onFirstViewAttach()")
        viewState.initAdapter()
        loadMoviesPopular()
    }

    fun loadMoviesPopular() {
        retrofitLoadMovies.retrofitLoadMoviesPopular()
            .observeOn(mainThreadScheduler)
            .subscribe({ movieList ->
                if (movieList != null) {
                    Log.v(
                        ClassKey.LOG_KEY,
                        "PresenterMovieList: loadMoviesPopular list.size = ${movieList.results.size}"
                    )
                    viewState.updateList(movieList.results)
                }
            }, {
                Log.v(
                    ClassKey.LOG_KEY,
                    "PresenterMovieList: error in loadMoviesPopular: ${it.message}"
                )
            })
    }

    fun loadMoviesTopRate() {
        retrofitLoadMovies.retrofitLoadMoviesTopRate()
            .observeOn(mainThreadScheduler)
            .subscribe({ movieList ->
                Log.v(
                    ClassKey.LOG_KEY,
                    "PresenterMovieList: loadMoviesTopRate list.size = ${movieList.results.size}"
                )
                viewState.updateList(movieList.results)
            }, {
                Log.v(
                    ClassKey.LOG_KEY,
                    "PresenterMovieList: error in loadMoviesTopRate: ${it.message}"
                )
            })
    }

    override fun clickMovie(movieId: Long) {
        Log.v(ClassKey.LOG_KEY, "PresenterMovieList: clickMovie go to FragmentMovieDetail")
        router.navigateTo(Screen.MovieDetail(movieId))
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}