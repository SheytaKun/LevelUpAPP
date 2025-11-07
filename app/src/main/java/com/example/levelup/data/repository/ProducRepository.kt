package com.example.levelup.data

import com.example.levelup.data.model.Producto

object ProductRepository {

    val products = listOf(
        Producto(
            codigo = "JM001",
            categoria = "Juegos de Mesa",
            nombre = "Catan",
            descripcion = "Clásico de estrategia para 3-4 jugadores.",
            precio = 29990,
            stock = 15
        ),
        Producto(
            codigo = "JM002",
            categoria = "Juegos de Mesa",
            nombre = "Carcassonne",
            descripcion = "Colocación de losetas, fácil de aprender.",
            precio = 24990,
            stock = 10
        ),
        Producto(
            codigo = "AC001",
            categoria = "Accesorios",
            nombre = "Control Xbox Series X",
            descripcion = "Cómodo, botones mapeables, compatible con PC.",
            precio = 59990,
            stock = 8
        ),
        Producto(
            codigo = "AC002",
            categoria = "Accesorios",
            nombre = "HyperX Cloud II",
            descripcion = "Sonido envolvente, micrófono desmontable.",
            precio = 79990,
            stock = 12
        ),
        Producto(
            codigo = "CO001",
            categoria = "Consolas",
            nombre = "PlayStation 5",
            descripcion = "Nueva generación con tiempos de carga ultra rápidos.",
            precio = 549990,
            stock = 5
        ),
        Producto(
            codigo = "CG001",
            categoria = "Computadores Gamers",
            nombre = "PC ASUS ROG Strix",
            descripcion = "Rendimiento top para gamers exigentes.",
            precio = 1299990,
            stock = 4
        ),
        Producto(
            codigo = "SG001",
            categoria = "Sillas Gamers",
            nombre = "Secretlab Titan",
            descripcion = "Soporte ergonómico personalizable.",
            precio = 349990,
            stock = 7
        ),
        Producto(
            codigo = "MS001",
            categoria = "Mouse",
            nombre = "Logitech G502 HERO",
            descripcion = "Sensor preciso y botones personalizables.",
            precio = 49990,
            stock = 20
        ),
        Producto(
            codigo = "MP001",
            categoria = "Mousepad",
            nombre = "Razer Goliathus Extended Chroma",
            descripcion = "Superficie amplia con RGB.",
            precio = 29990,
            stock = 18
        ),
        Producto(
            codigo = "PP001",
            categoria = "Poleras Personalizadas",
            nombre = "Polera 'Level-Up'",
            descripcion = "Personaliza con tu gamer tag o diseño favorito.",
            precio = 14990,
            stock = 25
        )
    )
    fun byCategory(cat: String?): List<Producto> =
        if (cat.isNullOrBlank()) products
        else products.filter { it.categoria.equals(cat, ignoreCase = true) }

    fun search(cat: String?, q: String): List<Producto> =
        byCategory(cat).filter {
            q.isBlank() || it.nombre.contains(q, ignoreCase = true) || it.descripcion.contains(q, ignoreCase = true)
        }

    fun findByCode(code: String): Producto? =
        products.find { it.codigo == code }
}
