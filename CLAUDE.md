# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

LTC (Lens Thickness Calculator) is a Kotlin Multiplatform Mobile (KMM) application for Android and iOS that calculates optical lens thickness. The app follows MVVM architecture with modern Android/iOS development practices.

**Package:** `parkhomov.andrew.lensthicknesscalculator` (Android) / `parkhomov.andrew.ltc` (common)
**Version:** 5.0.0 (versionCode: 22)
**Platforms:** Android (min SDK 23, target SDK 36), iOS (ARM64 + Simulator)

## Common Commands

### Build & Run
```bash
# Build all targets
./gradlew build

# Build Android APK (debug)
./gradlew :composeApp:assembleDebug

# Build Android APK (release)
./gradlew :composeApp:assembleRelease

# Install debug build on connected device
./gradlew installDebug

# Clean build
./gradlew clean
```

### Testing
```bash
# Run all unit tests
./gradlew test

# Run Android unit tests (debug)
./gradlew testDebugUnitTest

# Run specific test class
./gradlew testDebugUnitTest --tests "parkhomov.andrew.ltc.ThicknessUnitTest"

# Run iOS simulator tests
./gradlew iosSimulatorArm64Test

# Run all tests (Android + iOS)
./gradlew allTests
```

### Code Quality
```bash
# Run ktlint check
./gradlew ktlintCheck

# Auto-format code with ktlint
./gradlew ktlintFormat

# Generate ktlint baseline (if needed)
./gradlew ktlintGenerateBaseline

# Run Android lint
./gradlew lint
```

### iOS Development
The iOS app wrapper is in `iosApp/` and built with Xcode. The KMM framework is generated via:
```bash
# Build iOS framework
./gradlew :composeApp:linkDebugFrameworkIosArm64
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64
```

## Architecture

### Module Structure

**composeApp** - Main application module
- `commonMain/` - Shared UI and business logic (Compose Multiplatform)
- `androidMain/` - Android-specific implementations (Application class, Firebase setup)
- `iosMain/` - iOS-specific implementations (MainViewController)
- `androidUnitTest/` - JUnit unit tests

**core-storage** - Shared storage library
- DataStore-based persistent storage abstraction
- Platform-agnostic settings management (theme, language)
- Used by `composeApp` for preferences

### MVVM Pattern

All screens follow a consistent MVVM structure:

**Base ViewModel:**
```kotlin
// composeApp/src/commonMain/kotlin/parkhomov/andrew/ltc/base/AppViewModel.kt
abstract class AppViewModel<State, V> : ViewModel() {
    abstract val initialState: State
    val uiState: MutableStateFlow<State>
    abstract fun uiEvent(event: V)
}
```

**Screen Components:**
1. **ViewModel** - Manages state and handles UI events (e.g., `MainScreenViewModel`)
2. **UiState** - Immutable data class representing screen state
3. **UiEvent** - Sealed interface for user actions
4. **Screen** - Composable UI that observes state and emits events

### Dependency Injection (Koin)

DI modules are in `composeApp/src/commonMain/kotlin/parkhomov/andrew/ltc/di/`:
- `ViewModelsModule.kt` - All ViewModels
- `OtherModule.kt` - Domain services (e.g., `CompareLensStorage`)
- `StorageModule.kt` (in core-storage) - DataStore and `SettingsProvider`

Initialization:
- **Android:** `App.onCreate()` with Android context
- **iOS:** `initKoin()` in common code

### Navigation

Uses type-safe Navigation3 with serializable routes:
```kotlin
// composeApp/src/commonMain/kotlin/parkhomov/andrew/ltc/navigation/Route.kt
sealed interface Route : NavKey {
    data object MainScreen : Route
    data object CompareScreen : Route
}
```

Navigation is managed in `AppContent.kt` with `NavBackStack` and animated transitions.

### Localization (Lyricist)

Compile-time safe string localization with KSP code generation.

**Supported languages:** English (default), Ukrainian, Portuguese

