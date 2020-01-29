package kz.robot

import android.app.Application
import android.content.Context
import kz.robot.di.appModule
import kz.robot.di.databaseModule
import kz.robot.di.schedulerModule
import org.koin.android.ext.android.startKoin

class RobotApp : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
//        MultiDex.install(this)
        startKoin(this, listOf(appModule, schedulerModule, databaseModule))
    }

}