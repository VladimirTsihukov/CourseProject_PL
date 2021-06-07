package com.adnroidapp.muvieapp

import com.adnroidapp.muvieapp.model.cache.IMoviesCache
import com.adnroidapp.muvieapp.model.newtwork.INetworkStatus
import com.adnroidapp.muvieapp.model.retrofit.ILoadMoviesList
import com.adnroidapp.muvieapp.navigator.Screen
import com.adnroidapp.muvieapp.presenter.PresenterMovieListPresenterDetail
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Scheduler
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.terrakok.cicerone.Router

class PresenterMovieListPresenterDetailTest {

    private lateinit var presenterDetailTest : PresenterMovieListPresenterDetail

    @Mock
    lateinit var cacheTest: IMoviesCache
    @Mock
    lateinit var retrofitLoadMoviesTest: ILoadMoviesList
    @Mock
    lateinit var routerTest: Router
    @Mock
    lateinit var mainThreadSchedulerTest: Scheduler
    @Mock
    lateinit var networkStatusTest: INetworkStatus

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenterDetailTest = PresenterMovieListPresenterDetail()
        presenterDetailTest.cache = cacheTest
        presenterDetailTest.retrofitLoadMovies = retrofitLoadMoviesTest
        presenterDetailTest.router = routerTest
        presenterDetailTest.mainThreadScheduler = mainThreadSchedulerTest
        presenterDetailTest.networkStatus = networkStatusTest
    }

    @Test
    fun checkClickMovie() {
        val testId = 123L
        presenterDetailTest.clickMovie(testId)
        verify(routerTest, times(1))
            .navigateTo(ArgumentMatchers.refEq(Screen.MovieDetail(testId)))
    }

    @Test
    fun checkBackPressed() {
        presenterDetailTest.backPressed()
        verify(routerTest, times(1)).exit()
    }
}