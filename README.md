# Bkash-UI-implementation-
# bKash Clone — Android App (Jetpack Compose)

A pixel-faithful UI clone of the **bKash** mobile banking app built entirely with **Jetpack Compose** and **Material 3**. This project was created as a UI/UX study and demonstration of modern Android development practices using a declarative, component-driven architecture.

> **Disclaimer:** This is a non-commercial, educational UI clone. It has no backend, no real financial functionality, and no affiliation with bKash Limited.

---

## Contents

| Signup | Login | Home |
|--------|-------|------|
| Sign Up screen with editable phone number, PIN entry, and language toggle | Login screen with editable account number, PIN dots, and Bangla/English toggle | Full scrollable Home screen with hero, service grid, carousel, and sections |

---

## Features

- **Signup Screen** — Editable phone number field, 6-digit PIN entry via custom numeric keypad, Bangla/English language toggle, "Have account? Login" navigation link
- **Login Screen** — Editable account number, PIN dot indicator, custom keypad, biometric icon, Forgot PIN link, "Don't have an account? Sign Up" navigation link, full Bangla/English toggle across all text
- **Home Screen** — Rich layered hero header with stadium illustration (canvas-drawn pitch, goal posts, spotlight rays, footballer silhouette), expandable/collapsible 4-column service grid (16 services), horizontal swipeable promotional banner carousel with 5 slides and live page indicator dots, Quick Features section with shortcut chips and colored cards, Gaming & Entertainment card, horizontally scrollable Bundle / Suggestions / Offers sections, 4-tab bottom navigation bar
- **Navigation** — Jetpack Compose Navigation with three destinations: `signup → login → home`. Android system Back works correctly throughout.
- **Language Toggle** — All Login and Signup screen text switches between English and Bangla with a single tap. State is preserved across configuration changes using `rememberSaveable`.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI Toolkit | Jetpack Compose |
| Design System | Material 3 |
| Navigation | Jetpack Compose Navigation (`navigation-compose`) |
| Pager / Carousel | `androidx.compose.foundation.pager.HorizontalPager` |
| Build System | Gradle with Version Catalog (`libs.versions.toml`) |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 |
| Compose BOM | `2026.02.01` |

No third-party libraries beyond the official AndroidX / Compose ecosystem are used.

---

## Project Structure

```
com.example.bkashclone
├── MainActivity.kt          # Single activity, NavHost with signup/login/home routes
└── screens/
    ├── SignupScreen.kt      # Signup UI with language toggle and PIN keypad
    ├── LoginScreen.kt       # Login UI with editable account number and language toggle
    └── HomeScreen.kt        # Full home screen with all sections and carousel
```

All composables are scoped `private` within their respective files. No ViewModels, repositories, or dependency injection frameworks are used — this is a pure UI project.

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 11
- Android SDK with API 36

### Running the project

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/bkash-clone.git
   ```
2. Open the project in Android Studio.
3. Let Gradle sync complete.
4. Run on an emulator or physical device (API 24+).

No API keys, backend configuration, or environment variables are required.

---

## Navigation Flow

```
SignupScreen  ──[Sign Up]──►  LoginScreen  ──[Next]──►  HomeScreen
     ▲                             │
     └──────[Don't have account]───┘
     └──────[Have account? Login]──►  LoginScreen
                                       │
                            [System Back] returns through the stack
```

---

## Key Implementation Notes

- **No `!booleanValue` operator** — The project avoids the `WideNavigationRailValue.not()` ambiguity bug in certain Material 3 + Kotlin versions by using `value.not()` explicitly for all boolean toggles.
- **`rememberSaveable` for language state** — The Bangla/English toggle survives rotation and process death.
- **`BasicTextField` for account number** — Used instead of Material `TextField` to preserve the original bKash minimal underline style with no decoration box.
- **Canvas-drawn hero** — The home screen hero background is built entirely with Compose `Canvas` primitives: radial gradients, arc circles, diagonal ray lines, a green pitch rectangle, goal posts, and a semi-circle arc — no drawable resources required.
- **`HorizontalPager` carousel** — Five promotional banner slides with a live dot indicator that tracks `pagerState.currentPage`, matching the original app's swipeable banner behavior.

---

## License

This project is released under the [MIT License](LICENSE). It is intended solely for educational and portfolio purposes. All bKash branding, trademarks, and visual design belong to **bKash Limited**.
