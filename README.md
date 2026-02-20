# 📱 Proyecto Final – App Android (Kotlin)

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-orange)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-12+-green)](https://developer.android.com/)
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

---

## 📌 Descripción

Este repositorio contiene la **aplicación Android desarrollada en Kotlin** para el Proyecto Final de Desarrollo Multiplataforma.  

La app consume la **API REST del backend Spring Boot**, mostrando datos de usuarios, productos y pedidos, y permitiendo interacciones como crear, listar o modificar registros.  

Está diseñada siguiendo **arquitectura MVVM**, con Retrofit para la comunicación HTTP y Room/SharedPreferences para almacenamiento local si es necesario.

---

## ⚙️ Tecnologías utilizadas

- **Kotlin** – Lenguaje principal  
- **Android Studio (2023+)** – IDE recomendado  
- **Retrofit 2 / OkHttp** – Consumo de API REST  
- **MVVM + ViewModel + LiveData** – Arquitectura para separación de responsabilidades  
- **Hilt/Dagger (opcional)** – Inyección de dependencias  
- **Material Components** – UI moderna y consistente  

---

## 🏗️ Estructura del proyecto

```text
com/tuempresa/proyectofinal
│
├─ model/           → Data classes que reflejan las entidades del backend
├─ network/         → Retrofit interfaces y API client
├─ repository/      → Maneja la lógica de obtención de datos (API o cache)
├─ viewmodel/       → ViewModels que exponen LiveData a la UI
├─ ui/              → Activities y Fragments organizados por feature
│   ├─ main/        → Pantallas principales
│   ├─ login/       → Login y registro
│   └─ product/     → Gestión de productos y pedidos
├─ util/            → Constantes, helpers, extensiones
└─ App.kt           → Clase Application para inicialización global
