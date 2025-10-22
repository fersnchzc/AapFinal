package com.example.appfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appfinal.ui.theme.AapFinalTheme
import com.example.appfinal.ui.views.postdetail.PostDetailScreen
import com.example.appfinal.ui.views.postlist.PostListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AapFinalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "postList") {
                        composable("postList") {
                            PostListScreen(
                                onPostClick = { postId ->
                                    navController.navigate("postDetail/$postId")
                                }
                            )
                        }
                        composable(
                            route = "postDetail/{postId}",
                            arguments = listOf(navArgument("postId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val postId = backStackEntry.arguments?.getInt("postId") ?: -1
                            PostDetailScreen(postId = postId)
                        }
                    }
                }
            }
        }
    }
}
