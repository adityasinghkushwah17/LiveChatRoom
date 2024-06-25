# Live Chat Room


Live Chat Room is an Android application built with Kotlin and Jetpack Compose. It provides a real-time chat experience with features like live chat, fast signup and login using Firebase email authentication, status updates, and a profile page for users to view their profiles. The project uses Dagger Hilt for dependency injection and KSP for fast processing.

## Features
- Live Chat: Real-time chat functionality for users to communicate instantly.
- Fast Signup and Login: Quick and secure authentication using Firebase email authentication.
- User Status: Users can upload and view status updates.
- Profile Page: A profile page where users can view and edit their personal information.
## Technologies Used
- Kotlin: The programming language used for Android development.
- Jetpack Compose: Android's modern toolkit for building native UI.
- Firebase: Backend services including Firestore for database and Firebase Authentication for user authentication.
- Dagger Hilt: Dependency injection library for Android to manage dependencies.
- KSP: Kotlin Symbol Processing API for fast annotation processing.

## Usage
### Signup and Login:
- Open the app and sign up using your email and password.
- Log in with the registered credentials.
### Live Chat:
- Start a new chat or join an existing chat to communicate with other users in real-time.
### User Status:
- Upload a status update which can be viewed by other users.
- View status updates from your connections.
### Profile Page:

- View your profile information.
- Edit your profile details as needed.

## Project Structure
The project follows a clean architecture with MVVM (Model-View-ViewModel) pattern:

- Screens: Contains all the UI components built with Jetpack Compose.
- ViewModel: Contains the logic to interact with Firebase and manage UI state.
- Repository: Manages data operations and communicates with Firebase services.
- Di: Dependency injection setup using Dagger Hilt.
- Model: Data classes representing the app's data model.

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes. Ensure that your code adheres to the project's coding standards and passes all tests.

## Prerequisites
- Android Studio Bumblebee | 2021.1.1 or later
- A Firebase project set up with Firestore and Authentication enabled
## Installation
### 1.Clone the repository:
### 2.Open the project in Android Studio:

- Open Android Studio.
- Click on File > Open... and select the cloned project directory.
### 3.Configure Firebase:

- Go to the Firebase Console.
- Create a new project or select an existing project.
- Add an Android app to your Firebase project with your app's package name.
- Download the google-services.json file and place it in the app/ directory of your Android project.
- In the Firebase console, enable Firestore and Firebase Authentication.
### 4.Build the project:

- Sync the project with Gradle files by clicking on Sync Project with Gradle Files.
- Build and run the project on an emulator or a physical device.

