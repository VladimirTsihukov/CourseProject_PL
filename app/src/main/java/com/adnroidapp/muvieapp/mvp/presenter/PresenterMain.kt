package com.adnroidapp.muvieapp.mvp.presenter

import com.adnroidapp.muvieapp.mvp.navigator.Screen
import com.adnroidapp.muvieapp.mvp.view.MainView
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

@InjectViewState                                //TODO 1 (необходимо ли в Kotlin ставить эту аннотацию)
class PresenterMain(private val router: Router) : MvpPresenter<MainView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screen.MoviesListScreen())
    }

    fun backClicked() {
        router.exit()
    }
}