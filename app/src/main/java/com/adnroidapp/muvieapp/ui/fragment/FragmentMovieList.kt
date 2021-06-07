package com.adnroidapp.muvieapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.muvieapp.App
import com.adnroidapp.muvieapp.R
import com.adnroidapp.muvieapp.di.movie.MovieSubComponent
import com.adnroidapp.muvieapp.model.AppState
import com.adnroidapp.muvieapp.model.EnumTypeMovie
import com.adnroidapp.muvieapp.presenter.PresenterMovieListPresenterDetail
import com.adnroidapp.muvieapp.presenter.view.ViewMovieList
import com.adnroidapp.muvieapp.ui.BackButtonListener
import com.adnroidapp.muvieapp.ui.adapter.AdapterMoviesFilm
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class FragmentMovieList : MvpAppCompatFragment(R.layout.fragment_movies_list), BackButtonListener,
    ViewMovieList {

    private lateinit var adapter: AdapterMoviesFilm
    private lateinit var recycler: RecyclerView
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var mShimmerContainer: ShimmerFrameLayout

    private var movieSubComponent: MovieSubComponent? = null

    private val presenter by moxyPresenter {
        movieSubComponent = App.instance.initMovieSubComponent()
        PresenterMovieListPresenterDetail().apply {
            movieSubComponent?.inject(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initShimmerContainer(view)
        initBottomNavigation()

        movieSubComponent = App.instance.initMovieSubComponent()

        bottomNav.visibility = View.VISIBLE
        recycler = view.findViewById(R.id.res_view_move_list)
    }

    private fun initShimmerContainer(view: View) {
        mShimmerContainer = view.findViewById(R.id.shimmer_view_container)
        startAnimateShimmer()
    }

    private fun initBottomNavigation() {
        activity?.let {
            bottomNav = it.findViewById(R.id.bottom_navigation)
            bottomNav.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_popular -> {
                        presenter.loadMovies(EnumTypeMovie.POPULAR)
                        true
                    }
                    R.id.nav_top -> {
                        presenter.loadMovies(EnumTypeMovie.TOP)
                        true
                    }
                    R.id.nav_favorite -> {
                        presenter.loadMovies(EnumTypeMovie.FAVORITE)
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
        adapter = AdapterMoviesFilm(presenter).apply {
            movieSubComponent?.inject(this)
        }
        recycler.adapter = adapter
    }

    override fun getResponse(success: AppState) {
        stopAnimateShimmer()
        when (success) {
            is AppState.Success -> adapter.bindMovies(success.data)
            is AppState.Error -> showError(success.error)
        }
    }

    private fun showError(error: Throwable) {
        Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun release() {
        App.instance.releaseMovieSubComponent()
        movieSubComponent = null
    }

    private fun startAnimateShimmer() {
        mShimmerContainer.visibility = View.VISIBLE
        mShimmerContainer.startShimmerAnimation()
    }

    private fun stopAnimateShimmer() {
        mShimmerContainer.stopShimmerAnimation()
        mShimmerContainer.visibility = View.GONE
    }

    override fun backPressed() = presenter.backPressed()

    companion object {
        fun newInstance() = FragmentMovieList()
    }
}