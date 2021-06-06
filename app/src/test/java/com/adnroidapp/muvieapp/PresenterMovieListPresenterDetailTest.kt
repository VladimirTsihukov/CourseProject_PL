package com.adnroidapp.muvieapp

import com.adnroidapp.muvieapp.model.EnumTypeMovie
import com.adnroidapp.muvieapp.presenter.PresenterMovieListPresenterDetail
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PresenterMovieListPresenterDetailTest {

    @Mock
    lateinit var presenterDetailTest : PresenterMovieListPresenterDetail

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun checkMethodLoadMovies() {
        val typeMovieTest = EnumTypeMovie.FAVORITE
        presenterDetailTest.loadMovies(typeMovieTest)
        verify(presenterDetailTest, times(1)).loadMoviesType(typeMovieTest)
    }

}