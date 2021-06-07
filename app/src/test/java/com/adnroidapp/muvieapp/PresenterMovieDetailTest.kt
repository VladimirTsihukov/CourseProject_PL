package com.adnroidapp.muvieapp

import com.adnroidapp.muvieapp.presenter.PresenterMovieDetail
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.terrakok.cicerone.Router

class PresenterMovieDetailTest {

    private val movieIdTest = 123L
    private val presenterMovieDetailTest = PresenterMovieDetail(movieIdTest)

    @Mock
    private lateinit var routerTest: Router

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenterMovieDetailTest.router = routerTest
    }

    @Test
    fun checkBackPressed() {
        presenterMovieDetailTest.backPressed()
        verify(routerTest, times(1)).exit()
    }

}