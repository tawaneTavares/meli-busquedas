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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tawane.domain.model.Attribute
import com.tawane.domain.model.AttributeValue
import com.tawane.domain.model.DifferentialPricing
import com.tawane.domain.model.Installments
import com.tawane.domain.model.SearchItem
import com.tawane.domain.model.Seller
import com.tawane.domain.model.Shipping
import com.tawane.meli.R
import com.tawane.meli.ui.theme.DarkGreen
import com.tawane.meli.ui.theme.TitleGray
import com.tawane.meli.ui.utils.formatCurrency

@Composable
fun ProductDetailScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit, uiState: ProductDetailUiState) {
    Column(
        modifier = modifier
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(PaddingValues(16.dp)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        uiState.productDetails?.let { item ->
            ProductDetailHeader(modifier = modifier, item = item)

            PriceView(modifier = modifier, price = item.price, installments = item.installments)

            ProductDescription(item = item, modifier = modifier.padding(top = 16.dp))
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
            text = item.title,
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
                contentDescription = "Product Thumbnail", // TODO colocar no string
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
                    text = "${installments.quantity}x ${installments.amount.formatCurrency()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (installments.rate == 0.0) DarkGreen else Color.Black,
                    modifier = modifier.padding(end = 4.dp),
                )
                Text(
                    text = stringResource(R.string.installments_free_interest),
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkGreen,
                )
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
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                    text = item.officialStoreId.toString().uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp,
                        color = Color.Blue,
                    ),
                )
                Spacer(modifier = modifier.width(4.dp))
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

        LazyColumn(
            modifier = modifier.padding(bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(item.attributes.size) { index ->
                item.attributes[index].let { item ->
                    CharacteristicItem(name = item.name, value = item.valueName ?: "")
                }
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

@Preview
@Composable
private fun ProductDetailPreview() {
    val searchItem = SearchItem(
        id = "MLA811601010",
        siteId = "MLA",
        title = "Samsung Galaxy J4+ Dual Sim 32 Gb Negro (2 Gb Ram)",
        seller = Seller(
            id = 451257294,
            nickname = "SELLER_123",
            powerSellerStatus = null,
            carDealer = false,
            realEstateAgency = false,
            tags = emptyList(),
        ),
        price = 19609.0,
        currencyId = "ARS",
        availableQuantity = 1,
        buyingMode = "buy_it_now",
        listingTypeId = "gold_pro",
        stopTime = "2039-08-22T04:00:00.000Z",
        condition = "new",
        permalink = "https://www.mercadolibre.com.ar/p/MLA13550363",
        thumbnail = "http://mla-s1-p.mlstatic.com/943469-MLA31002769183_062019-I.jpg",
        acceptsMercadoPago = true,
        installments = Installments(
            quantity = 6,
            amount = 3268.17,
            rate = 0.0,
            currencyId = "ARS",
        ),
        shipping = Shipping(
            freeShipping = true,
            mode = "me2",
            tags = listOf("mandatory_free_shipping"),
            logisticType = "drop_off",
            storePickUp = false,
        ),
        attributes = listOf(
            Attribute(
                id = "BRAND",
                name = "Marca",
                valueId = "206",
                valueName = "Samsung",
                valueStruct = null,
                values = listOf(
                    AttributeValue(
                        id = "206",
                        name = "Samsung",
                        struct = null,
                        source = 1,
                    ),
                ),
                source = 1,
                attributeGroupId = "OTHERS",
                attributeGroupName = "Otros",
            ),
            Attribute(
                id = "CPU_MODEL",
                name = "Modelo de CPU",
                valueId = "7657686",
                valueName = "4x1.4 GHz Cortex-A53",
                valueStruct = null,
                values = listOf(
                    AttributeValue(
                        id = "7657686",
                        name = "4x1.4 GHz Cortex-A53",
                        struct = null,
                        source = 6587939990796619,
                    ),
                ),
                source = 6587939990796619,
                attributeGroupId = "OTHERS",
                attributeGroupName = "Otros",
            ),
            Attribute(
                id = "GPU_MODEL",
                name = "Modelo de GPU",
                valueId = "7531831",
                valueName = "Adreno 308",
                valueStruct = null,
                values = listOf(
                    AttributeValue(
                        id = "7531831",
                        name = "Adreno 308",
                        struct = null,
                        source = 4709228965570453,
                    ),
                ),
                source = 4709228965570453,
                attributeGroupId = "OTHERS",
                attributeGroupName = "Otros",
            ),
            Attribute(
                id = "ITEM_CONDITION",
                name = "Condición del ítem",
                valueId = "2230284",
                valueName = "Nuevo",
                valueStruct = null,
                values = listOf(
                    AttributeValue(
                        id = "2230284",
                        name = "Nuevo",
                        struct = null,
                        source = 8342579661593500,
                    ),
                ),
                source = 8342579661593500,
                attributeGroupId = "OTHERS",
                attributeGroupName = "Otros",
            ),
            Attribute(
                id = "LINE",
                name = "Línea",
                valueId = "195973",
                valueName = "Galaxy J",
                valueStruct = null,
                values = listOf(
                    AttributeValue(
                        id = "195973",
                        name = "Galaxy J",
                        struct = null,
                        source = 1,
                    ),
                ),
                source = 1,
                attributeGroupId = "OTHERS",
                attributeGroupName = "Otros",
            ),
            Attribute(
                id = "MODEL",
                name = "Modelo",
                valueId = "6047739",
                valueName = "J4+ Duos",
                valueStruct = null,
                values = listOf(
                    AttributeValue(
                        id = "6047739",
                        name = "J4+ Duos",
                        struct = null,
                        source = 1,
                    ),
                ),
                source = 1,
                attributeGroupId = "OTHERS",
                attributeGroupName = "Otros",
            ),
            Attribute(
                id = "PROCESSOR_MODEL",
                name = "Modelo del procesador",
                valueId = "2087879",
                valueName = "Snapdragon 425",
                valueStruct = null,
                values = listOf(
                    AttributeValue(
                        id = "2087879",
                        name = "Snapdragon 425",
                        struct = null,
                        source = 6587939990796619,
                    ),
                ),
                source = 6587939990796619,
                attributeGroupId = "OTHERS",
                attributeGroupName = "Otros",
            ),
        ),
        originalPrice = null,
        categoryId = "MLA1055",
        officialStoreId = null,
        catalogProductId = "MLA13550363",
        tags = listOf(
            "extended_warranty_eligible",
            "good_quality_picture",
            "good_quality_thumbnail",
            "immediate_payment",
            "cart_eligible",
        ),
        catalogListing = true,
        address = null,
        sellerAddress = null,
        differentialPricing = DifferentialPricing(id = 33669181),
    )

    ProductDetailScreen(
        uiState = ProductDetailUiState(productDetails = searchItem),
        onBackClick = {},
    )
}
