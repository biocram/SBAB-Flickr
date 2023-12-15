package com.marco.sbab_flickr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.marco.sbab_flickr.navigation.Screen
import com.marco.sbab_flickr.navigation.SetupNavGraph
import com.marco.sbab_flickr.ui.theme.SBAB_FlickrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SBAB_FlickrTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = Screen.Search.route,
                    navController = navController
                )
            }
        }
    }
}
