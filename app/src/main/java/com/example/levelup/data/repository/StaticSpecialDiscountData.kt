package com.example.levelup.data.repository

import com.example.levelup.data.model.Producto

object StaticSpecialDiscountData {

    // Calcula precio original a partir de precio con descuento
    fun precioOriginal(precioOferta: Int, descuento: Int): Int {
        return (precioOferta / (1 - descuento / 100f)).toInt()
    }

    // Porcentaje de descuento por producto
    val descuentos = mapOf(
        "DS001" to 10,  // 10% OFF
        "DS002" to 15,  // 15% OFF
        "DS003" to 20,  // 20% OFF
        "DS004" to 25,  // 25% OFF
        "DS005" to 30   // 30% OFF
    )

    // 5 productos nuevos de "Descuentos especiales"
    val products = listOf(
        Producto(
            codigo = "DS001",
            categoria = "Descuentos especiales",
            nombre = "Pack Starter Gamer",
            descripcion = "Combo de teclado, mouse y pad para iniciar tu setup gamer.",
            precio = 29990,   // precio con descuento
            stock = 8,
            imagenUrl = "https://i.imgur.com/R0LwDeM.jpeg"
        ),
        Producto(
            codigo = "DS002",
            categoria = "Descuentos especiales",
            nombre = "Auriculares Surround 7.1",
            descripcion = "Audio envolvente 7.1, ideal para juegos competitivos.",
            precio = 34990,
            stock = 5,
            imagenUrl = "https://i.imgur.com/0oEUZyA.jpeg"
        ),
        Producto(
            codigo = "DS003",
            categoria = "Descuentos especiales",
            nombre = "Teclado Gamer Low Profile",
            descripcion = "Teclas de bajo perfil, respuesta rápida y diseño minimalista.",
            precio = 25990,
            stock = 10,
            imagenUrl = "https://i.imgur.com/PJOWb2Z.jpeg"
        ),
        Producto(
            codigo = "DS004",
            categoria = "Descuentos especiales",
            nombre = "Mouse Inalámbrico Pro",
            descripcion = "Sensor preciso, baja latencia y batería de larga duración.",
            precio = 19990,
            stock = 12,
            imagenUrl = "https://i.imgur.com/fYwq3IU.jpeg"
        ),
        Producto(
            codigo = "DS005",
            categoria = "Descuentos especiales",
            nombre = "Silla Gamer Compact",
            descripcion = "Silla cómoda y compacta, ideal para espacios pequeños.",
            precio = 119990,
            stock = 3,
            imagenUrl = "https://i.imgur.com/eB1m4hz.jpeg"
        )
    )
}