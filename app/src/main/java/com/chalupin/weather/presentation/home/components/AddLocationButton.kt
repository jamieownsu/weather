package com.chalupin.weather.presentation.home.components

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

@Composable
fun AddLocationButton(onLocationAdded: (place: Place) -> Unit) {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    val place = Autocomplete.getPlaceFromIntent(data)
                    onLocationAdded(place)
                }
            } else {
                result.data?.let {
                    val status = Autocomplete.getStatusFromIntent(it)
                    Log.e("AddLocationButton", status.statusMessage.toString())
                }
            }
        }

//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.BottomCenter
//    ) {
    FloatingActionButton(
//            modifier = Modifier.padding(16.dp),
        onClick = {
            val fields =
                listOf(
                    Place.Field.ADDRESS,
                    Place.Field.LAT_LNG
                )
            val intent = Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields
            )
                .setTypesFilter(listOf("locality", "administrative_area_level_3"))
                .build(context)
            launcher.launch(intent)
        }) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Location")
        }
//        }
    }
}