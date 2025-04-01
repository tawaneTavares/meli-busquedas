package com.tawane.meli.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize

@Composable
fun SkeletonList(skeletonContent: @Composable () -> Unit) {
    LazyColumn {
        items(20) {
            skeletonContent()
        }
    }
}

fun Modifier.shimmerEffect(shape: Shape = RectangleShape): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "InfiniteTransition")
    val width = size.width.toFloat()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * width,
        targetValue = 2 * width,
        animationSpec = infiniteRepeatable(
            animation = tween(1400),
        ),
        label = "shimmer",
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.LightGray.copy(alpha = 0.6f),
                Color.LightGray.copy(alpha = 0.2f),
                Color.LightGray.copy(alpha = 0.6f),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat()),
        ),
        shape = shape,
    )
        .onGloballyPositioned {
            size = it.size
        }
}

@Preview(
    showBackground = true,
)
@Composable
fun SkeletonListPreview() {
    SkeletonList { SearchSkeleton() }
}
