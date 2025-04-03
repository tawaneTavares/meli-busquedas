package com.tawane.meli.ui.search

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tawane.domain.model.Installments
import com.tawane.domain.model.SearchItem
import com.tawane.meli.R
import com.tawane.meli.ui.components.SearchSkeleton
import com.tawane.meli.ui.components.SkeletonList
import com.tawane.meli.ui.theme.DarkGreen
import com.tawane.meli.ui.theme.PrimaryColor
import com.tawane.meli.ui.theme.TitleGray
import com.tawane.meli.ui.utils.formatCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchUiState,
    onQueryChange: (String, String) -> Unit = { _, _ -> },
    onItemClick: (SearchItem) -> Unit = {},
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
                .height(106.dp)
                .background(color = PrimaryColor)
                .padding(top = 16.dp),
        ) {
            SearchBar(
                query = uiState.query,
                onQueryChange = {
                    onQueryChange(
                        it,
                        // TODO trecho de código pode ser removido após correção da api
                        String(
                            context.resources.openRawResource(R.raw.search_items_response).readBytes(),
                        ),
                    )
                },
                onSearch = {},
                active = false,
                onActiveChange = {},
                windowInsets = WindowInsets(top = 0),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = SearchBarColors(
                    containerColor = Color.White,
                    dividerColor = Color.White,
                ),
                placeholder = { Text(text = stringResource(R.string.search_hint)) },
            ) {}

            if (uiState.showMinimumCharactersMessage) {
                Text(
                    text = stringResource(R.string.minimum_characters_message),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = modifier.padding(horizontal = 26.dp, vertical = 4.dp),
                )
            }
        }

        if (uiState.searchResults == null) {
            EmptyState(modifier)
        }

        Column(modifier = modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = uiState.searchResults != null,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 }),
            ) {
                var showError by remember { mutableStateOf(false) }

                uiState.searchResults?.collectAsLazyPagingItems()?.let { itemsPaging ->
                    val localContext = LocalContext.current
                    LaunchedEffect(key1 = itemsPaging.loadState) {
                        if (itemsPaging.loadState.refresh is LoadState.Error) {
                            Toast.makeText(
                                localContext,
                                "Error: " + (itemsPaging.loadState.refresh as LoadState.Error).error.message,
                                Toast.LENGTH_LONG,
                            ).show()

                            showError = true
                        } else {
                            showError = false
                        }
                    }

                    if (showError) {
                        ErrorState(modifier)
                    } else {
                        LoadStates(itemsPaging) {
                            LazyColumn(
                                modifier = modifier
                                    .fillMaxSize()
                                    .padding(top = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(1.dp),
                            ) {
                                items(itemsPaging.itemCount) { index ->
                                    itemsPaging[index]?.let { item ->
                                        SearchResultItemView(item = item, onItemClick = onItemClick)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadStates(paging: LazyPagingItems<SearchItem>, productView: @Composable () -> Unit) {
    when (paging.loadState.refresh) {
        is LoadState.Loading -> {
            SkeletonList { SearchSkeleton() }
        }

        is LoadState.NotLoading -> productView()
        is LoadState.Error -> ErrorState()
    }
}

@Composable
fun SearchResultItemView(modifier: Modifier = Modifier, item: SearchItem, onItemClick: (SearchItem) -> Unit = {}) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .background(color = Color.White),
        colors = CardColors(
            disabledContainerColor = Color.White,
            containerColor = Color.White,
            disabledContentColor = Color.Black,
            contentColor = Color.Black,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(0.dp),
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.thumbnail)
                    .build(),
                contentDescription = "Product Thumbnail", // TODO colocar no string
                placeholder = painterResource(R.drawable.ic_empty_image),
                error = painterResource(R.drawable.ic_empty_image),
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .size(130.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(4.dp)),
            )

            Column(
                modifier = modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                TitleView(item = item)

                PriceView(price = item.price ?: 0.0, installments = item.installments)

                if (item.shipping?.freeShipping == true) {
                    Text(
                        text = stringResource(R.string.free_shipping),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkGreen,
                    )
                }
            }
        }
    }
}

@Composable
private fun TitleView(modifier: Modifier = Modifier, item: SearchItem) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = item.title ?: "",
            style = MaterialTheme.typography.titleSmall,
            color = TitleGray,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )

        if (item.officialStoreId != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.by_store, item.officialStoreId ?: ""),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray,
                )
                Spacer(modifier = modifier.width(4.dp))
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_checked_store),
                    contentDescription = "Official Store Seal",
                    modifier = modifier.size(16.dp),
                )
            }
        }
    }
}

@Composable
private fun PriceView(modifier: Modifier = Modifier, price: Double, installments: Installments?) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = price.formatCurrency(),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
        )

        if (installments != null && installments.quantity > 0) {
            Row {
                Text(
                    text = stringResource(R.string.installments_in),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    modifier = modifier.padding(end = 4.dp),
                )
                Text(
                    text = "${installments.quantity}x ${installments.amount?.formatCurrency()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (installments.rate == 0.0) DarkGreen else Color.Black,
                    modifier = modifier.padding(end = 4.dp),
                )
                if (installments.rate == 0.0) {
                    Text(
                        text = stringResource(R.string.installments_free_interest),
                        style = MaterialTheme.typography.bodyMedium,
                        color = DarkGreen,
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorState(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_alert_error),
            contentDescription = null,
            modifier = modifier
                .size(100.dp),
            contentScale = ContentScale.Fit,
        )
        Text(text = stringResource(R.string.error_message))
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_search_icon),
            contentDescription = null,
            modifier = modifier
                .size(100.dp),
            contentScale = ContentScale.Fit,
        )
        Text(
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            text = stringResource(R.string.empty_message),
        )
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(uiState = SearchUiState())
}
