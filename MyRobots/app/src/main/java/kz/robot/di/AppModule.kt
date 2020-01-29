package kz.robot.di

import android.content.Context
import androidx.room.Room
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import kz.robot.data.ApiService
import kz.robot.data.db.RobotAppDatabase
import kz.robot.data.repository.RobotRepository
import kz.robot.data.repository.RobotRepositoryImpl
import kz.robot.ui.robot.RobotViewModel
import kz.robot.util.rx.ApplicationSchedulerProvider
import kz.robot.util.rx.SchedulerProvider

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val appModule = module {
    factory {
        createOkHttpClient(get())
    }
    factory {
        createWebService<ApiService>(get(), "http://help.flip.kz/")
    }

    module("repository") {

        factory {
            RobotRepositoryImpl(get(), get()) as RobotRepository
        }

        module("viewModel") {

            viewModel {
                RobotViewModel(get(), get())
            }
        }
    }
}

val schedulerModule = module {
    single { ApplicationSchedulerProvider() as SchedulerProvider }
}
val databaseModule = module {
    single {
        Room.databaseBuilder(get(),
            RobotAppDatabase::class.java, "robot_db")
            .allowMainThreadQueries()
            .build()
    }
}

fun createOkHttpClient(context: Context): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(OkHttpProfilerInterceptor())
            .connectTimeout((60 * 1000).toLong(), TimeUnit.SECONDS)
            .readTimeout((60 * 1000).toLong(), TimeUnit.SECONDS)
    return okHttpBuilder.build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}
