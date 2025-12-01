package com.example.levelup.data.repository

import com.example.levelup.data.model.Producto

object StaticProductData {

    val products = listOf(
        Producto(
            codigo = "JM001",
            categoria = "Juegos de Mesa",
            nombre = "Catan",
            descripcion = "Clásico de estrategia para 3-4 jugadores.",
            precio = 29990,
            stock = 15,
            imagenUrl = "https://www.magicsur.cl/25180-large_default/catan-el-juego.jpg"
        ),
        Producto(
            codigo = "JM002",
            categoria = "Juegos de Mesa",
            nombre = "Carcassonne",
            descripcion = "Colocación de losetas, fácil de aprender.",
            precio = 24990,
            stock = 10,
            imagenUrl = "https://dementegames.cl/9316-large_default/carcassonne.jpg"
        ),
        Producto(
            codigo = "AC001",
            categoria = "Accesorios",
            nombre = "Control Xbox Series X",
            descripcion = "Cómodo, botones mapeables, compatible con PC.",
            precio = 59990,
            stock = 8,
            imagenUrl = "https://media.falabella.com/falabellaCL/14618824_4/w=1500,h=1500,fit=cover"
        ),
        Producto(
            codigo = "AC002",
            categoria = "Accesorios",
            nombre = "HyperX Cloud II",
            descripcion = "Sonido envolvente, micrófono desmontable.",
            precio = 79990,
            stock = 12,
            imagenUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQyUiqoo1A-rjBkAOHbSgnbHtavVBbl8Fn71A&s"
        ),
        Producto(
            codigo = "CO001",
            categoria = "Consolas",
            nombre = "PlayStation 5",
            descripcion = "Nueva generación con tiempos de carga ultra rápidos.",
            precio = 549990,
            stock = 5,
            imagenUrl = "https://d16c9dlthokxv6.cloudfront.net/catalog/product/g/s/gs-_sony_ps5_hw_slim_standard_superbundle-_v_2-min.jpg"
        ),
        Producto(
            codigo = "CG001",
            categoria = "Computadores Gamers",
            nombre = "PC ASUS ROG Strix",
            descripcion = "Rendimiento top para gamers exigentes.",
            precio = 1299990,
            stock = 4,
            imagenUrl = "https://dlcdnwebimgs.asus.com/gain/C7178639-F8DD-4267-B9AA-3EB5E99362FE"
        ),
        Producto(
            codigo = "SG001",
            categoria = "Sillas Gamers",
            nombre = "Secretlab Titan",
            descripcion = "Soporte ergonómico personalizable.",
            precio = 349990,
            stock = 7,
            imagenUrl = "https://images.secretlab.co/theme/common/titanevolite_lumbar_curve.jpg"
        ),
        Producto(
            codigo = "MS001",
            categoria = "Mouse",
            nombre = "Logitech G502 HERO",
            descripcion = "Sensor preciso y botones personalizables.",
            precio = 49990,
            stock = 20,
            imagenUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0nz71hN4QfpPz3xyEPCwLZ840O2BizCFZJg&s"
        ),
        Producto(
            codigo = "MP001",
            categoria = "Mousepad",
            nombre = "Razer Goliathus Extended Chroma",
            descripcion = "Superficie amplia con RGB.",
            precio = 29990,
            stock = 18,
            imagenUrl = "https://x-tremesolution.com/cdn/shop/products/Razer_Goliathus_Chroma_1024x1024.png?v=1570940542"
        ),
        Producto(
            codigo = "PP001",
            categoria = "Poleras Personalizadas",
            nombre = "Polera 'Level-Up'",
            descripcion = "Personaliza con tu gamer tag o diseño favorito.",
            precio = 14990,
            stock = 25,
            imagenUrl = "file:///C:/Users/crist/OneDrive/Documentos/GitHub/LevelUpProyect/public/assets/img/poleraLevel.png"
        ),
    )

    fun byCategory(cat: String?): List<Producto> =
        if (cat.isNullOrBlank()) products
        else products.filter { it.categoria.equals(cat, ignoreCase = true) }

    fun search(cat: String?, q: String): List<Producto> =
        byCategory(cat).filter {
            q.isBlank() ||
                    it.nombre.contains(q, ignoreCase = true) ||
                    it.descripcion.contains(q, ignoreCase = true)
        }

    fun findByCode(code: String): Producto? =
        products.find { it.codigo == code }
}
