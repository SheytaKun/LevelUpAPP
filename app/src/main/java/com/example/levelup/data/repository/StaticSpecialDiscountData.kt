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
            imagenUrl = "https://media.falabella.com/falabellaCL/148258161_01/w=1500,h=1500,fit=pad"
        ),
        Producto(
            codigo = "DS002",
            categoria = "Descuentos especiales",
            nombre = "Auriculares Surround 7.1",
            descripcion = "Audio envolvente 7.1, ideaAuriculares Surround 7.1l para juegos competitivos.",
            precio = 34990,
            stock = 5,
            imagenUrl = "https://todoclick.cl/5431611-large_default/audifonos_gamer_redragon_zeus_x_rgb_multiplataforma_7_1_surround_sound_h510xrgb.jpg"
        ),
        Producto(
            codigo = "DS003",
            categoria = "Descuentos especiales",
            nombre = "Teclado Gamer Low Profile",
            descripcion = "Teclas de bajo perfil, respuesta rápida y diseño minimalista.",
            precio = 25990,
            stock = 10,
            imagenUrl = "https://cl-cenco-pim-resizer.ecomm.cencosud.com/unsafe/adaptive-fit-in/3840x0/filters:quality(75)/prd-cl/product-medias/378c4b80-8654-41fc-932b-e52455772cd4/MKBJ69PH5S/MKBJ69PH5S-1/1700037013304-MKBJ69PH5S-1-1.jpg"
        ),
        Producto(
            codigo = "DS004",
            categoria = "Descuentos especiales",
            nombre = "Mouse Inalámbrico Pro",
            descripcion = "Sensor preciso, baja latencia y batería de larga duración.",
            precio = 19990,
            stock = 12,
            imagenUrl = "https://todoclick.cl/6743028-large_default/mouse-gamer-inalambrico-logitech-pro-2-lightspeed-44000-dpi-negro-ambidiestro.jpg"
        ),
        Producto(
            codigo = "DS005",
            categoria = "Descuentos especiales",
            nombre = "Silla Gamer Compact",
            descripcion = "Silla cómoda y compacta, ideal para espacios pequeños.",
            precio = 119990,
            stock = 3,
            imagenUrl = "https://cl-cenco-pim-resizer.ecomm.cencosud.com/unsafe/adaptive-fit-in/3840x0/filters:quality(75)/prd-cl/product-medias/fb97438e-e383-442c-a030-e9a67855ff22/MKM38GNAE2/MKM38GNAE2-1/1740768773719-MKM38GNAE2-1-0.jpg"
        )
    )
}