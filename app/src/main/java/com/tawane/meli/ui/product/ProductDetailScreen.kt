package com.tawane.meli.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tawane.domain.model.Installments
import com.tawane.domain.model.SearchItem
import com.tawane.meli.R
import com.tawane.meli.ui.theme.DarkGreen
import com.tawane.meli.ui.theme.PrimaryColor
import com.tawane.meli.ui.theme.TitleGray
import com.tawane.meli.ui.utils.formatCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    uiState: ProductDetailUiState,
    onItemClick: (SearchItem) -> Unit = {},
) {
    Column {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = PrimaryColor,
                titleContentColor = Color.Black,
            ),
            modifier = modifier.background(PrimaryColor),
            navigationIcon = {
                IconButton(
                    onClick = { onBackClick() },
                    colors = IconButtonColors(
                        containerColor = PrimaryColor,
                        contentColor = Color.Black,
                        disabledContainerColor = PrimaryColor,
                        disabledContentColor = Color.Black,
                    ),
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        "backIcon",
                    )
                }
            },
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(PaddingValues(16.dp)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            uiState.productDetails?.let { item ->
                ProductDetailHeader(modifier = modifier, item = item)

                PriceView(modifier = modifier, price = item.price ?: 0.0, installments = item.installments)

                ProductDescription(item = item, modifier = modifier.padding(top = 16.dp))
            }

            uiState.lastViewedItems?.let { item ->
                HorizontalCardList(items = item, modifier = modifier.padding(top = 16.dp), onItemClick = onItemClick)
            }
        }
    }
}

@Composable
fun ProductDetailHeader(modifier: Modifier = Modifier, item: SearchItem) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = item.title ?: "",
            style = MaterialTheme.typography.titleMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = TitleGray,
        )

        Box(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.thumbnail)
                    .placeholder(R.drawable.ic_empty_image)
                    .error(R.drawable.ic_empty_image)
                    .build(),
                contentDescription = stringResource(R.string.product_thumbnail_description),
                placeholder = painterResource(R.drawable.ic_empty_image),
                error = painterResource(R.drawable.ic_empty_image),
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(4.dp)),
            )
        }
    }
}

@Composable
private fun PriceView(modifier: Modifier = Modifier, price: Double, installments: Installments?) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = price.formatCurrency(),
            style = MaterialTheme.typography.headlineMedium,
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
                if ((installments.rate == 0.0)) {
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
fun ProductDescription(modifier: Modifier = Modifier, item: SearchItem) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        if (item.officialStoreId != null) {
            Row {
                Text(
                    modifier = modifier.padding(end = 4.dp),
                    text = stringResource(R.string.official_store),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp,
                        color = Color.Gray,
                    ),
                )
                Text(
                    text = item.officialStoreName.toString().uppercase(),
                    modifier = modifier.padding(end = 4.dp),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp,
                        color = Color.Blue,
                    ),
                )
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_checked_store),
                    contentDescription = "Official Store Seal",
                    modifier = modifier.size(16.dp),
                )
            }
        }
        Text(
            text = stringResource(R.string.characteristics),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
        )

        Column(
            modifier = modifier.padding(bottom = 5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item.attributes.forEach { attribute ->
                CharacteristicItem(name = attribute.name ?: "", value = attribute.valueName ?: "")
            }
        }
    }
}

@Composable
private fun CharacteristicItem(name: String, value: String) {
    Column {
        Text(
            text = name.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            ),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
        )
    }
}

@Composable
fun HorizontalCardList(modifier: Modifier = Modifier, items: List<SearchItem>, onItemClick: (SearchItem) -> Unit = {}) {
    Column {
        Text(
            text = stringResource(R.string.last_viewed),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            fontSize = 20.sp,
            modifier = modifier.padding(start = 7.dp, top = 10.dp),
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 5.dp),
        ) {
            items(items) { item ->
                HorizontalCard(modifier = modifier, item = item, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
fun HorizontalCard(modifier: Modifier = Modifier, item: SearchItem, onItemClick: (SearchItem) -> Unit = {}) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(140.dp)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardColors(
            disabledContainerColor = Color.White,
            containerColor = Color.White,
            disabledContentColor = Color.Black,
            contentColor = Color.Black,
        ),
        onClick = { onItemClick(item) },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.thumbnail)
                    .placeholder(R.drawable.ic_empty_image)
                    .error(R.drawable.ic_empty_image)
                    .build(),
                contentDescription = stringResource(R.string.product_thumbnail_description),
                placeholder = painterResource(R.drawable.ic_empty_image),
                error = painterResource(R.drawable.ic_empty_image),
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .size(64.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title ?: "",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
