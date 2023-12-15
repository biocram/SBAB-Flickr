package com.marco.sbab_flickr.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.marco.sbab_flickr.presentation.DetailsScreen
import com.marco.sbab_flickr.presentation.SearchScreen
import com.marco.sbab_flickr.util.Constants.DETAILS_SCREEN_ARGUMENT_KEY

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController,
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        searchRoute(
            navigateToDetails = {
                navController.navigate(Screen.Details.passItemId(itemId = it))
            },
        )
        detailsRoute(
            navigateBack = {
                navController.popBackStack()
            }
        )
    }
}

fun NavGraphBuilder.searchRoute(
    navigateToDetails: (String) -> Unit,
) {
    composable(route = Screen.Search.route) {
        SearchScreen(
            navigateToDetails = navigateToDetails,
        )
    }
}

fun NavGraphBuilder.detailsRoute(navigateBack: () -> Unit) {
    composable(
        route = Screen.Details.route,
        arguments = listOf(navArgument(name = DETAILS_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val itemId = navBackStackEntry.arguments!!.getInt(DETAILS_SCREEN_ARGUMENT_KEY)
        LaunchedEffect(key1 = itemId) {
            // pass itemId to the viewmodel for preparing the data
        }
        DetailsScreen(navigateBack)
    }
}