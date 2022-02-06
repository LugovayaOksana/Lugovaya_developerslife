package com.example.developerslife.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.*
import com.example.developerslife.R
import com.example.developerslife.domain.model.FeedPost
import com.example.developerslife.presentation.feeds.FeedsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedsScreen(viewModel: FeedsViewModel) {
    val states by viewModel.feedsState.collectAsState()
    if (states.isEmpty()) return

    val pagedItems = states.map {
        it.posts.collectAsLazyPagingItems()
    }

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = Color.LightGray,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },

            ) {
            states.forEachIndexed { index, feed ->
                Tab(
                    text = {
                        Text(text = feed.title, color = Color.White)
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            count = states.size
        ) { page ->
            FeedView(postsState = pagedItems[page])
        }
    }
}

@Composable
fun FeedView(postsState: LazyPagingItems<FeedPost>) {
    val isRefreshing = postsState.loadState.refresh is LoadState.Loading

    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { postsState.refresh() },
    ) {

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(postsState) { index, item ->
                    if (item != null) {
                        PostView(post = item)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }

            postsState.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { CircularProgressIndicator() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        item {
                            FeedError(modifier = Modifier.fillParentMaxHeight()) { retry() }
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        item {
                            FeedError(modifier = Modifier.fillParentMaxWidth()) { retry() }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeedError(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.text_error)
        )
        Button(onClick = onRetry) {
            Text(text = "ПОВТОРИТЬ")
        }
    }
}

@Composable
fun PostView(post: FeedPost) {
    Card(
        modifier = Modifier.fillMaxSize(),
        elevation = 5.dp,
    ) {
        Column {
            Text(
                text = post.description,
                color = Color.Black,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                imageModel = post.gifURL,
                // shows a progress indicator when loading an image.
                loading = {
                    ConstraintLayout(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val indicator = createRef()
                        CircularProgressIndicator(
                            modifier = Modifier.constrainAs(indicator) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                        )
                    }
                },
                // shows an error text message when request failed.
                failure = {
//                    Text(text = "image request failed.")
                    Image(painter = painterResource(id = R.drawable.gif), contentDescription = "")
                })
        }
    }
}

@Composable
fun InfiniteAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    val heartSize by infiniteTransition.animateFloat(
        initialValue = 100.0f,
        targetValue = 250.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, delayMillis = 100, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
//    Image(
//        painter = painterResource(R.drawable.
//        heart),
//        contentDescription = "heart",
//        modifier = Modifier
//            .size(heartSize.dp)
//    )
}

@Composable
private fun Hearts() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.hearts))
    val colors = remember {
        listOf(
            Color.Red,
            Color.Green,
            Color.Blue,
            Color.Yellow,
        )
    }
    var colorIndex by remember { mutableStateOf(0) }
    val color by derivedStateOf { colors[colorIndex] }
    val blurRadius = with(LocalDensity.current) { 12.dp.toPx() }

    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = color.toArgb(),
            keyPath = arrayOf(
                "H2",
                "Shape 1",
                "Fill 1",
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.BLUR_RADIUS,
            value = blurRadius,
            keyPath = arrayOf(
                "**",
                "Stroke 1",
            )
        ),
    )
    LottieAnimation(
        composition,
        iterations = LottieConstants.IterateForever,
        dynamicProperties = dynamicProperties,
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { colorIndex = (colorIndex + 1) % colors.size },
            )
    )
}

/*
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerWithOffsetTransition() {

    HorizontalPager(
        count = 3,
        // Add 32.dp horizontal padding to 'center' the pages
        contentPadding = PaddingValues(horizontal = 32.dp),
        modifier = Modifier.fillMaxSize()
    ) { page ->
        Card(
            Modifier
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    // We animate the scaleX + scaleY, between 85% and 100%
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            Box {

            }
        }
    }
}
*/

@Preview
@Composable
fun PreviewFeedsScreen() {
}