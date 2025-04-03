package com.tawane.meli.ui.navigation

import android.net.Uri
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.squareup.moshi.Moshi
import com.tawane.domain.model.SearchItem
import com.tawane.meli.ui.navigation.ScreensDestinations.ProductDetailsScreenDestination
import com.tawane.meli.ui.product.ProductDetailScreen
import com.tawane.meli.ui.product.ProductDetailViewModel
import timber.log.Timber

fun NavGraphBuilder.productDetailsScreen(onPopBackStack: () -> Unit, moshi: Moshi) {
    composable(
        route = ProductDetailsScreenDestination.routeWithArgs,
        arguments = listOf(
            navArgument(ProductDetailsScreenDestination.argument) {
                defaultValue = ""
                type = NavType.StringType
            },
        ),
    ) { backStackEntry ->
        backStackEntry.arguments?.getString(ProductDetailsScreenDestination.argument)?.let { json ->
            val decodedJson = Uri.decode(json)
            val productItem = moshi.adapter(SearchItem::class.java).fromJson(decodedJson)
            Timber.d("product: ${productItem?.title}")

            val viewModel = hiltViewModel<ProductDetailViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            LaunchedEffect(Unit) {
                productItem?.let { viewModel.applyProductDetail(it) }
                viewModel.getLastViewedItems()
            }

            ProductDetailScreen(
                onBackClick = {
                    productItem?.let { viewModel.saveLastViewedItem(it) }
                    onPopBackStack()
                },
                uiState = uiState,
            )
        } ?: LaunchedEffect(Unit) {
            onPopBackStack()
        }
    }
}

fun NavController.navigateToProductDetails(product: SearchItem, navOptions: NavOptions? = null, moshi: Moshi) {
    val productJson = moshi.adapter(SearchItem::class.java).toJson(product)
    Timber.d("productJson: $productJson")
    val encodedJson = Uri.encode(productJson)
    navigate("${ProductDetailsScreenDestination.route}/$encodedJson", navOptions)
}
