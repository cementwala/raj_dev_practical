package com.app.practical.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.practical.model.DeliveryItem


@Dao
interface DeliveryDao {
    /**
     * Insert deliveries
     * @param List of delivery items
     *
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addDelivery(deliveryItemList: List<DeliveryItem>)

    /**
     * used to check the table has records or not
     */
    @Query("SELECT EXISTS(SELECT * FROM delivery)")
    fun isExists(): Boolean

    /**
     * Get the list of deliveries from the database
     */
    @Query("SELECT * FROM delivery")
     fun getDeliveries(): List<DeliveryItem>
}