package com.marco.sbab_flickr.navigation

import com.marco.sbab_flickr.util.Constants.DETAILS_SCREEN_ARGUMENT_KEY

/**
 * Represents the screens of the app with their internal navigation route.
 */
sealed class Screen(val route: String) {
    data object Search : Screen(route = "search")
    data object Details : Screen(
        route = "details?$DETAILS_SCREEN_ARGUMENT_KEY=" +
                "{$DETAILS_SCREEN_ARGUMENT_KEY}"
    ) {
        fun passItemId(itemId: String) =
            "details?$DETAILS_SCREEN_ARGUMENT_KEY=$itemId"
    }
}
