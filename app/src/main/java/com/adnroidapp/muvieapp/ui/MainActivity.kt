package com.adnroidapp.muvieapp.ui

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.adnroidapp.muvieapp.App
import com.adnroidapp.muvieapp.R
import com.adnroidapp.muvieapp.mvp.presenter.PresenterMain
import com.adnroidapp.muvieapp.mvp.view.MainView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator =
        SupportAppNavigator(
            this,
            supportFragmentManager,
            R.id.nav_host_fragment_container
        )

    private val moxyPresenter by moxyPresenter {
        PresenterMain().apply {
            App.instance.appComponent.inject(this)
        }
    }

    private lateinit var viewLoader: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.instance.appComponent.inject(this)

        viewLoader = findViewById(R.id.loader_connect_internet)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
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

    override fun setVisibilityProgressBar(checkInternet: Boolean) {
        if (checkInternet) {
            viewLoader.visibility = View.INVISIBLE
        } else {
            viewLoader.visibility = View.VISIBLE
        }
    }
}