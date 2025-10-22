// En: di/AppModule.kt
package com.example.aapfinal.di

import com.example.appfinal.ui.views.postdetail.PostDetailViewModel
import com.example.appfinal.ui.views.postlist.PostListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { PostListViewModel() }
    viewModel { (postId: Int) -> PostDetailViewModel(postId) }
}
