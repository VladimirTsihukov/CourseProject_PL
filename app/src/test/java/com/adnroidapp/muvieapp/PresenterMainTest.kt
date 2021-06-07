package com.adnroidapp.muvieapp

import com.adnroidapp.muvieapp.presenter.PresenterMain
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.terrakok.cicerone.Router

class PresenterMainTest {

    private val presenterMainTest = PresenterMain()

    @Mock
    lateinit var routerTest : Router

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenterMainTest.router = routerTest
    }

    @Test
    fun checkBackClicked() {
        presenterMainTest.backClicked()
        verify(routerTest, times(1)).exit()
    }

/*    @Test
    fun checkOnFirstViewAttach() {
        verify(routerTest, times(1))
            .replaceScreen(ArgumentMatchers.refEq(Screen.MoviesListScreen()))
    }*/
}