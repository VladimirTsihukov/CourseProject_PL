package com.adnroidapp.muvieapp.mvp.presenter

import android.util.Log
import com.adnroidapp.muvieapp.ClassKey
import com.adnroidapp.muvieapp.mvp.model.EnumTypeMovie
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
class PresenterMovieList : MvpPresenter<MovieListView>(), PresenterDetailViewClick {

    @Inject
    lateinit var cache: IMoviesCache

    @Inject
    lateinit var retrofitLoadMovies: ILoadMoviesList

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    private var flagMovieFavorite = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initAdapter()
        loadMovies(EnumTypeMovie.POPULAR)
    }

    fun loadMovies(typeMovie: EnumTypeMovie) {
        flagMovieFavorite = typeMovie.name == EnumTypeMovie.FAVORITE.name
        loadMoviesType(typeMovie)
    }

    private fun loadMoviesType(typeMovie: EnumTypeMovie) {
        if (typeMovie.name == EnumTypeMovie.FAVORITE.name) {
            loadMoviesFavorite()
        } else {
            retrofitLoadMovies.loadMovieInServer(typeMovie)
                .observeOn(mainThreadScheduler)
                .subscribe({ movieList ->
                    if (movieList != null) {
                        viewState.updateList(movieList)
                    }
                }, {
                    Log.e(ClassKey.LOG_KEY, "Error in loadMoviesType(): ${it.message}")
                })
        }
    }

    private fun loadMoviesFavorite() {
        cache.getCacheMoviesLike().observeOn(mainThreadScheduler)
            .subscribe({
                viewState.updateList(it)
            }, {
                Log.e(ClassKey.LOG_KEY, "Error in loadMoviesFavorite(): ${it.message}")
            })
    }

    override fun clickMovie(movieId: Long) {
        router.navigateTo(Screen.MovieDetail(movieId))
    }

    override fun clickLikeIcon(iconLike: Boolean, movies: Movie) {
        if (iconLike) {
            cache.deleteMovieLike(movies.id).observeOn(mainThreadScheduler)
                .subscribe({
                    Log.v(ClassKey.LOG_KEY, "PresenterMovieList: delete like movie")
                    if (flagMovieFavorite) loadMoviesFavorite()
                }, {
                    Log.v(ClassKey.LOG_KEY, "Error in deleteMovieLike(): ${it.message}")
                })
        } else {
            getMovieLikeInDB(movies)
        }
    }

    private fun getMovieLikeInDB(movies: Movie) {
        cache.putCacheMoviesLike(movies)
            .observeOn(mainThreadScheduler)
            .subscribe()
    }

    fun backPressed(): Boolean {
        Log.v(ClassKey.LOG_KEY, "PresenterMovieList: backPressed()")
        router.exit()
        return true
    }
}