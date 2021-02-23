package com.adnroidapp.muvieapp.mvp.presenter

import android.util.Log
import com.adnroidapp.muvieapp.ClassKey
import com.adnroidapp.muvieapp.mvp.model.api.data.Movie
import com.adnroidapp.muvieapp.mvp.model.cache.IMoviesCache
import com.adnroidapp.muvieapp.mvp.model.retrofit.ILoadMoviesList
import com.adnroidapp.muvieapp.mvp.navigator.Screen
import com.adnroidapp.muvieapp.mvp.view.MovieListView
import com.adnroidapp.muvieapp.mvp.view.preenterView.PresenterDetailViewClick
import io.reactivex.rxjava3.core.Scheduler
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class PresenterMovieList: MvpPresenter<MovieListView>(), PresenterDetailViewClick {

    @Inject
    lateinit var cache: IMoviesCache

    @Inject
    lateinit var retrofitLoadMovies: ILoadMoviesList

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    var movieFavorite = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.v(ClassKey.LOG_KEY, "PresenterMovieList: onFirstViewAttach()")
        viewState.initAdapter()
        loadMoviesPopular()
    }

    fun loadMoviesPopular() {
        Log.v(ClassKey.LOG_KEY, "PresenterMovieList: loadMoviesPopular()")
        retrofitLoadMovies.retrofitLoadMoviesPopular()
            .observeOn(mainThreadScheduler)
            .subscribe({ movieList ->
                if (movieList != null) {
                    Log.v(
                        ClassKey.LOG_KEY,
                        "PresenterMovieList: loadMoviesPopular list.size = ${movieList.size}"
                    )
                    viewState.updateList(movieList)
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
                    "PresenterMovieList: loadMoviesTopRate list.size = ${movieList.size}"
                )
                viewState.updateList(movieList)
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

    fun loadMoviesFavorite() {
        Log.v(ClassKey.LOG_KEY, "PresenterMovieList: loadMoviesFavorite()")
        cache.getCacheMoviesLike().observeOn(mainThreadScheduler)
            .subscribe({
                Log.v(
                    ClassKey.LOG_KEY,
                    "PresenterMovieList: loadMoviesFavorite list.size = ${it.size}"
                )
                viewState.updateList(it)
            }, {
                Log.v(
                    ClassKey.LOG_KEY,
                    "PresenterMovieList: error in loadMoviesFavorite: ${it.message}"
                )
            })
    }

    override fun clickLikeIcon(iconLike: Boolean, movies: Movie) {
        if (iconLike) {
            cache.deleteMovieLike(movies.id).observeOn(mainThreadScheduler)
                .subscribe({
                    Log.v(ClassKey.LOG_KEY, "PresenterMovieList: delete like movie result = $it")
                    if (movieFavorite) loadMoviesFavorite()
                }, {
                    Log.v(ClassKey.LOG_KEY, "PresenterMovieList: error deleteMovieLike: ${it.message}")
                })
        } else {
            cache.putCacheMoviesLike(movies)
                .observeOn(mainThreadScheduler)
                .subscribe({
                    Log.v(ClassKey.LOG_KEY, "PresenterMovieList: put like movie result = $it")
                }, {
                    Log.v(ClassKey.LOG_KEY, "PresenterMovieList: error putCacheMoviesLike: ${it.message}")
                })
        }
    }

    fun backPressed(): Boolean {
        Log.v(ClassKey.LOG_KEY, "PresenterMovieList: backPressed()")
        router.exit()
        return true
    }
}