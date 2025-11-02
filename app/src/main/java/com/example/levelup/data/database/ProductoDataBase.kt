package com.example.levelup.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.levelup.data.dao.ProductoDao
import com.example.levelup.data.model.Producto

@Database(
    entities =[Producto::class],
    version = 1,
    exportSchema =false
)

abstract class ProductoDataBase: RoomDatabase() {
    abstract fun ProductoDao(): ProductoDao

    companion object{
        @Volatile
        private var INSTANCE: ProductoDataBase? = null

        fun getDataBase(context: Context): ProductoDataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDataBase::class.java,
                    "producto_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}