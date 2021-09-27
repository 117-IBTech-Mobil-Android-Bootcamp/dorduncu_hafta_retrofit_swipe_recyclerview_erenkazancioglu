package com.erenkazancioglu.patikadevweek4

import android.app.Application
import com.erenkazancioglu.patikadevweek4.service.ServiceConnector

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceConnector.init()
    }
}