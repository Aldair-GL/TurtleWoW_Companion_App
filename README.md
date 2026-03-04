# 📱 Proyecto Final – App Android (Kotlin)

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-orange)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-12+-green)](https://developer.android.com/)
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

---

## 📌 Descripción

Este repositorio contiene la **aplicación Android desarrollada en Kotlin** para el Proyecto Final de Desarrollo Multiplataforma.  

La app está orientada a ser una **companion app de Turtle WoW**, con secciones de:
- Objetos
- Lore
- Mapas
- Clases

La aplicación sigue una base **MVVM**, consume backend mediante Retrofit y mantiene datos locales con Room para uso offline.

---

## ⚙️ Tecnologías utilizadas

- **Kotlin** – Lenguaje principal  
- **Android Studio (2023+)** – IDE recomendado  
- **Retrofit 2 / OkHttp** – Consumo de API REST  
- **MVVM + ViewModel + LiveData** – Arquitectura para separación de responsabilidades  
- **Hilt/Dagger (opcional)** – Inyección de dependencias  
- **Material Components** – UI moderna y consistente  

---

## ✅ Estado actual del desarrollo

- UI principal en español con pestañas por categoría (Objetos, Lore, Mapas, Clases)
- Persistencia local con Room (`guide_entries`)
- Cliente Retrofit preparado para backend Spring Boot (`http://10.0.2.2:8080/api/guia/{category}`)
- Repositorio con fallback local (seed data) cuando la API no está disponible

## 🏗️ Estructura del proyecto

```text
com/example/turtlewowcompanion
│
├─ data/
│  ├─ local/        → Room (DB, DAO, Entity)
│  ├─ remote/       → Retrofit API client
│  └─ GuideRepository.kt
├─ ui/
│  └─ GuideViewModel.kt
└─ MainActivity.kt  → UI Compose principal
