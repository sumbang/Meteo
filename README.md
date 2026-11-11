# Meteo

Application Android native (Java) de **météo par ville**, publiée comme projet libre et pédagogique : elle sert d'exemple à celles et ceux qui souhaitent réaliser leur propre application météo. L'utilisateur enregistre une liste de villes et consulte, pour chacune, la température, la vitesse du vent et une description du temps, récupérées depuis l'API publique OpenWeatherMap.

## Fonctionnalités principales

- **Ajout d'une ville** par son nom (boîte de dialogue).
- **Liste des villes** enregistrées, avec mise en avant d'une ville principale affichée en haut de l'écran (température, vent, description, date du jour, icône du temps).
- Actions sur une ville : **rafraîchir** ses données, la **mettre en avant**, la **modifier** ou la **supprimer**.
- **Rafraîchissement** des données météo via l'API OpenWeatherMap (`/data/2.5/weather?q=<ville>`) ; conversion de la température de Kelvin en degrés Celsius.
- **Stockage local** des villes et de leurs dernières données météo dans une base SQLite (`weather.db`, table `ville`).
- Écran « À propos ».
- Interface en anglais et en français (`res/values`, `res/values-fr`).

## Stack technique

- **Java** / SDK Android (compileSdk et targetSdk 22, minSdk 9)
- Bibliothèque `com.android.support:appcompat-v7:22.0.0`
- Android Gradle Plugin 1.0.0, Gradle 2.2.1 (wrapper fourni)
- SQLite (`SQLiteOpenHelper`), `HttpURLConnection` + `AsyncTask` pour les appels réseau, `org.json` pour l'analyse des réponses
- Projet Android Studio (`Meteo.iml`, `.idea/`)

## Structure du projet

```
app/src/main/
├── AndroidManifest.xml               # Activités (principale, À propos) et permissions (INTERNET, VIBRATE)
├── java/biz/glieunou/meteo/
│   ├── MainActivity.java             # Écran principal : ville mise en avant, liste, appels à l'API météo
│   ├── City.java                     # Boîte de dialogue d'ajout d'une ville
│   ├── Fenetre.java                  # Boîte de dialogue des actions sur une ville
│   ├── About.java                    # Écran « À propos »
│   ├── Ville.java                    # Modèle de données d'une ville
│   ├── Database.java                 # Création / mise à jour de la base SQLite
│   └── Query.java                    # Accès aux données (CRUD des villes)
└── res/                              # Layouts, menus, chaînes (en / fr), images
build.gradle, settings.gradle         # Configuration Gradle
gradlew, gradlew.bat, gradle/         # Wrapper Gradle
```

## Installation et lancement

Le projet utilise des versions anciennes de l'outillage Android (Gradle 2.2.1, Android Gradle Plugin 1.0.0, build-tools 22.0.1) : il faut un JDK et un SDK Android compatibles (ou mettre à jour ces versions).

- Ouvrir le dossier du projet dans Android Studio, puis lancer l'application sur un émulateur ou un appareil.
- Ou en ligne de commande :

  ```bash
  ./gradlew assembleDebug      # génère app/build/outputs/apk/
  ./gradlew installDebug       # installe sur un appareil connecté
  ```

## Configuration

Aucun fichier `.env` n'est utilisé. L'URL de l'API OpenWeatherMap est définie dans `MainActivity.java` (constante `BASEURL`). L'API OpenWeatherMap exige désormais une clé d'API (paramètre `appid`) : ajoutez-la à la requête si nécessaire. Le chemin du SDK Android se renseigne dans `local.properties` (`sdk.dir`), fichier non versionné.
