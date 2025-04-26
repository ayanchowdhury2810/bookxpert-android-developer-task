package com.android.bookxpert_android_developer_task.ui.screens.apiScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.bookxpert_android_developer_task.R
import com.android.bookxpert_android_developer_task.dataClass.ApiDataItem
import com.android.bookxpert_android_developer_task.retrofit.Status
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ApiScreen(
    apiScreenViewModel: ApiScreenViewModel,
) {

    var showAddDialog by remember { mutableStateOf(false) }
    var clickedItem by remember {
        mutableStateOf(ApiDataItem(data = null, id = null, name = null, itemId = 0))
//        mutableStateOf("")
    }
//    var editedText by remember {
//        mutableStateOf("")
//    }
    var isEditButtonClicked by remember {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()
    val allSpecsData: State<List<ApiDataItem>> = apiScreenViewModel.allSpecsData.collectAsState()
    val apiDataResponse by apiScreenViewModel.apiResult.observeAsState()


    LaunchedEffect(Unit) {
        apiScreenViewModel.getApiData()
    }

    when (apiDataResponse?.status) {
        Status.SUCCESS -> {
            Log.d("HomeScreen", "result-> ${apiDataResponse?.data}")
        }

        Status.ERROR -> {
            Log.d("HomeScreen", "Something went wrong")

        }

        Status.LOADING -> {
            Log.d("HomeScreen", "Loading")
        }

        null -> {}
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(top = 40.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Api Screen", style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Blue
                    ),
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
            contentAlignment = Alignment.Center
        ) {
            if (apiDataResponse?.status == Status.LOADING) {
                CircularProgressIndicator()
            }

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                items(allSpecsData.value) { item ->
                    DatabaseItem(
                        item = item,
                        onUpdate = {
                            clickedItem = item
                            isEditButtonClicked = true
                        },
                        onDelete = {
                            coroutineScope.launch(Dispatchers.IO) {
                                apiScreenViewModel.deleteSpecsData(item)
                            }
                        })
                    Divider()
                }
            }
        }
    }

    if (isEditButtonClicked) {
        LaunchedEffect(Unit) {
            apiScreenViewModel.updateEditItem(clickedItem.name!!)
            showAddDialog = true
        }
        isEditButtonClicked = false
    }

    if (showAddDialog) {
        val editItem by apiScreenViewModel.editItem.observeAsState()

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add New Reminder") },
            text = {
                if (editItem != null) {
                    OutlinedTextField(
                        value = editItem.toString(),
                        onValueChange = { apiScreenViewModel.updateEditItem(it) },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editItem?.isNotBlank() == true) {
                            coroutineScope.launch(Dispatchers.IO) {
                                apiScreenViewModel.updateSpecsData(
                                    ApiDataItem(
                                        data = clickedItem.data,
                                        id = clickedItem.id,
                                        name = editItem,
                                        itemId = clickedItem.itemId
                                    )
                                )
                            }
                            showAddDialog = false
                        }
                    }
                ) {
                    Text("Edit")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showAddDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun DatabaseItem(
    item: ApiDataItem,
    onUpdate: (ApiDataItem) -> Unit,
    onDelete: (ApiDataItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (!item.name.isNullOrEmpty()) {
                Text(
                    text = item.name!!,
                    style = TextStyle(
                        color = Black
                    )
                )
            }
        }

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { onUpdate(item) }) {
                Image(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(5.dp))

            IconButton(onClick = { onDelete(item) }) {
                Text("✖")
            }
        }
    }
}