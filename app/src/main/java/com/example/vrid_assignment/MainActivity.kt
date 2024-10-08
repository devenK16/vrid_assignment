package com.example.vrid_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vrid_assignment.ui.screen.blogdetail.BlogDetailScreen
import com.example.vrid_assignment.ui.screen.bloglist.BlogListScreen
import com.example.vrid_assignment.ui.theme.Vrid_assignmentTheme
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import java.net.URLEncoder

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Vrid_assignmentTheme {

                BlogNavigation()
            }
        }
    }
}


@Composable
fun BlogNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "blogList") {
        composable("blogList") {
            BlogListScreen(onBlogSelected = { url ->
                val encodedUrl = URLEncoder.encode(url, "UTF-8")
                navController.navigate("blogDetail/$encodedUrl")
            })
        }
        composable(
            route = "blogDetail/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url") ?: ""
            val decodedUrl = URLDecoder.decode(encodedUrl, "UTF-8")
            BlogDetailScreen(url = decodedUrl)
        }
    }
}