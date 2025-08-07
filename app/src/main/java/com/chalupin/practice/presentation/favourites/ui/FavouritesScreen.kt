package com.chalupin.practice.presentation.favourites.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FavouritesScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
    ) {
        Text(
            "FavouritesScreen",
        )
    }
}