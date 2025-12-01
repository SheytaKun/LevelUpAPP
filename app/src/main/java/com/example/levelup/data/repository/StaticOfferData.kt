package com.example.levelup.data.repository

import com.example.levelup.data.model.Producto

object StaticOfferData {

    // Función para calcular precio original
    fun precioOriginal(precioOferta: Int, descuento: Int): Int {
        return (precioOferta / (1 - descuento / 100f)).toInt()
    }

    // Porcentajes de descuento de cada oferta
    val descuentos = mapOf(
        "OF001" to 15, // 15% OFF
        "OF002" to 20, // 20% OFF
        "OF003" to 25, // 25% OFF
        "OF004" to 25, // 25% OFF
        "OF005" to 30, // 30% OFF
        "OF006" to 20, // 20% OFF
        "OF007" to 35  // 35% OFF
    )

    val offers = listOf(
        Producto(
            codigo = "OF001",
            categoria = "Ofertas",
            nombre = "Pack Accesorios Gamer",
            descripcion = "Mouse + teclado + pad gamer a precio especial.",
            precio = 39990,
            stock = 10,
            imagenUrl = "https://media.solotodo.com/media/products/1386160_picture_1620320881.jpg"
        ),
        Producto(
            codigo = "OF002",
            categoria = "Ofertas",
            nombre = "Headset Gamer RGB",
            descripcion = "Audífonos con micrófono y luces RGB.",
            precio = 24990,
            stock = 6,
            imagenUrl = "https://i.imgur.com/UH5Od4E.jpeg"
        ),
        Producto(
            codigo = "OF003",
            categoria = "Ofertas",
            nombre = "Silla Gamer Económica",
            descripcion = "Modelo cómodo para empezar tu setup.",
            precio = 99990,
            stock = 3,
            imagenUrl = "https://i.imgur.com/0oEUZyA.jpeg"
        ),
        // NUEVOS PRODUCTOS
        Producto(
            codigo = "OF004",
            categoria = "Ofertas",
            nombre = "Mouse Gamer RGB Pro",
            descripcion = "Sensor óptico preciso, 7 botones programables y luces RGB dinámicas.",
            precio = 14990,
            stock = 12,
            imagenUrl = "https://i.imgur.com/fYwq3IU.jpeg"
        ),
        Producto(
            codigo = "OF005",
            categoria = "Ofertas",
            nombre = "Teclado Mecánico Retroiluminado",
            descripcion = "Switches mecánicos, retroiluminación LED y construcción reforzada.",
            precio = 21990,
            stock = 8,
            imagenUrl = "https://i.imgur.com/PJOWb2Z.jpeg"
        ),
        Producto(
            codigo = "OF006",
            categoria = "Ofertas",
            nombre = "Monitor Gamer 24'' 144Hz",
            descripcion = "Pantalla FullHD con alta tasa de refresco ideal para juegos competitivos.",
            precio = 129990,
            stock = 4,
            imagenUrl = "https://i.imgur.com/1pXWl3S.jpeg"
        ),
        Producto(
            codigo = "OF007",
            categoria = "Ofertas",
            nombre = "Silla Gamer Premium Confort",
            descripcion = "Diseño ergonómico, apoyabrazos ajustables y soporte lumbar.",
            precio = 159990,
            stock = 3,
            imagenUrl = "https://i.imgur.com/eB1m4hz.jpeg"
        )
    )
}
