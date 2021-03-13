package com.rivki.katalogfilm.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rivki.katalogfilm.di.DepsModuleProvider
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
import org.koin.test.inject
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.times

class FavoriteViewModelTest: KoinTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val viewModel: FavoriteViewModel by inject()

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
    fun `Should get list favorite movie when success`() {
        runBlocking {
            val service = declareMock<DataRepository> {
                runBlocking {
                    given(getAllFavorite()).willReturn(FakeRepository.getDataMovies())
                }
            }
            viewModel.getFavoriteList()
            val data = viewModel.favoriteList.value
            val loading = viewModel.observeLoading().value!!
            Mockito.verify(service, times(1)).getAllFavorite()
            assertFalse(loading)
            assertNotNull(data)
        }
    }

    @Test
    fun `Should set true when favorite movie is empty`() {
        runBlocking {
            val service = declareMock<DataRepository> {
                runBlocking {
                    given(getAllFavorite()).willReturn(null)
                }
            }
            viewModel.getFavoriteList()
            val data = viewModel.favoriteList.value
            Mockito.verify(service, times(1)).getAllFavorite()
            assertNull(data)
        }
    }
}