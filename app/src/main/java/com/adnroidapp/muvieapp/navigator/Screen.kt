package com.adnroidapp.muvieapp.navigator

import com.adnroidapp.muvieapp.ui.fragment.FragmentMovieDetail
import com.adnroidapp.muvieapp.ui.fragment.FragmentMovieList
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screen {
    class MoviesListScreen : SupportAppScreen() {
        override fun getFragment() = FragmentMovieList.newInstance()
    }

    class MovieDetail(private val id: Long) : SupportAppScreen() {
        override fun getFragment() = FragmentMovieDetail.newInstance(id)
    }
}