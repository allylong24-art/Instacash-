InstantCash/
├── README.md                                 # Project documentation
├── build.gradle.kts                          # Top-level Gradle configuration
├── settings.gradle.kts                       # Gradle settings & repository declarations
├── metadata.json                             # AI Studio project metadata
├── app/
│   ├── build.gradle.kts                      # Android App module build config & dependencies
│   └── src/main/
│       ├── AndroidManifest.xml               # Manifest file declaring permissions & Activity
│       └── java/com/example/
│           ├── MainActivity.kt               # Entry point Activity with Navigation host
│           ├── data/                         # Room Database Entities & DAOs
│           │   ├── dao/InstantCashDao.kt
│           │   ├── database/InstantCashDatabase.kt
│           │   └── entity/
│           │       ├── GigTask.kt
│           │       ├── UserProfile.kt
│           │       ├── WalletTransaction.kt
│           │       ├── ChatMessage.kt
│           │       └── NotificationItem.kt
│           ├── repository/
│           │   └── InstantCashRepository.kt  # Central data access repository
│           ├── network/
│           │   └── HyperwalletService.kt     # Hyperwallet instant payout API integration
│           └── ui/
│               ├── InstantCashViewModel.kt   # App State & MVVM Business Logic
│               ├── components/               # Reusable Jetpack Compose Components
│               │   ├── NavbarAndHeader.kt
│               │   ├── TaskCard.kt
│               │   ├── CashOutDialog.kt
│               │   ├── ProofUploadDialog.kt
│               │   └── MapSimulator.kt
│               ├── screens/                  # Application Screen Views
│               │   ├── WorkerHomeScreen.kt
│               │   ├── CustomerDashboardScreen.kt
│               │   ├── PostJobScreen.kt
│               │   ├── JobDetailScreen.kt
│               │   ├── WalletScreen.kt
│               │   ├── ChatScreen.kt
│               │   ├── DisputeCenterScreen.kt
│               │   ├── ProfileVerificationScreen.kt
│               │   └── NotificationsScreen.kt
│               └── theme/                    # Material 3 Color, Type, and Theme
│                   ├── Color.kt
│                   ├── Theme.kt
│                   └── Type.kt
