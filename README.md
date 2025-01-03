# Set Game App 🎮

This repository contains the implementation of a **Set (Card) Game App**, developed as part of the CPP0902 Coding Assignment for the "Mobile Networking Technologies and Applications" course. The app is based on the classic card game "Set," designed by Marsha Falco in 1974.

## Objective

The goal is to provide an engaging digital experience of the Set game, complete with intuitive gameplay, visually appealing custom views, and seamless navigation.

## Features

### Gameplay & UI
- **Interactive Gameplay**:
  - Shuffle and deal 12 cards at the start.
  - Toggle card selection with a click.
  - Validate selected cards as a "set" or notify the user if invalid.
  - Automatically deal 3 more cards when no sets are found among the current cards.
  - End the game with a notification when the deck is exhausted.
  - Restart the game at any point with a dedicated button.
- **Responsive UI**:
  - Cards scale dynamically to fit different screen sizes.
  - Scrollable table to accommodate increasing card counts.

### Custom Views
- Cards are drawn programmatically using Android’s Graphics API for a polished and customizable look.
- Support for high-resolution displays.

### Navigation and Fragments
- **Two Fragments**:
  - Game Fragment: For core gameplay.
  - History Fragment: Displays a list of sets found so far.
- **Device-Specific Layouts**:
  - Mobile: Button-based navigation between fragments.
  - Tablets: Dual-pane layout displaying both fragments side by side.
- Navigation Component ensures smooth transitions without direct function calls between fragments.

## Technical Details
- **Programming Language**: Kotlin/Java for Android.
- **Platform**: Android Studio.
- **Graphics API**: Android Canvas and drawBitmap().
- **Navigation**: Jetpack Navigation Component.

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/Set-Game-App.git
   ```
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## Credits
- Designed and developed as part of the CPP0902 course project.
- Inspired by the original Set card game by Marsha Falco.

## License
This project is for educational purposes only. Plagiarism is strictly prohibited.
