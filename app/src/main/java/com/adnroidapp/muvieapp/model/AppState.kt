package com.adnroidapp.muvieapp.model

import com.adnroidapp.muvieapp.model.api.data.Movie

sealed class AppState {
    data class Success (val data: List<Movie>) : AppState()
    data class Error (val error : Throwable) : AppState()
}
