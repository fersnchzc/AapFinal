package com.example.appfinal.ui.views.postlist


import com.example.appfinal.domain.Post
import com.example.appfinal.repository.PostRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filter
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
class PostListViewModelTest {

    @RelaxedMockK
    private lateinit var postRepository: PostRepository

    private val testDispatcher = StandardTestDispatcher()

    // La instancia del ViewModel se creará dentro de cada test para un mejor control.
    // private lateinit var viewModel: PostListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init viewmodel with success should update uiState from loading to not loading`() = runTest(testDispatcher) {
        // ARRANGE
        coEvery { postRepository.refreshPosts() } returns Unit
        coEvery { postRepository.getPosts() } returns flowOf(emptyList())

        // ACT
        val viewModel = PostListViewModel(postRepository)

        // Avanzamos el dispatcher para que la corrutina del `init` se complete.
        testDispatcher.scheduler.advanceUntilIdle()

        // ASSERT
        // Verificamos el estado final: no está cargando y no hay error.
        assertEquals(false, viewModel.uiState.value.isLoading)
        assertNull(viewModel.uiState.value.errorMessage)

        // Verificamos que se llamó a `refreshPosts` exactamente una vez.
        coVerify(exactly = 1) { postRepository.refreshPosts() }
    }
    @Test
    fun `init viewmodel with network failure should show error message`() = runTest(testDispatcher) {
        // ARRANGE
        val errorMessage = "No hay conexión"
        coEvery { postRepository.refreshPosts() } throws Exception(errorMessage)
        coEvery { postRepository.getPosts() } returns flowOf(emptyList())

        // ACT
        val viewModel = PostListViewModel(postRepository)

        // Avanzamos el dispatcher
        testDispatcher.scheduler.advanceUntilIdle()

        // ASSERT
        assertEquals(false, viewModel.uiState.value.isLoading)
        assertEquals("Mostrando contenido sin conexión.", viewModel.uiState.value.errorMessage)

        coVerify(exactly = 1) { postRepository.refreshPosts() }
    }

    @Test
    fun `posts StateFlow should emit posts from repository`() = runTest(testDispatcher) {
        // ARRANGE
        val fakePosts = listOf(Post(id = 1, userId = 1, title = "Test Post", body = ""))
        coEvery { postRepository.getPosts() } returns flowOf(fakePosts)
        // Mockeamos también la llamada a refresh() del bloque init para que el test sea más robusto
        coEvery { postRepository.refreshPosts() } returns Unit

        // ACT
        val viewModel = PostListViewModel(postRepository)

        // Para probar un flujo con SharingStarted.WhileSubscribed, debemos coleccionarlo activamente.
        // Aquí, coleccionamos el primer elemento que no esté vacío para asegurarnos de que hemos recibido los datos.
        val actualPosts = viewModel.posts.filter { it.isNotEmpty() }.first()

        // ASSERT
        assertEquals(fakePosts, actualPosts)
        assertEquals(1, actualPosts.size)
        assertEquals("Test Post", actualPosts.first().title)
    }
}