**String definitions:** `composeApp/src/commonMain/kotlin/parkhomov/andrew/ltc/strings/`
- `Strings.kt` - String data class
- `EnStrings.kt`, `UkStrings.kt` - Language-specific strings with `@LyricistStrings` annotation

**Usage in Compose:**
```kotlin
val strings = rememberStrings()
Text(strings.someKey)
```

**Language switching:** Managed via `SettingsProvider` with DataStore persistence.

### Platform-Specific Code (expect/actual)

Key platform abstractions:
- `DataStoreProvider` - File system access for DataStore (Android uses Context, iOS uses NSFileManager)
- `ShowToast` - Toast/notification UI
- `ShareManager` - Content sharing
- `Platform` - Platform detection utilities

Located in `commonMain/` (expect declarations) with implementations in `androidMain/` and `iosMain/`.

### Theme & Styling

Material3 design system with Light/Dark mode support:
- `composeApp/src/commonMain/kotlin/parkhomov/andrew/ltc/theme/`
- `ThemeMode` enum: SYSTEM, LIGHT, DARK
- Persistent preference via `SettingsProvider`
- Composition local: `LocalThemeMode`

## Key Dependencies

- **Kotlin:** 2.3.0
- **Compose Multiplatform:** 1.9.3
- **Koin:** 4.1.1 (DI)
- **Navigation3:** 1.0.0-alpha06 (type-safe navigation)
- **Lyricist:** 1.8.0 (localization)
- **DataStore:** 1.2.0 (preferences)
- **Kotlinx Serialization:** 1.9.0
- **Lifecycle/ViewModel:** 2.10.0-alpha07
- **Firebase:** BOM 34.7.0 (Android only - Crashlytics, Analytics)
- **Testing:** JUnit 4.13.2, MockK 1.14.7, Turbine 1.2.1

## Code Quality Standards

### ktlint Configuration
- Version: 1.3.1
- Excludes generated code: `/build/generated/`, `/ksp/`, `/commonMain/kotlin/` (auto-generated)
- Configuration in root `build.gradle.kts`

### Testing Patterns
- Use `MainDispatcherRule` for coroutines in tests
- Turbine for Flow/StateFlow testing
- Test doubles: `TestCompareLensStorage`, `TestSettingsProvider`, `TestToastProvider`
- Example: `composeApp/src/androidUnitTest/kotlin/parkhomov/andrew/ltc/ThicknessUnitTest.kt`

## Important Notes

### Kotlin Compiler Flags
The project uses `-Xexpect-actual-classes` for expect/actual mechanism (configured in root `build.gradle.kts`).

### Firebase Setup
Firebase is Android-only. iOS does not include Firebase dependencies. Configuration in:
- `composeApp/build.gradle.kts` (androidMain dependencies)
- `App.kt` for initialization

### Proguard/R8
Release builds use ProGuard rules. Keep rules for serialization, reflection-based libraries.

### Version Catalog
All dependency versions managed in `gradle/libs.versions.toml` using Gradle version catalogs.

### Build Variants (Android)
- **debug** - Development build
- **stage** - Staging build (distinct package: `.stage`)
- **release** - Production build (signed, minified)

## Common Development Tasks

### Adding a New Screen
1. Create route in `navigation/Route.kt`
2. Create ViewModel extending `AppViewModel<State, Event>`
3. Define UiState and UiEvent interfaces
4. Register ViewModel in `di/ViewModelsModule.kt`
5. Create Composable screen in `ui/screens/`
6. Add navigation in `AppContent.kt`

### Adding Localized Strings
1. Add property to `Strings.kt` data class
2. Update `EnStrings.kt`, `UkStrings.kt` with translations
3. KSP will regenerate code on next build
4. Use via `rememberStrings()` in Compose

### Adding Dependencies
1. Update `gradle/libs.versions.toml`
2. Add to appropriate source set in `composeApp/build.gradle.kts`:
   - `commonMain` for multiplatform libraries
   - `androidMain` for Android-only
   - `iosMain` for iOS-only
