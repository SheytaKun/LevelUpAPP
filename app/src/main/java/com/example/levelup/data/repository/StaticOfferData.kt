package com.example.levelup.data.repository

import com.example.levelup.data.model.Producto

object StaticOfferData {

    // Función para calcular precio original
    fun precioOriginal(precioOferta: Int, descuento: Int): Int {
        return (precioOferta / (1 - descuento / 100f)).toInt()
    }

    // Porcentajes de descuento de cada oferta
    val descuentos = mapOf(
        "OF001" to 15,
        "OF002" to 20,
        "OF003" to 25,
        "OF004" to 25,
        "OF005" to 30,
        "OF006" to 20,
        "OF007" to 35
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
            imagenUrl = "https://i5.walmartimages.cl/asr/0c4d76f3-0c20-4470-a76e-929552419382.c59c3679af4915b1e18c9b44861729a3.jpeg?odnHeight=612&odnWidth=612&odnBg=FFFFFF"
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
