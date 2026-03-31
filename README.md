# Turtle WoW Companion

App Android tipo enciclopedia de World of Warcraft Vanilla, desarrollada como proyecto final del ciclo de Desarrollo Multiplataforma.

## Descripción

Aplicación móvil que permite consultar información sobre zonas, quests, NPCs y más del universo WoW clásico. Consume un backend propio en Spring Boot y mantiene caché local para uso offline.

## Tecnologías

- **Kotlin** + Jetpack Compose
- **MVVM** con ViewModel y StateFlow
- **Room** para persistencia local (caché de datos, favoritos, historial de búsqueda)
- **DataStore Preferences** para ajustes de usuario (tema, configuración)
- **Retrofit 2** + OkHttp para consumo de API REST
- **Navigation Compose** para navegación entre pantallas
- **Coil** para carga de imágenes
- **Material 3** con tema personalizado inspirado en WoW

## Estructura del proyecto

```
com.example.turtlewowcompanion/
├── di/                    → Inyección de dependencias manual
├── domain/model/          → Modelos de dominio
├── data/
│   ├── remote/            → API Retrofit + DTOs
│   ├── local/             → Room (entities, DAOs, database)
│   ├── mapper/            → Conversión entre capas
│   └── repository/        → Repositorios con lógica de datos
└── ui/
    ├── common/            → Componentes reutilizables
    ├── navigation/        → Rutas y barra de navegación
    ├── screens/           → Pantallas (Home, Zones, Quests, NPCs, Search, Favorites, Settings)
    └── theme/             → Colores, tipografía y tema
```

## Pantallas

- **Home** — Dashboard con acceso a las secciones principales
- **Zonas** — Listado y detalle de zonas del juego
- **Quests** — Listado y detalle de misiones
- **NPCs** — Listado y detalle de personajes no jugables
- **Búsqueda** — Búsqueda global con historial local
- **Favoritos** — Elementos guardados por el usuario
- **Ajustes** — Preferencias de tema y configuración

## Backend

La app se conecta a un backend Spring Boot propio. Endpoints:

- `GET /api/zones` · `GET /api/zones/{id}`
- `GET /api/quests` · `GET /api/quests/{id}`
- `GET /api/npcs` · `GET /api/npcs/{id}`
- `GET /api/search?q={term}`

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
