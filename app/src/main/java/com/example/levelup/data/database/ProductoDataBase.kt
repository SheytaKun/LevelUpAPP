package com.example.levelup.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.levelup.data.dao.CartDao
import com.example.levelup.data.dao.UsuarioDao
import com.example.levelup.data.dao.ProductoDao
import com.example.levelup.data.model.UsuarioEntity
import com.example.levelup.data.model.Producto
import com.example.levelup.data.model.CartItem

@Database(
    entities = [
        Producto::class,
        UsuarioEntity::class,
        CartItem::class
    ],
    version = 3,
    exportSchema = false
)
abstract class ProductoDataBase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: ProductoDataBase? = null

        fun getDataBase(context: Context): ProductoDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDataBase::class.java,
                    "producto_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
