package com.android.bookxpert_android_developer_task.ui.screens.HomeScreen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.android.bookxpert_android_developer_task.BuildConfig
import com.android.bookxpert_android_developer_task.R
import com.rizzi.bouquet.BlackPage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel,
    onPdfClick: () -> Unit,
    onApiClick: () -> Unit,
    onLogoutClick: () -> Unit
) {

    val userData by homeScreenViewModel.userData.collectAsState()

    val isDarkTheme = isSystemInDarkTheme()

    var interactionSource = remember {
        MutableInteractionSource()
    }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var showImagePickerBottomSheet by remember {
        mutableStateOf(false)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(Uri.EMPTY)
    }

    val photoFile = context.createImageFile()
    val uriForFile = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".fileprovider",
        photoFile
    )

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { success ->
                if (success) {
                    imageUri = uriForFile
                } else {
                    Toast.makeText(context, "Failed to capture image", Toast.LENGTH_SHORT).show()
                }
                showImagePickerBottomSheet = false
            })

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
            }
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            cameraLauncher.launch(uriForFile)
        }
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
                    "Home Screen", style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Blue
                    ),
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )

                Row(
                    modifier = Modifier.padding(end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .background(Red, RoundedCornerShape(100.dp))
                            .clickable {
                                onLogoutClick()
                            }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_logout),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(15.dp)
                                .size(15.dp)
                        )
                    }

                    if (userData?.data?.profilePictureUrl != null) {
                        AsyncImage(
                            model = userData?.data?.profilePictureUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(45.dp)
                                .clip(RoundedCornerShape(100.dp))
                        )
                    } else {
                        // Fallback image when profile picture is null
                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .background(Color.Gray)
                        ) {
                            // You can add a default icon or leave it as an empty box
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.Center),
                                tint = Color.White
                            )
                        }
                    }
                }


            }
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .padding(innerpadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (imageUri != Uri.EMPTY) {
                Image(
                    painter = rememberAsyncImagePainter(
                        imageUri
                    ), contentDescription = "Profile",
                    Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(100.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .background(if(isDarkTheme) White else Black, RoundedCornerShape(100.dp))
                        .clip(RoundedCornerShape(100.dp)),
                    tint = if(isDarkTheme) Black else White

                )
            }

            Button(
                onClick = {
                    showImagePickerBottomSheet = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDarkTheme) Yellow else Red
                ),
                modifier = Modifier.padding(bottom = 5.dp)
            ) {
                Text("Add Image", color = if(isDarkTheme) Black else White)
            }

            Button(
                onClick = onPdfClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDarkTheme) Yellow else Red
                ),
                modifier = Modifier.padding(bottom = 5.dp)
            ) {
                Text("PDF Screen", color = if(isDarkTheme) Black else White)
            }

            Button(
                onClick = onApiClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDarkTheme) Yellow else Red
                )
            ) {
                Text("API Screen", color = if(isDarkTheme) Black else White)
            }

        }
    }

    if (showImagePickerBottomSheet) {
        CameraGalleryImageSelectionBottomSheet(
            onCameraClick = {
                val permissionCheckResult =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                    cameraLauncher.launch(uriForFile)
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            onGalleryClick = {
                galleryLauncher.launch("image/*")
                showImagePickerBottomSheet = false
            },
            onDismiss = {
                showImagePickerBottomSheet = false
            }
        )
    }

}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmdd").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, ".jpg", externalCacheDir
    )
    return image
}