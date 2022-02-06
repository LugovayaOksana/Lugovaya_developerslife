package com.example.developerslife.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.developerslife.domain.model.FeedPost
import com.example.developerslife.presentation.feeds.FeedsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedsScreen(viewModel: FeedsViewModel) {
    val states by viewModel.feedsState.collectAsState()
    if (states.isEmpty()) return

    val pagedItems = states.first().posts.collectAsLazyPagingItems()

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
            state = pagerState,
            count = states.size
        ) { page ->
            FeedView(postsState = pagedItems)
        }
    }
}

@Composable
fun FeedView(postsState: LazyPagingItems<FeedPost>) {
    LazyColumn(content = {
        itemsIndexed(postsState) { index, item ->
            if (item != null) {
                PostView(post = item)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        if (postsState.loadState.append is LoadState.Loading) {

        }
    })
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
                modifier = Modifier.fillMaxWidth().height(200.dp),
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
                    Text(text = "image request failed.")
                })
        }
    }
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