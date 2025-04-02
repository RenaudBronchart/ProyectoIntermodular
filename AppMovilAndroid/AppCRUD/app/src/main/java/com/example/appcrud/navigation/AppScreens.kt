package com.example.appcrud.navigation

sealed class AppScreens(val ruta: String) {
    object PaginaBienvenida: AppScreens("PaginaBienvenida")
    object MenuInicio: AppScreens("MenuInicio")
    object CrearActividad: AppScreens("CrearActividad")
    object EditarActividad: AppScreens("EditarActividad")
    object BorrarActividad: AppScreens("BorrarActividad")
    object ListaActividad: AppScreens("ListaActividad")
}
