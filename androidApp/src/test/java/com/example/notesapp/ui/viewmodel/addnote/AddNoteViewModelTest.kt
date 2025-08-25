package com.example.notesapp.ui.viewmodel.addnote

import com.example.shared.viewmodels.addnote.AddNoteAction
import com.example.shared.viewmodels.addnote.AddNoteUiEffect
import com.example.shared.viewmodels.addnote.AddNoteUiState
import com.example.shared.viewmodels.addnote.AddNoteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddNoteViewModelTest {

    private lateinit var viewModel: AddNoteViewModel
    private lateinit var mockRepository: MockNotesRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = MockNotesRepository()
        viewModel = AddNoteViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be empty content`() = runTest {
        val initialState = viewModel.uiState.first()
        assertTrue(initialState is AddNoteUiState.Content)
        val contentState = initialState as AddNoteUiState.Content
        assertEquals("", contentState.noteTitle)
        assertEquals("", contentState.noteContent)
    }

    @Test
    fun `updateTitle should update noteTitle in content state`() = runTest {
        val newTitle = "Test Title"
        
        viewModel.onAction(AddNoteAction.UpdateTitle(newTitle))
        
        val state = viewModel.uiState.first()
        assertTrue(state is AddNoteUiState.Content)
        val contentState = state as AddNoteUiState.Content
        assertEquals(newTitle, contentState.noteTitle)
        assertEquals("", contentState.noteContent)
    }

    @Test
    fun `updateContent should update noteContent in content state`() = runTest {
        val newContent = "Test Content"
        
        viewModel.onAction(AddNoteAction.UpdateContent(newContent))
        
        val state = viewModel.uiState.first()
        assertTrue(state is AddNoteUiState.Content)
        val contentState = state as AddNoteUiState.Content
        assertEquals("", contentState.noteTitle)
        assertEquals(newContent, contentState.noteContent)
    }

    @Test
    fun `updateTitle and updateContent should update both fields`() = runTest {
        val title = "Test Title"
        val content = "Test Content"
        
        viewModel.onAction(AddNoteAction.UpdateTitle(title))
        viewModel.onAction(AddNoteAction.UpdateContent(content))
        
        val state = viewModel.uiState.first()
        assertTrue(state is AddNoteUiState.Content)
        val contentState = state as AddNoteUiState.Content
        assertEquals(title, contentState.noteTitle)
        assertEquals(content, contentState.noteContent)
    }

    @Test
    fun `clearFields should reset title and content to empty strings`() = runTest {
        viewModel.onAction(AddNoteAction.UpdateTitle("Title to clear"))
        viewModel.onAction(AddNoteAction.UpdateContent("Content to clear"))
        
        viewModel.onAction(AddNoteAction.ClearFields)
        
        val state = viewModel.uiState.first()
        assertTrue(state is AddNoteUiState.Content)
        val contentState = state as AddNoteUiState.Content
        assertEquals("", contentState.noteTitle)
        assertEquals("", contentState.noteContent)
    }

    @Test
    fun `saveNote should change state to loading then back to content and emit NavigateBack effect`() = runTest {
        val title = "Test Note"
        val content = "Test Content"
        
        viewModel.onAction(AddNoteAction.UpdateTitle(title))
        viewModel.onAction(AddNoteAction.UpdateContent(content))
        
        viewModel.onAction(AddNoteAction.SaveNote)
        
        val finalState = viewModel.uiState.first()
        assertTrue(finalState is AddNoteUiState.Content)
        val contentState = finalState as AddNoteUiState.Content
        assertEquals("", contentState.noteTitle)
        assertEquals("", contentState.noteContent)
        
        assertTrue(mockRepository.addNoteCalled)
        assertEquals(title, mockRepository.lastCreateNote?.title)
        assertEquals(content, mockRepository.lastCreateNote?.description)
    }

    @Test
    fun `saveNote should emit NavigateBack effect on success`() = runTest {
        val title = "Test Note"
        val content = "Test Content"
        
        viewModel.onAction(AddNoteAction.UpdateTitle(title))
        viewModel.onAction(AddNoteAction.UpdateContent(content))
        
        val effects = mutableListOf<AddNoteUiEffect>()
        backgroundScope.launch(testDispatcher) {
            viewModel.uiEffects.collect { effects.add(it) }
        }
        
        viewModel.onAction(AddNoteAction.SaveNote)
        
        assertEquals(1, effects.size)
        assertTrue(effects[0] is AddNoteUiEffect.NavigateBack)
    }

    @Test
    fun `saveNote should handle repository error and show error state and effect`() = runTest {
        val errorMessage = "Network error"
        mockRepository.shouldThrowError = true
        mockRepository.errorMessage = errorMessage
        
        val title = "Test Note"
        val content = "Test Content"
        
        viewModel.onAction(AddNoteAction.UpdateTitle(title))
        viewModel.onAction(AddNoteAction.UpdateContent(content))
        
        val effects = mutableListOf<AddNoteUiEffect>()
        backgroundScope.launch(testDispatcher) {
            viewModel.uiEffects.collect { effects.add(it) }
        }
        
        viewModel.onAction(AddNoteAction.SaveNote)
        
        val finalState = viewModel.uiState.first()
        assertTrue(finalState is AddNoteUiState.Error)
        val errorState = finalState as AddNoteUiState.Error
        assertEquals(errorMessage, errorState.message)
        
        assertEquals(1, effects.size)
        assertTrue(effects[0] is AddNoteUiEffect.ShowError)
        val showErrorEffect = effects[0] as AddNoteUiEffect.ShowError
        assertEquals(errorMessage, showErrorEffect.message)
    }

    @Test
    fun `actions should not modify state when not in content state - loading`() = runTest {
        mockRepository.shouldDelayAddNote = true
        viewModel.onAction(AddNoteAction.UpdateTitle("Initial Title"))
        viewModel.onAction(AddNoteAction.UpdateContent("Initial Content"))
        
        viewModel.onAction(AddNoteAction.SaveNote)
        
        val loadingState = viewModel.uiState.first()
        assertTrue(loadingState is AddNoteUiState.Loading)
        
        viewModel.onAction(AddNoteAction.UpdateTitle("New Title"))
        viewModel.onAction(AddNoteAction.UpdateContent("New Content"))
        viewModel.onAction(AddNoteAction.ClearFields)
        
        val stateAfterActions = viewModel.uiState.first()
        assertTrue(stateAfterActions is AddNoteUiState.Loading)
    }

    @Test
    fun `actions should not modify state when in error state`() = runTest {
        mockRepository.shouldThrowError = true
        mockRepository.errorMessage = "Test error"
        
        viewModel.onAction(AddNoteAction.UpdateTitle("Initial Title"))
        viewModel.onAction(AddNoteAction.UpdateContent("Initial Content"))
        viewModel.onAction(AddNoteAction.SaveNote)
        
        val errorState = viewModel.uiState.first()
        assertTrue(errorState is AddNoteUiState.Error)
        
        viewModel.onAction(AddNoteAction.UpdateTitle("New Title"))
        viewModel.onAction(AddNoteAction.UpdateContent("New Content"))
        viewModel.onAction(AddNoteAction.ClearFields)
        
        val stateAfterActions = viewModel.uiState.first()
        assertTrue(stateAfterActions is AddNoteUiState.Error)
    }
}