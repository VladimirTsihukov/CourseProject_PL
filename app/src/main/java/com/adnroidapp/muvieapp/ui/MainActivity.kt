package com.adnroidapp.muvieapp.ui

import android.os.Bundle
import android.util.Log
import com.adnroidapp.muvieapp.App
import com.adnroidapp.muvieapp.ClassKey.LOG_KEY
import com.adnroidapp.muvieapp.R
import com.adnroidapp.muvieapp.mvp.presenter.PresenterMain
import moxy.MvpAppCompatActivity
import moxy.MvpView
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : MvpAppCompatActivity(), MvpView {

    private val navigatorHolder = App.instance.navigatorHolder
    private val navigator =
        SupportAppNavigator(this, supportFragmentManager, R.id.container) //осуществляет навигацию

    private val moxyPresenter by moxyPresenter {
        Log.v(LOG_KEY, "Create moxyPresenter")
        PresenterMain(App.instance.router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(LOG_KEY, "MainActivity onCreate()")
        setContentView(R.layout.activity_main)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        Log.v(LOG_KEY, "MainActivity onResumeFragments()")
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        Log.v(LOG_KEY, "MainActivity onPause()")
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        moxyPresenter.backClicked()
    }
}