package com.app.practical.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "delivery")
data class DeliveryItem(

    var deliveryId:Int,
    @PrimaryKey()
    val id: Int,
    val description: String,
    val imageUrl: String,
    @Embedded
    val location: Location
): Serializable {
    data class Location(
        val address: String,
        val lat: Double,
        val lng: Double
    ) : Serializable
}