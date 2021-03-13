package com.rivki.katalogfilm.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rivki.katalogfilm.di.DepsModuleProvider
import com.rivki.katalogfilm.model.response.MovieResponse
import com.rivki.katalogfilm.repository.DataRepository
import com.rivki.katalogfilm.ui.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mockito
import org.mockito.Mockito.times

class DetailViewModelTest: KoinTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val viewModel: DetailViewModel by inject()

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
    fun `Should get detail movie when success`() {
        runBlocking {
            val repository = declareMock<DataRepository> {
                runBlocking {
                    given(getDetailMovie(5887)).willReturn(FakeRepository.getDetailMovie())
                }
            }
            viewModel.getMovie(5887)
            val data = viewModel.detailMovie.value
            val loading = viewModel.observeLoading().value!!
            Mockito.verify(repository, times(1)).getDetailMovie(5887)
            assertFalse(loading)
            assertNotNull(data)
        }
    }

    @Test
    fun `Should set true when get empty movie response`(){
        runBlocking {
            val repository = declareMock<DataRepository> {
                runBlocking {
                    given(getDetailMovie(5997)).willReturn(null)
                }
            }
            viewModel.getMovie(5887)
            val data = viewModel.detailMovie.value
            verify(repository, times(1)).getDetailMovie(5887)
            assertNull(data)
        }
    }

    @Test
    fun `Should get favorite movie status when success`() {
        runBlocking {
            val repository = declareMock<DataRepository> {
                runBlocking {
                    given(getFavoriteStatus(5887)).willReturn(FakeRepository.getFavoriteStatus(true))
                }
            }
            viewModel.getFavoriteStatus(5887)
            val data = viewModel.favoriteStatus.value
            Mockito.verify(repository, times(1)).getFavoriteStatus(5887)
            data?.let { assertTrue(it) }
        }
    }

    @Test
    fun `Should get favorite movie status when status is false`() {
        runBlocking {
            val repository = declareMock<DataRepository> {
                runBlocking {
                    given(getFavoriteStatus(5887)).willReturn(FakeRepository.getFavoriteStatus(false))
                }
            }
            viewModel.getFavoriteStatus(5887)
            val data = viewModel.favoriteStatus.value
            Mockito.verify(repository, times(1)).getFavoriteStatus(5887)
            data?.let { assertFalse(it) }
        }
    }

    @Test
    fun `Should post favorite movie`() {
        val dataMovie = MovieResponse("", 0, "", "", "", 0.0, "", "", "", 0.0, 0)
        runBlocking {
            val repository = declareMock<DataRepository> {
                runBlocking {
                    given(addToFavorite(dataMovie)).willReturn(any())
                }
            }
            viewModel.addFavorite(dataMovie)
            Mockito.verify(repository, times(1)).addToFavorite(dataMovie)
        }
    }

    @Test
    fun `Should delete favorite movie`() {
        val dataMovie = MovieResponse("", 0, "", "", "", 0.0, "", "", "", 0.0, 0)
        runBlocking {
            val repository = declareMock<DataRepository> {
                runBlocking {
                    given(deleteFavorite(dataMovie)).willReturn(any())
                }
            }
            viewModel.deleteFavorite(dataMovie)
            Mockito.verify(repository, times(1)).deleteFavorite(dataMovie)
        }
    }

    @Test
    fun `Should get review movies when success`() {
        runBlocking {
            val repository = declareMock<DataRepository> {
                runBlocking {
                    given(getReviewMovie(2885)).willReturn(FakeRepository.getDummyReviews())
                }
            }
            viewModel.getReviewList(2885)
            val data = viewModel.listReview.value
            Mockito.verify(repository, times(1)).getReviewMovie(2885)
            assertNotNull(data)
        }
    }

    @Test
    fun `Should get favorite movie status when status is failed`() {
        runBlocking {
            val repository = declareMock<DataRepository> {
                runBlocking {
                    given(getReviewMovie(5887)).willReturn(null)
                }
            }
            viewModel.getReviewList(5887)
            val data = viewModel.listReview.value
            Mockito.verify(repository, times(1)).getReviewMovie(5887)
            assertNull(data)
        }
    }
}