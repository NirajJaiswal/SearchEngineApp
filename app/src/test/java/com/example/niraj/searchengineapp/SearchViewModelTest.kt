package com.example.niraj.searchengineapp

import com.example.niraj.searchengineapp.data.model.Owner
import com.example.niraj.searchengineapp.data.model.Question
import com.example.niraj.searchengineapp.data.model.StackOverflowResponse
import com.example.niraj.searchengineapp.domain.usecase.SearchQuestionsUseCase
import com.example.niraj.searchengineapp.presentation.viewmodel.SearchViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
    companion object {
        @JvmField
        @ClassRule
        val rxSchedulerRule = RxSchedulerRule(Schedulers.trampoline())
    }

    @get:Rule
    val instantTaskExecutorRule = InstantExecutorRule()

    @Mock
    private lateinit var searchQuestionsUseCase: SearchQuestionsUseCase

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = SearchViewModel(searchQuestionsUseCase)
    }

    @Test
    fun `searchQuestions with query length less than 3 should update error message`() {
        viewModel.searchQuestions("te")
        assertEquals("One more character required", viewModel.errorMessage.value)
        assertEquals(true, viewModel.searchResults.value.isEmpty())
    }

    @Test
    fun `searchQuestions with valid query should update searchResults`() = runTest {
        val query = "android"
        val mockResponse = StackOverflowResponse(
            items = listOf(
                Question(
                    title = "Question 1",
                    owner = Owner(displayName = "User 1"),
                    creationDate = 1678886400,
                    link = "link1"
                ),
                Question(
                    title = "Question 2", owner = Owner(displayName = "User 2"),
                    creationDate = 1678972800, link = "link2"
                )
            )
        )

        Mockito.`when`(searchQuestionsUseCase(query)).thenReturn(Single.just(mockResponse))
        viewModel.searchQuestions(query)
        advanceUntilIdle()
        assertEquals(mockResponse.items, viewModel.searchResults.value)
        assertEquals("", viewModel.errorMessage.value)
        assertEquals(false, viewModel.loading.value)
    }

    @Test
    fun `searchQuestions with valid api call will return an error then update show appropriate value`() =
        runTest {
            val query = "android"
            val errorMessageTest = "Test"
            Mockito.`when`(searchQuestionsUseCase(query))
                .thenReturn(Single.error(Throwable(errorMessageTest)))

            viewModel.searchQuestions(query)
            advanceUntilIdle()

            assertEquals(emptyList<Question>(), viewModel.searchResults.value)
            assertEquals(
                "Error: $errorMessageTest",
                viewModel.errorMessage.value
            )
            assertEquals(false, viewModel.loading.value)
        }

    @After
    fun tearDown() {
        viewModel.onCleared()
    }
}