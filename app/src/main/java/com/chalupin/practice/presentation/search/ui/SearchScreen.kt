package com.chalupin.practice.presentation.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    var active by rememberSaveable { mutableStateOf(false) }

    val allItems = listOf("Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape")

    val filteredItems = allItems.filter {
        it.contains(searchQuery, ignoreCase = true)
    }

    Scaffold { paddingValues ->
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text("Search") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(filteredItems) { item ->
                    ListItem(
                        headlineContent = { Text(item) },
                        modifier = Modifier.clickable {
                            searchQuery = item
                            active = false
                        }
                    )
                }
            }
        }
        if (!active) {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(allItems) { item ->
                    ListItem(
                        headlineContent = { Text(item) }
                    )
                }
            }
        }
    }
}