package com.rivki.katalogfilm.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rivki.katalogfilm.di.DepsModuleProvider
import com.rivki.katalogfilm.repository.DataRepository
import com.rivki.katalogfilm.ui.FakeRepository
import com.rivki.katalogfilm.ui.home.MovieViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.times

class MovieViewModelTest : KoinTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val viewModel: MovieViewModel by inject()

    @ExperimentalCoroutinesApi
    @get:Rule
    val koinRule = KoinTestRule.create {
        modules(DepsModuleProvider.modules)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }


    @Test
    fun `Should get list now playing movie when success`() {
        runBlocking {
            val service = declareMock<DataRepository> {
                runBlocking {
                    given(getNowPlayingMovie(1)).willReturn(FakeRepository.getDataMovies())
                }
            }
            viewModel.getMovieList()
            val data = viewModel.nowPlayingMovie.value
            val loading = viewModel.observeLoading().value!!
            Mockito.verify(service, times(1)).getNowPlayingMovie(1)
            assertFalse(loading)
            assertNotNull(data)
        }
    }

    @Test
    fun `Should set true isEmpty when now playing movie get empty response`(){
        runBlocking {
            val repository = declareMock<DataRepository> {
                runBlocking {
                    given(getNowPlayingMovie(1)).willReturn(null)
                }
            }
            viewModel.getMovieList()
            val data = viewModel.nowPlayingMovie.value
            Mockito.verify(repository, times(1)).getNowPlayingMovie(1)
            assertNull(data)
        }
    }

    @Test
    fun `Should get list popular movie when success`() {
        runBlocking {
            val service = declareMock<DataRepository> {
                runBlocking {
                    given(getPopularMovie(1)).willReturn(FakeRepository.getDataMovies())
                }
            }
            viewModel.getPopularMovie()
            val data = viewModel.popularMovie.value
            val loading = viewModel.observeLoading().value!!
            Mockito.verify(service, times(1)).getPopularMovie(1)
            assertFalse(loading)
            assertNotNull(data)
        }
    }

    @Test
    fun `Should set true isEmpty when popular movie get empty response`(){
        runBlocking {
            val repository = declareMock<DataRepository> {
                runBlocking {
                    given(getPopularMovie(1)).willReturn(null)
                }
            }
            viewModel.getPopularMovie()
            val data = viewModel.popularMovie.value
            Mockito.verify(repository, times(1)).getPopularMovie(1)
            assertNull(data)
        }
    }

    @Test
    fun `Should get list top rated movie when success`() {
        runBlocking {
            val service = declareMock<DataRepository> {
                runBlocking {
                    given(getTopRatedMovie(1)).willReturn(FakeRepository.getDataMovies())
                }
            }
            viewModel.getTopRatedMovie()
            val data = viewModel.topRatedMovie.value
            val loading = viewModel.observeLoading().value!!
            Mockito.verify(service, times(1)).getTopRatedMovie(1)
            assertFalse(loading)
            assertNotNull(data)
        }
    }

    @Test
    fun `Should set true isEmpty when top rated movie get empty response`(){
        runBlocking {
            val repository = declareMock<DataRepository> {
                runBlocking {
                    given(getTopRatedMovie(1)).willReturn(null)
                }
            }
            viewModel.getTopRatedMovie()
            val data = viewModel.topRatedMovie.value
            Mockito.verify(repository, times(1)).getTopRatedMovie(1)
            assertNull(data)
        }
    }
}