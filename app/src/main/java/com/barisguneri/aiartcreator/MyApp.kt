package com.barisguneri.aiartcreator

import android.app.Application
import androidx.room.Room
import com.barisguneri.aiartcreator.repository.Repository
import com.barisguneri.aiartcreator.data.api.ApiService
import com.barisguneri.aiartcreator.db.ImageDatabase
import com.barisguneri.aiartcreator.viewmodel.AppViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(appModule)
        }
    }

    private val appModule = module {

        // Retrofit
        single {
            val baseUrl = "https://dezgo.p.rapidapi.com/"
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        single {
            Room.databaseBuilder(
                androidContext(),
                ImageDatabase::class.java,
                "image_database"
            ).build()
        }

        single {
            get<ImageDatabase>().imageDao()
        }


        single {
            get<Retrofit>().create(ApiService::class.java)
        }


        // Repository
        single {
            Repository(get(),get())
        }

        // AppViewModel
        viewModel {
            AppViewModel(get())
        }
    }
}