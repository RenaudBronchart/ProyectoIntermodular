package com.example.appcrud.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appcrud.screens.BorrarActividad
import com.example.appcrud.screens.BorrarActividadPreview
import com.example.appcrud.screens.CrearActividad
import com.example.appcrud.screens.EditarActividad
import com.example.appcrud.screens.MenuInicio
import com.example.appcrud.screens.ListaActividad
import com.example.appcrud.screens.PaginaBienvenida

@Composable // indica pertence a Jetpack copose y defina parta IU
fun AppNavigation() {
    val navigationController = rememberNavController() // para crear instancia del controlador navegacion
    NavHost(
        navController = navigationController,
        startDestination = AppScreens.PaginaBienvenida.ruta // para coger primera pagina app que es paginaBienvenida..
    ) {
        composable(AppScreens.PaginaBienvenida.ruta) {
            PaginaBienvenida(navigationController)
        }

        composable(AppScreens.MenuInicio.ruta) {
            MenuInicio(navigationController)
        }
        composable(AppScreens.CrearActividad.ruta) {
            CrearActividad(navigationController)
        }

        composable(AppScreens.ListaActividad.ruta) {
            ListaActividad(navigationController)
        }

        composable(AppScreens.EditarActividad.ruta) {
            EditarActividad(navigationController)
        }

        composable(AppScreens.BorrarActividad.ruta) {
            BorrarActividad(navigationController)
        }


    }
}