package com.android.bookxpert_android_developer_task.ui.screens.pdfScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

@Composable
fun PdfScreen(
    pdfScreenViewModel: PdfScreenViewModel = hiltViewModel()
) {

    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Remote("https://fssservices.bookxpert.co/GeneratedPDF/Companies/nadc/2024-2025/BalanceSheet.pdf"),
        isZoomEnable = true
    )

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            VerticalPDFReader(
                state = pdfScreenViewModel.pdfVerticallReaderState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Gray)
            )
        }
    }
}