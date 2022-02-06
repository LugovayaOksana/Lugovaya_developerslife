package com.example.developerslife.presentation.feeds

import androidx.compose.runtime.Composable

typealias ComposableFun = @Composable () -> Unit

//sealed class TabItem(var title: String, var screen: ComposableFun) {
//    object Latest : TabItem("Последние", { MusicScreen() })
//    object Hot : TabItem( "Лучшие", { MoviesScreen() })
//    object Top : TabItem("Горячие", { BooksScreen() })
//}