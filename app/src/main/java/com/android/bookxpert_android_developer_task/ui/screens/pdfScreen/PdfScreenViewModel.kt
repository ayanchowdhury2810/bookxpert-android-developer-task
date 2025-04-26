package com.android.bookxpert_android_developer_task.ui.screens.pdfScreen

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.android.bookxpert_android_developer_task.ui.Repository
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPdfReaderState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PdfScreenViewModel @Inject constructor(
    private val repository: Repository,
    private val resources: Resources
) : ViewModel() {

    val pdfVerticallReaderState = VerticalPdfReaderState(
        resource = ResourceType.Remote("https://fssservices.bookxpert.co/GeneratedPDF/Companies/nadc/2024-2025/BalanceSheet.pdf"),
        isZoomEnable = true
    )
}