package com.android.bookxpert_android_developer_task.dataClass

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("CPU model") val cpu_model: String?,
    @SerializedName("Capacity") val capacity: String?,
    @SerializedName("Case Size") val case_size: String?,
     @SerializedName("Color") val color: String?,
     @SerializedName("Description") val description: String?,
     @SerializedName("Generation") val generation: String?,
     @SerializedName("Hard disk_size") val hard_disk_size: String?,
     @SerializedName("Price") val price: String?,
     @SerializedName("Screen size") val screen_size: Double?,
     @SerializedName("Strap Colour") val strap_colour: String?,
     @SerializedName("capacity") val capacityAlternate: String?,
     @SerializedName("capacity GB") val capacity_gb: Int?,
     @SerializedName("color") val colorAlternate: String?,
     @SerializedName("generation") val generationAlternate: String?,
     @SerializedName("price") val priceAlternate: Double?,
     @SerializedName("year") val year: Int?
)