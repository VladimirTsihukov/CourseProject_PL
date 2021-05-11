package com.adnroidapp.muvieapp.mvp.presenter

import com.adnroidapp.muvieapp.mvp.model.newtwork.INetworkStatus
import com.adnroidapp.muvieapp.mvp.navigator.Screen
import com.adnroidapp.muvieapp.mvp.view.MainView
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class PresenterMain : MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    @Inject
    lateinit var networkStatus: INetworkStatus

    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screen.MoviesListScreen())
        checkedInternet()
    }

    private fun checkedInternet() {
        disposable.add(networkStatus.isOnline()
            .observeOn(mainThreadScheduler)
            .subscribe {
                viewState.setVisibilityProgressBar(it)
            })
    }

    fun backClicked() {
        router.exit()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}