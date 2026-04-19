# Turtle WoW Companion

App Android tipo enciclopedia de World of Warcraft Vanilla, desarrollada como proyecto final del ciclo de Desarrollo Multiplataforma.

## Descripción

Aplicación móvil que permite consultar información sobre zonas, razas, clases y más del universo WoW clásico. Consume un backend propio en Spring Boot 3 + PostgreSQL (Neon) y mantiene caché local de zonas para uso offline.

## Tecnologías

- **Kotlin** + Jetpack Compose
- **MVVM** con ViewModel y StateFlow
- **Room** para persistencia local (caché de zonas, favoritos, historial de búsqueda)
- **DataStore Preferences** para ajustes de usuario
- **Retrofit 2** + OkHttp + Gson para consumo de API REST
- **Navigation Compose** para navegación con transiciones animadas
- **Material 3** con tema personalizado inspirado en WoW (glassmorphism, paleta de facciones)

## Estructura del proyecto

```
com.example.turtlewowcompanion/
├── di/                    → Inyección de dependencias manual (AppContainer)
├── domain/model/          → Zone, Race, WowClass, Faction, Profession, ...
├── data/
│   ├── remote/            → Retrofit API + DTOs (PagedResponseDto, ZoneDto, RaceDto, ...)
│   ├── local/             → Room (entities, DAOs, AppDatabase)
│   ├── mapper/            → Conversión DTO ↔ Entity ↔ Domain
│   └── repository/        → ZoneRepository, RaceRepository, WowClassRepository, ...
└── ui/
    ├── common/            → GlassCard, WowCardEnhanced, HeroHeader, FactionBadge, ...
    ├── navigation/        → Screen, NavGraph, BottomNavBar
    ├── screens/           → home, zones, races, classes, search, favorites, settings
    └── theme/             → Colores, tipografía, FactionTheme
```

## Pantallas

| Pantalla     | Descripción                                                    |
|--------------|----------------------------------------------------------------|
| **Home**     | Dashboard con hero banner y acceso visual a las 3 categorías   |
| **Zonas**    | 47 zonas paginadas del backend (mundo abierto + mazmorras)     |
| **Razas**    | 8 razas jugables con clases disponibles por facción            |
| **Clases**   | 9 clases de personaje con rol y recurso                        |
| **Búsqueda** | Búsqueda global con historial local                            |
| **Favoritos**| Elementos guardados por el usuario                             |
| **Ajustes**  | Preferencias de tema y configuración                           |

## Backend

URL base (emulador): `http://10.0.2.2:8084`

| Endpoint                    | Descripción                         |
|-----------------------------|-------------------------------------|
| `GET /api/v1/zones`         | Listado paginado de zonas           |
| `GET /api/v1/zones/{id}`    | Detalle de zona                     |
| `GET /api/v1/races`         | Listado de razas jugables           |
| `GET /api/v1/races/{id}`    | Detalle de raza                     |
| `GET /api/v1/classes`       | Listado de clases de personaje      |
| `GET /api/v1/classes/{id}`  | Detalle de clase                    |
| `GET /api/v1/factions`      | Listado de facciones                |
| `GET /api/v1/professions`   | Listado de profesiones              |

## Build

```bash
./gradlew :app:assembleDebug
```

## Tests

```bash
./gradlew :app:testDebugUnitTest
./gradlew :core:test
```

## Requisitos

- Android Studio 2024+
- JDK 17+
- SDK Android API 35
- Backend Spring Boot corriendo en localhost:8084
