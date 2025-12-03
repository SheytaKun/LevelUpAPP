package com.example.levelup.repository

import com.example.levelup.data.model.NoticiasArticle
import com.example.levelup.data.model.NoticiasResponse
import com.example.levelup.data.remote.RetrofitNoticia
import com.example.levelup.data.repository.NoticiaRepository
import com.example.levelup.remote.NoticiaApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NoticiaRepositoryTest {

    @RelaxedMockK
    lateinit var fakeApi: NoticiaApiService

    private lateinit var repository: NoticiaRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)

        // Mockeamos el objeto RetrofitNoticia para que usemos una API falsa
        mockkObject(RetrofitNoticia)

        // Cuando alguien pida RetrofitNoticia.api, devolvemos nuestro fakeApi
        every { RetrofitNoticia.api } returns fakeApi

        repository = NoticiaRepository()
    }

    @Test
    fun `getGamingNews retorna la lista de articulos de la respuesta`() = runTest {
        // Arrange: simulamos respuesta de la API con dos noticias
        val articulosFalsos = listOf(
            NoticiasArticle(
                title = "Nuevo juego de mundo abierto",
                description = "Un juego enorme llega a la next-gen",
                url = "https://example.com/juego1"
            ),
            NoticiasArticle(
                title = "Torneo de esports anunciado",
                description = "Premio millonario para el campeonato",
                url = "https://example.com/torneo"
            )
        )

        val respuestaFalsa = NoticiasResponse(
            status = "ok",
            totalResults = 2,
            articles = articulosFalsos
        )

        // Ignoramos los parámetros y devolvemos siempre la misma respuesta
        coEvery {
            fakeApi.getGamingNews(
                query = any(),
                language = any(),
                sortBy = any(),
                pageSize = any(),
                domains = any(),
                apiKey = any()
            )
        } returns respuestaFalsa

        // Act
        val resultado = repository.getGamingNews()

        // Assert
        assertEquals(2, resultado.size)
        assertEquals("Nuevo juego de mundo abierto", resultado[0].title)
        assertEquals("Torneo de esports anunciado", resultado[1].title)
    }
}
