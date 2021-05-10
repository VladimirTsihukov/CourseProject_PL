package com.adnroidapp.muvieapp.mvp.model.newtwork

import io.reactivex.Observable
import io.reactivex.Single

interface INetworkStatus {
    fun isOnline(): Observable<Boolean>
    fun isOnlineSingle(): Single<Boolean>
}