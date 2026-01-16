# LoadApp

LoadApp is an Android application that allows users to download files from the internet and view the download status via notifications and a detail screen. It features a custom animated loading button and demonstrates the use of `DownloadManager`, `NotificationManager`, and custom views.

## Overview
The application provides a simple interface with radio buttons to select one of several predefined files to download. Upon clicking the custom "Download" button, the app initiates a download using the Android `DownloadManager`. Users are notified when the download completes, and they can click on the notification to view the download details (file name and success/fail status).

### Key Features
- **Custom Loading Button**: A custom `View` with animated progress and a rotating circle.
- **Download Management**: Uses `DownloadManager` for robust background downloads.
- **Notifications**: Notifies users upon download completion with an action to view details.
- **Detail Screen**: Displays the name of the downloaded file and its status.
- **Modern Android Components**: Built using Kotlin, ViewBinding, Navigation Component, and ViewModel.

## Requirements
- **Android SDK**: Min SDK 27, Target SDK 36.
- **Language**: Kotlin.
- **Build System**: Gradle with Kotlin DSL.
- **Java Version**: JDK 11.
- **Android Studio**: Recommended latest version.

## Setup & Run
1.  **Clone the repository**:
    ```bash
    git clone <repository-url>
    ```
2.  **Open in Android Studio**:
    - File > Open... > Select the `LoadApp` project directory.
3.  **Sync Gradle**:
    - Let Android Studio sync the Gradle projects.
4.  **Run the app**:
    - Connect an Android device or start an emulator (API 27+).
    - Click the **Run** button (green play icon) in Android Studio.

## Scripts & Commands
Since this is a standard Android project using Gradle, you can use the following commands from the terminal:

- **Build project**:
  ```bash
  ./gradlew assembleDebug
  ```
- **Clean project**:
  ```bash
  ./gradlew clean
  ```

## Project Structure
```text
app/
├── src/
│   ├── main/
│   │   ├── java/com/emman/android/loadapp/
│   │   │   ├── ui/                 # Fragments (Menu, Detail, Permission)
│   │   │   ├── utils/              # Notification utilities
│   │   │   ├── LoadingButton.kt    # Custom View implementation
│   │   │   ├── MainActivity.kt     # Entry Activity
│   │   │   ├── MainViewModel.kt    # Shared ViewModel
│   │   │   └── ...
│   │   ├── res/
│   │   │   ├── layout/             # XML Layouts
│   │   │   ├── navigation/         # NavGraph for app flow
│   │   │   └── ...
│   │   └── AndroidManifest.xml
│   ├── test/                       # Unit tests
│   └── androidTest/                # Instrumented tests
build.gradle.kts                    # Project-level build configuration
app/build.gradle.kts                # App-level build configuration
```

*Created as part of the Android Kotlin Developer Nanodegree.*