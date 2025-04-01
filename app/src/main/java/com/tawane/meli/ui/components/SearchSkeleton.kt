package com.tawane.meli.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchSkeleton(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        Box(
            modifier = modifier
                .size(130.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect(),
        )
        Column(
            modifier = modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth(0.8f)
                    .size(height = 20.dp, width = 100.dp)
                    .shimmerEffect(),
            )

            Box(
                modifier = modifier
                    .fillMaxWidth(0.5f)
                    .size(height = 20.dp, width = 100.dp)
                    .shimmerEffect(),
            )

            Box(
                modifier = modifier
                    .fillMaxWidth(0.3f)
                    .size(height = 16.dp, width = 100.dp)
                    .shimmerEffect(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchSkeletonPreview() {
    SearchSkeleton()
}
