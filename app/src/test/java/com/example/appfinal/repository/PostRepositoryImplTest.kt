package com.example.appfinal.repository

import com.example.appfinal.data.local.PostDao
import com.example.appfinal.data.local.PostEntity
import com.example.appfinal.data.remote.dto.PostDto
import com.example.appfinal.network.PostApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostRepositoryImplTest {

    @RelaxedMockK
    private lateinit var postApiService: PostApiService

    @RelaxedMockK
    private lateinit var postDao: PostDao

    private lateinit var repository: PostRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        repository = PostRepositoryImpl(postApiService, postDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getPosts emits from DAO and refresh updates from API`() = runTest(testDispatcher) {
        // ARRANGE (Preparar)
        // 1. Datos locales iniciales en la base de datos (DAO)
        val localPosts = listOf(PostEntity(1, 1, "Local Post", "Content"))
        coEvery { postDao.getAllPosts() } returns flowOf(localPosts)

        // 2. Datos remotos que devolverá la API al refrescar
        val remotePostsDto = listOf(PostDto(2, 2, "Remote Post", "New Content"))
        coEvery { postApiService.getAllPosts() } returns remotePostsDto

        // ACT (Actuar)
        // 1. Obtenemos el flujo de posts. La primera emisión debería ser de los datos locales.
        val postsFlow = repository.getPosts()
        val initialPosts = postsFlow.first() // Recolectamos solo la primera emisión

        // 2. Llamamos a refresh para obtener datos de la API.
        repository.refreshPosts()
        testDispatcher.scheduler.advanceUntilIdle() // Aseguramos que la corrutina de refresh se complete

        // ASSERT (Verificar)
        // 1. Verificamos que los datos iniciales son los que esperábamos del DAO.
        assertEquals(1, initialPosts.size)
        assertEquals("Local Post", initialPosts.first().title)

        // 2. Verificamos que se llamó a `insertAll` en el DAO con los datos de la API,
        // convertidos a entidades de base de datos.
        coVerify(exactly = 1) {
            postDao.insertAll(
                withArg { postEntities ->
                    assertEquals(1, postEntities.size)
                    assertEquals("Remote Post", postEntities.first().title)
                }
            )
        }
    }
}
