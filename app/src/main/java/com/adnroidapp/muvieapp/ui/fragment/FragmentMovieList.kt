package com.adnroidapp.muvieapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.muvieapp.App
import com.adnroidapp.muvieapp.ClassKey
import com.adnroidapp.muvieapp.R
import com.adnroidapp.muvieapp.mvp.model.api.ApiFactory
import com.adnroidapp.muvieapp.mvp.model.api.data.Movie
import com.adnroidapp.muvieapp.mvp.model.cache.roomcache.CacheRoomMovies
import com.adnroidapp.muvieapp.mvp.model.entity.room.db.DBMovies
import com.adnroidapp.muvieapp.mvp.model.retrofit.RetrofitLoadMoviesList
import com.adnroidapp.muvieapp.mvp.presenter.PresenterMovieList
import com.adnroidapp.muvieapp.mvp.view.MovieListView
import com.adnroidapp.muvieapp.ui.BackButtonListener
import com.adnroidapp.muvieapp.ui.adapter.AdapterMoviesFilm
import com.adnroidapp.muvieapp.ui.image.GlideImageLoaderMovies
import com.adnroidapp.muvieapp.ui.network.AndroidNetworkStatus
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class FragmentMovieList : MvpAppCompatFragment(R.layout.fragment_movies_list), BackButtonListener,
    MovieListView {

    companion object {
        fun newInstance() = FragmentMovieList()
    }

    private lateinit var adapter: AdapterMoviesFilm
    private lateinit var recycler: RecyclerView
    private lateinit var bottomNav: BottomNavigationView

    private val presenter by moxyPresenter {
        PresenterMovieList(
            router = App.instance.router,
            mainThreadScheduler = AndroidSchedulers.mainThread(),
            cache = CacheRoomMovies(DBMovies.instance(App.instance)),
            retrofitLoadMovies = RetrofitLoadMoviesList(
                ApiFactory.apiServiceMovies,
                AndroidNetworkStatus(App.instance),
                CacheRoomMovies(DBMovies.instance(App.instance))
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation()
        Log.v(ClassKey.LOG_KEY, "FragmentMovieList: onViewCreated()")
        bottomNav.visibility = View.VISIBLE
        recycler = view.findViewById(R.id.res_view_move_list)
    }

    private fun initBottomNavigation() {
        activity?.let{
            bottomNav = it.findViewById(R.id.bottom_navigation)
            bottomNav.setOnNavigationItemSelectedListener { item ->
                when(item.itemId) {
                    R.id.nav_popular -> {
                        presenter.loadMoviesPopular()
                        presenter.movieFavorite = false
                        true
                    }
                    R.id.nav_top -> {
                        presenter.loadMoviesTopRate()
                        presenter.movieFavorite = false
                        true
                    }
                    R.id.nav_favorite -> {
                        presenter.loadMoviesFavorite()
                        presenter.movieFavorite = true
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
        }
    }

    override fun initAdapter() {
        Log.v(ClassKey.LOG_KEY, "FragmentMovieList: init adapter")
        adapter = AdapterMoviesFilm(presenter, GlideImageLoaderMovies())
        recycler.adapter = adapter
    }

    override fun updateList(newMovies: List<Movie>) {
        Log.v(ClassKey.LOG_KEY, "FragmentMovieList: updateList")
        adapter.bindMovies(newMovies)
    }

    override fun backPressed() = presenter.backPressed()
}