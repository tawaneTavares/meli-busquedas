package com.tawane.meli.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.squareup.moshi.Moshi
import com.tawane.meli.ui.navigation.ScreensDestinations.SearchScreenDestination

@Composable
fun ProductNavHost(navController: NavHostController, moshi: Moshi) {
    NavHost(
        navController = navController,
        startDestination = SearchScreenDestination.route,
    ) {
        searchScreen(
            onNavigateToProductDetails = { product ->
                navController.navigateToProductDetails(product, moshi = moshi)
            },
        )
        productDetailsScreen(
            onPopBackStack = {
                navController.navigateUp()
            },
            moshi = moshi,
            navigateToProductDetails = { product ->
                navController.navigateToProductDetails(product, moshi = moshi)
            },
        )
    }
}
