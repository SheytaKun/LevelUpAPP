package com.example.levelup.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.levelup.data.dao.CartDao
import com.example.levelup.data.dao.OrderDao
import com.example.levelup.data.dao.RegionDao
import com.example.levelup.data.dao.UsuarioDao
import com.example.levelup.data.dao.ProductoDao
import com.example.levelup.data.model.UsuarioEntity
import com.example.levelup.data.model.Producto
import com.example.levelup.data.model.CartItem
import com.example.levelup.data.model.OrderEntity
import com.example.levelup.data.model.OrderItemEntity
import com.example.levelup.data.model.RegionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Producto::class,
        UsuarioEntity::class,
        CartItem::class,
        OrderEntity::class,
        OrderItemEntity::class,
        RegionEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class ProductoDataBase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun regionDao(): RegionDao

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
                .addCallback(ProductoDatabaseCallback(context))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class ProductoDatabaseCallback(
            private val context: Context
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateRegions(database.regionDao())
                    }
                }
            }
            
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // Optional: check if empty and populate even on open if needed
                 INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        if (database.regionDao().getRegionCount() == 0) {
                            populateRegions(database.regionDao())
                        }
                    }
                }
            }

            suspend fun populateRegions(regionDao: RegionDao) {
                val regions = listOf(
                    RegionEntity(name = "Arica y Parinacota"),
                    RegionEntity(name = "Tarapacá"),
                    RegionEntity(name = "Antofagasta"),
                    RegionEntity(name = "Atacama"),
                    RegionEntity(name = "Coquimbo"),
                    RegionEntity(name = "Valparaíso"),
                    RegionEntity(name = "Metropolitana de Santiago"),
                    RegionEntity(name = "Libertador General Bernardo O'Higgins"),
                    RegionEntity(name = "Maule"),
                    RegionEntity(name = "Ñuble"),
                    RegionEntity(name = "Biobío"),
                    RegionEntity(name = "La Araucanía"),
                    RegionEntity(name = "Los Ríos"),
                    RegionEntity(name = "Los Lagos"),
                    RegionEntity(name = "Aysén del General Carlos Ibáñez del Campo"),
                    RegionEntity(name = "Magallanes y de la Antártica Chilena")
                )
                regionDao.insertRegions(regions)
            }
        }
    }
}
