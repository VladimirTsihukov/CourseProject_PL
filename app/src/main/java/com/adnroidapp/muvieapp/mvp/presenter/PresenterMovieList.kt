package com.adnroidapp.muvieapp.mvp.presenter

import android.annotation.SuppressLint
import android.util.Log
import com.adnroidapp.muvieapp.ClassKey
import com.adnroidapp.muvieapp.mvp.model.EnumTypeMovie
import com.adnroidapp.muvieapp.mvp.model.api.data.Movie
import com.adnroidapp.muvieapp.mvp.model.cache.IMoviesCache
import com.adnroidapp.muvieapp.mvp.model.newtwork.INetworkStatus
import com.adnroidapp.muvieapp.mvp.model.retrofit.ILoadMoviesList
import com.adnroidapp.muvieapp.mvp.navigator.Screen
import com.adnroidapp.muvieapp.mvp.view.MovieListView
import com.adnroidapp.muvieapp.mvp.view.preenterView.PresenterDetailViewClick
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
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

    @Inject
    lateinit var networkStatus: INetworkStatus

    private var flagMovieFavorite = false
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initAdapter()
        loadMovies(EnumTypeMovie.POPULAR)
    }

    fun loadMovies(typeMovie: EnumTypeMovie) {
        flagMovieFavorite = typeMovie.name == EnumTypeMovie.FAVORITE.name
        loadMoviesType(typeMovie)
    }

    @SuppressLint("CheckResult")
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

    @SuppressLint("CheckResult")
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

    @SuppressLint("CheckResult")
    override fun clickLikeIcon(iconLike: Boolean, movies: Movie) {
        if (iconLike) {
            cache.deleteMovieLike(movies.id).observeOn(mainThreadScheduler)
                .subscribe({
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

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}