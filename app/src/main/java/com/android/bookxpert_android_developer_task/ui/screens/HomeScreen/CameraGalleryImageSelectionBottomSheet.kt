package com.android.bookxpert_android_developer_task.ui.screens.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraGalleryImageSelectionBottomSheet(
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onDismiss: (Boolean) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val interactionSource = remember {
        MutableInteractionSource()
    }

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss(false)
        },
        sheetState = sheetState,
        containerColor = White,
        dragHandle = {

        }) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Select option to upload image",
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                style = TextStyle(
                    color = Black,
                )
            )

            Divider(modifier = Modifier, color = Gray)
            Column(
                modifier = Modifier.background(White)
            ) {
                Text(
                    text = "Capture Image",
                    color = Black,
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 12.dp)
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onCameraClick()
                        }
                )

                Divider(modifier = Modifier, color = Gray)

                Text(
                    text = "Choose image from gallery",
                    color = Black,
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 12.dp)
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onGalleryClick()
                        }
                )
            }
        }
    }

}
