package com.example.levelup.data.repository.blog

import com.example.levelup.data.model.Blog
import kotlinx.coroutines.delay

interface BlogRepository {
    suspend fun getPosts(): List<Blog>
}

class FakeBlogRepository : BlogRepository {
    override suspend fun getPosts(): List<Blog> {
        delay(500)
        return listOf(
            Blog(
                id = "1",
                title = "Top 5 periféricos gamer 2025",
                excerpt = "Te mostramos los mouse, teclados y headsets más vendidos…",
                content = "Contenido largo del artículo…",
                author = "Equipo Level-Up",
                date = "2025-11-01",
                imageUrl = null,
                externalUrl = "https://example.com/post1"
            ),
            Blog(
                id = "2",
                title = "Cómo elegir tu primera silla gamer",
                excerpt = "Ergonomía, materiales y tips para no equivocarte…",
                content = "Contenido largo del artículo…",
                author = "Sebas G.",
                date = "2025-10-25",
                imageUrl = null,
                externalUrl = "https://example.com/post2"
            ),
            Blog(
                id = "3",
                title = "Eventos y lanzamientos del mes",
                excerpt = "Fechas clave, demos y torneos en Chile.",
                content = "Contenido largo del artículo…",
                author = "Comunidad",
                date = "2025-10-18"
            )
        )
    }
}
