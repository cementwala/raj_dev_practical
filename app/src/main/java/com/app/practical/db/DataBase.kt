package com.app.practical.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.practical.model.DeliveryItem


@Database(entities = [DeliveryItem::class], version = 2)
abstract class DeliveryDatabase : RoomDatabase() {
    abstract fun deliveryDao(): DeliveryDao

    companion object {
        @Volatile
        private var INSTANCE: DeliveryDatabase? = null

        fun getDatabase(context: Context): DeliveryDatabase {
            if (INSTANCE == null) {

                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context, DeliveryDatabase::class.java, "deliveryDB")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}