package com.example.appfinal.di

import androidx.room.Room
import com.example.appfinal.data.local.AppDatabase
import com.example.appfinal.data.local.PostDao
import com.example.appfinal.network.PostApiService
import com.example.appfinal.network.PostApiServiceImpl
import com.example.appfinal.repository.PostRepository
import com.example.appfinal.repository.PostRepositoryImpl
import com.example.appfinal.ui.views.postdetail.PostDetailViewModel
import com.example.appfinal.ui.views.postlist.PostListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
    single<PostDao> {
        get<AppDatabase>().postDao()
    }

    viewModel { PostListViewModel(get()) }
    viewModel { (postId: Int) -> PostDetailViewModel(postId, get()) }

    single<PostRepository> { PostRepositoryImpl(get(), get()) } bind PostRepository::class

    singleOf(::PostApiServiceImpl) bind PostApiService::class
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }
}
