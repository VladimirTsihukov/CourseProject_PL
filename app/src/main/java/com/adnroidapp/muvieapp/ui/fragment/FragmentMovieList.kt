package com.adnroidapp.muvieapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.muvieapp.App
import com.adnroidapp.muvieapp.ClassKey
import com.adnroidapp.muvieapp.R
import com.adnroidapp.muvieapp.mvp.di.movie.MovieSubComponent
import com.adnroidapp.muvieapp.mvp.model.api.data.Movie
import com.adnroidapp.muvieapp.mvp.presenter.PresenterMovieList
import com.adnroidapp.muvieapp.mvp.view.MovieListView
import com.adnroidapp.muvieapp.ui.BackButtonListener
import com.adnroidapp.muvieapp.ui.adapter.AdapterMoviesFilm
import com.adnroidapp.muvieapp.ui.image.GlideImageLoaderActorMovies
import com.google.android.material.bottomnavigation.BottomNavigationView
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

    private var movieSubComponent: MovieSubComponent? = null

    private val presenter by moxyPresenter {
        movieSubComponent = App.instance.initMovieSubComponent()
        PresenterMovieList().apply {
            movieSubComponent?.inject(this)
        }
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
        adapter = AdapterMoviesFilm(presenter).apply {
            movieSubComponent?.inject(this)
        }
        recycler.adapter = adapter
    }

    override fun updateList(newMovies: List<Movie>) {
        Log.v(ClassKey.LOG_KEY, "FragmentMovieList: updateList")
        adapter.bindMovies(newMovies)
    }

    override fun release() {
        App.instance.releaseMovieSubComponent()
        movieSubComponent = null
    }

    override fun backPressed() = presenter.backPressed()
}