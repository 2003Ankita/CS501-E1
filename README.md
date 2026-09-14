# Mobility Lens

**CS501 E1 — Mobile Application Development**  
**Exercise 1: Understanding Mobility and Mobile Application Development**  
**Ankita Patra**
**BU ID : U38177365**

## Overview

Mobility Lens is a single-screen Android application developed using Kotlin and Jetpack Compose. The application explores six dimensions that distinguish mobile application development from traditional desktop application development.

Users can navigate through the six mobility dimensions using Previous and Next controls. Each dimension presents a mobile constraint together with a corresponding developer implication. The application also allows the user to enter the name of an application or feature and analyze it in the context of the currently selected mobility dimension.

## Mobility Dimensions

The application covers six dimensions:

1. Input and Interaction
2. Screen Size, Orientation, and Density
3. Lifecycle and Resource Constraints
4. Context Awareness
5. Usage Patterns
6. Security and Privacy Expectations

Each dimension includes a short explanation of the mobile constraint and a practical implication for application developers.

## Features

- Six mobility dimensions with developer implications
- Previous and Next navigation
- Dimension counter and progress indicator
- Text input using Compose state
- Empty-input validation and visible error feedback
- Dynamic analysis feedback based on the selected dimension
- Material 3 components and customized typography
- Scrollable layout for different screen dimensions
- Portrait and landscape support
- Jetpack Compose Preview for the principal screen
- String resources for user-facing labels and messages

## Project Configuration

- **Package / applicationId:** `com.ankitapatra.mobilitylens`
- **Language:** Kotlin
- **UI framework:** Jetpack Compose with Material 3
- **minSdk:** 24
- **targetSdk:** 37
- **compileSdk:** 37
- **Test device:** Pixel 7 emulator, API 35
- **Build system:** Gradle with Kotlin DSL
- **Dependency management:** Gradle Version Catalog (`libs.versions.toml`)

## How to Run

1. Clone this repository.
2. Open the project folder in Android Studio.
3. Allow Gradle to sync and download the required dependencies.
4. Select an Android emulator or compatible Android device.
5. Run the `app` configuration.
6. The project was tested using a Pixel 7 emulator running API 35.

## User Interaction and Validation

The application maintains the currently selected mobility dimension and text input using Compose state.

The **Previous** and **Next** buttons update the selected dimension and become disabled when the user reaches the beginning or end of the six-dimension list.

The **Analyze** button validates the application or feature name. If the input is empty, the text field enters an error state and an explanatory message is displayed. With valid input, the application generates feedback connecting the entered application name to the currently selected mobility dimension.

## Rotation Observation

The application was tested in both portrait and landscape orientations.

The screen uses a vertically scrollable Compose layout so that content remains accessible when the available vertical space is reduced in landscape orientation.

The current dimension and entered text are stored using `remember`. During testing, rotating the emulator caused the Activity to be recreated, resetting the selected dimension to Dimension 1 and clearing the entered text. This demonstrates the distinction between state retained during recomposition and state that must survive Activity recreation.

## Android Configuration

`AndroidManifest.xml` defines `MainActivity` as the launcher activity. The application does not request additional device permissions because its functionality does not require access to protected system features.

The project uses Gradle Kotlin DSL for build configuration. Dependency and plugin versions are centralized through `gradle/libs.versions.toml`, while `app/build.gradle.kts` specifies application configuration including the minimum, target, and compile SDK levels.

User-facing labels and messages are stored in `strings.xml` rather than being unnecessarily hardcoded in the UI.

## Project Structure

- `app/src/main/java/com/ankitapatra/mobilitylens/MainActivity.kt` — main Compose UI, state, interaction logic, and mobility dimension data
- `app/src/main/java/com/ankitapatra/mobilitylens/ui/theme/` — Material theme and typography configuration
- `app/src/main/res/values/strings.xml` — user-facing string resources
- `app/src/main/AndroidManifest.xml` — application and activity configuration
- `app/build.gradle.kts` — module-level Android and dependency configuration
- `gradle/libs.versions.toml` — centralized dependency and plugin versions
- `report/` — final exercise report and supporting submission material

## AI Assistance Disclosure

Generative AI was used as a limited development aid during this exercise for guidance on project setup, Jetpack Compose concepts, code review, and documentation organization. AI-generated suggestions were reviewed and adapted before being incorporated into the project. The application was manually built, executed, tested, and verified in Android Studio.
