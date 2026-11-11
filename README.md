# Meteo

Native Android (Java) **per-city weather** app, released as a free, educational project: it serves as an example for anyone who wants to build their own weather app. The user saves a list of cities and views, for each one, the temperature, wind speed and a weather description, fetched from the public OpenWeatherMap API.

## Main features

- **Add a city** by name (dialog box).
- **List of saved cities**, with a featured main city shown at the top of the screen (temperature, wind, description, current date, weather icon).
- Actions on a city: **refresh** its data, **feature** it, **edit** it or **delete** it.
- **Refreshing** weather data through the OpenWeatherMap API (`/data/2.5/weather?q=<city>`); conversion of the temperature from Kelvin to degrees Celsius.
- **Local storage** of cities and their latest weather data in a SQLite database (`weather.db`, `ville` table).
- "About" screen.
- English and French interface (`res/values`, `res/values-fr`).

## Tech stack

- **Java** / Android SDK (compileSdk and targetSdk 22, minSdk 9)
- `com.android.support:appcompat-v7:22.0.0` library
- Android Gradle Plugin 1.0.0, Gradle 2.2.1 (wrapper included)
- SQLite (`SQLiteOpenHelper`), `HttpURLConnection` + `AsyncTask` for network calls, `org.json` for parsing responses
- Android Studio project (`Meteo.iml`, `.idea/`)

## Project structure

```
app/src/main/
├── AndroidManifest.xml               # Activities (main, About) and permissions (INTERNET, VIBRATE)
├── java/biz/glieunou/meteo/
│   ├── MainActivity.java             # Main screen: featured city, list, weather API calls
│   ├── City.java                     # Dialog box for adding a city
│   ├── Fenetre.java                  # Dialog box for actions on a city
│   ├── About.java                    # "About" screen
│   ├── Ville.java                    # City data model
│   ├── Database.java                 # SQLite database creation / upgrade
│   └── Query.java                    # Data access (city CRUD)
└── res/                              # Layouts, menus, strings (en / fr), images
build.gradle, settings.gradle         # Gradle configuration
gradlew, gradlew.bat, gradle/         # Gradle wrapper
```

## Installation and running

The project uses old versions of the Android tooling (Gradle 2.2.1, Android Gradle Plugin 1.0.0, build-tools 22.0.1): you need a compatible JDK and Android SDK (or upgrade these versions).

- Open the project folder in Android Studio, then run the app on an emulator or a device.
- Or from the command line:

  ```bash
  ./gradlew assembleDebug      # generates app/build/outputs/apk/
  ./gradlew installDebug       # installs on a connected device
  ```

## Configuration

No `.env` file is used. The OpenWeatherMap API URL is defined in `MainActivity.java` (`BASEURL` constant). The OpenWeatherMap API now requires an API key (`appid` parameter): add it to the request if needed. The Android SDK path is set in `local.properties` (`sdk.dir`), a file not tracked by Git.
