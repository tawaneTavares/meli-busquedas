package com.tawane.meli.ui.navigation

private const val SEARCH_ROUTE = "search"
private const val PRODUCT_DETAILS_ROUTE = "product_detail"
private const val PRODUCT_DETAILS_ARGUMENT = "product_details"
private const val PRODUCT_DETAILS_WITH_ARGUMENT_ROUTE = "product_detail/{product_details}"

sealed class ScreensDestinations(
    val route: String,
    val routeWithArgs: String = String(),
    val argument: String = String(),
) {
    data object SearchScreenDestination : ScreensDestinations(SEARCH_ROUTE)
    data object ProductDetailsScreenDestination : ScreensDestinations(
        PRODUCT_DETAILS_ROUTE,
        PRODUCT_DETAILS_WITH_ARGUMENT_ROUTE,
        PRODUCT_DETAILS_ARGUMENT,
    )
}
