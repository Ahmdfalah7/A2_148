package com.example.uaspam

import android.app.Application
import com.example.uaspam.dependenciesinjection.AppContainer
import com.example.uaspam.dependenciesinjection.BukuContainer

class BukuApplications : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = BukuContainer()
    }
}
