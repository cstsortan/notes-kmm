# NotesApp - Kotlin Multiplatform Mobile

A cross-platform note-taking application built with Kotlin Multiplatform Mobile (KMM), demonstrating modern Android development patterns and cross-platform code sharing.

## 🚀 Features

- ✅ **Create Notes**: Add new notes with title and description
- ✅ **View Notes**: Browse all notes in a clean Material3 interface  
- ✅ **Edit Notes**: Modify existing notes with real-time updates
- ✅ **Delete Notes**: Remove notes with confirmation
- ✅ **Persistent Storage**: Local SQLite database with Room
- ✅ **Cross-Platform**: Shared business logic between Android and iOS

## 🏗️ Architecture

### Project Structure

```
NotesApp/
├── shared/                 # Shared business logic (Kotlin Multiplatform)
│   ├── src/commonMain/    # Platform-agnostic code
│   ├── src/androidMain/   # Android-specific implementations
│   └── src/iosMain/       # iOS-specific implementations
├── androidApp/            # Android application
└── iosApp/                # iOS application (basic structure)
```

### Technologies Used

#### Core Technologies
- **Kotlin Multiplatform**: 2.2.10
- **Jetpack Compose**: Latest BOM (2025.08.00)
- **Room Database**: 2.7.2 with multiplatform support
- **Material3**: Modern Android design system
- **SKIE**: 0.10.6 for enhanced Swift interop

#### Architecture Patterns
- **MVI (Model-View-Intent)**: Unidirectional data flow
- **Repository Pattern**: Clean data layer separation
- **Dependency Injection**: Koin for modular DI

#### Navigation & State Management
- **Navigation Compose**: Type-safe navigation
- **StateFlow & SharedFlow**: Reactive state management
- **UI State/Actions/Effects**: Complete MVI implementation

## 🔧 Technical Implementation

### MVI Architecture Pattern

The Android app implements a comprehensive MVI architecture with:

```kotlin
// UI State - All possible screen states
sealed class AddNoteUiState {
    data class Content(
        val noteTitle: String = "",
        val noteContent: String = ""
    ) : AddNoteUiState()
    object Loading : AddNoteUiState()
    data class Error(val message: String) : AddNoteUiState()
}

// Actions - User intents
sealed class AddNoteAction {
    data class UpdateTitle(val title: String) : AddNoteAction()
    data class UpdateContent(val content: String) : AddNoteAction()
    object SaveNote : AddNoteAction()
}

// Effects - One-time side effects (navigation, error messages)
sealed class AddNoteUiEffect {
    object NavigateBack : AddNoteUiEffect()
    data class ShowError(val message: String) : AddNoteUiEffect()
}
```

### Shared Module Components

#### Data Models
```kotlin
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val createdAt: Long
)

data class Note(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: Long
)
```

#### Repository Pattern
```kotlin
interface NotesRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun getNote(id: String): Flow<Note?>
    suspend fun addNote(note: CreateNote)
    suspend fun updateNote(id: String, note: CreateNote)
    suspend fun deleteNote(id: String)
}
```

### Database Configuration

Room database with multiplatform support:
- **Platform-agnostic**: DAO interfaces and entities in commonMain
- **Platform-specific**: Database builders using expect/actual pattern
- **Migration Support**: Schema export enabled for version control

## 🛠️ Setup & Installation

### Prerequisites
- **Android Studio**: Electric Eel or newer
- **Xcode**: 14.0+ (for iOS development)
- **JDK**: 11 or higher
- **Kotlin**: 2.2.10

### Getting Started

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd NotesApp
   ```

2. **Open in Android Studio**
   - Import the project
   - Sync Gradle files
   - Wait for indexing to complete

3. **Run Android App**
   ```bash
   ./gradlew :androidApp:assembleDebug
   ```

4. **Run iOS App** (macOS only)
   ```bash
   ./gradlew :shared:podInstall
   # Open iosApp/iosApp.xcodeproj in Xcode
   ```

### Build Configurations

- **Compile SDK**: 36
- **Target SDK**: 36  
- **Min SDK**: 26 (Android 8.0+)
- **Java**: 11
- **iOS Deployment Target**: 15.0+

## 📱 Platform Status

### Android
✅ **Fully Implemented**
- Complete CRUD operations
- MVI architecture with StateFlow/SharedFlow
- Material3 UI with proper theming
- Type-safe navigation
- Error handling and loading states
- Dependency injection with Koin

### iOS  
🚧 **Basic Structure**
- Project setup with SKIE integration
- Shared framework integration
- SwiftUI placeholder interface
- *UI implementation pending*

## 🏛️ Project Highlights

### Basic Architecture Features
- **Sealed Class States**: Type-safe UI state management
- **Action-Based Events**: Unidirectional data flow
- **Effect System**: Proper separation of side effects
- **Repository Abstraction**: Testable data layer
- **Modular DI**: Clean dependency management

### Code Organization
```
androidApp/src/main/java/com/example/notesapp/
├── ui/
│   ├── screens/              # Compose screens
│   ├── viewmodel/
│   │   ├── addnote/         # Add note MVI components
│   │   └── editnote/        # Edit note MVI components  
│   └── router/              # Navigation setup
└── di/                      # Dependency injection modules
```

Run tests:
```bash
./gradlew test
./gradlew connectedAndroidTest
```

## 📄 License

This project is a demonstration of Kotlin Multiplatform Mobile development patterns and modern Android architecture.

## 🤝 Contributing

This is a sample project showcasing:
- Kotlin Multiplatform Mobile best practices
- Modern Android development with Compose
- MVI architecture implementation
- Cross-platform code sharing strategies

---

Built with ❤️ using Kotlin Multiplatform Mobile