package com.example.appcrud.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appcrud.R
import com.example.appcrud.navigation.AppScreens
import com.google.firebase.firestore.FirebaseFirestore


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun CrearActividad(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.Yellow,
                ),
                title = { Text("Crear Actividad") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("MenuInicio") }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Gray,
                contentColor = Color.White,
                modifier = Modifier.height(80.dp)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), // anadir pading
                    horizontalArrangement = Arrangement.Center // para centrar elementos
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.clickable { navController.navigate("MenuInicio") }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home, //
                            contentDescription = "Inicio"
                        )
                        Text(
                            text = "Inicio",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontSize = 14.sp

                        )
                    }

                    Spacer(modifier = Modifier.width(32.dp)) //

                    // Icono perfil con el texto
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.clickable { navController.navigate("") }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person, // Icono perfil
                            contentDescription = "Perfil"
                        )
                        Text(
                            text = "Perfil",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(32.dp)) // para poner espacio

                    // Icône "Perfil" avec texte sous l'icône
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.clickable { navController.navigate("") }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu, // icono "Perfil"
                            contentDescription = "Mas"
                        )
                        Text(
                            text = "Mas",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 100.dp)
                .padding(start = 10.dp)
                .padding(end = 10.dp)
        ) {
            Spacer(modifier = Modifier.size(16.dp))

            var nombreActividad by remember { mutableStateOf("") }

            OutlinedTextField(value = nombreActividad, onValueChange = { nombreActividad = it },
                label = { Text("Nombre Actividad ") }
            )

            Spacer(modifier = Modifier.size(16.dp))

            var precioPorActividad by remember { mutableStateOf("") }
            OutlinedTextField(
                value = precioPorActividad,
                onValueChange = {

                    if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                        precioPorActividad = it
                    }
                },
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.size(16.dp))

            var personasPorActividad by remember { mutableStateOf("") }
            OutlinedTextField(
                value = personasPorActividad,
                onValueChange = {
                    // Valider que l'entrée contient uniquement des chiffres
                    if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                        personasPorActividad = it
                    }
                },
                label = { Text("Número de Personas") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) // Afficher le clavier numérique
            )

            Spacer(modifier = Modifier.size(16.dp))

            var precioTotalActividad = calcularTotal(
                precioPorActividad.toDoubleOrNull() ?: 0.0,
                personasPorActividad.toIntOrNull() ?: 0
            )

            OutlinedTextField(
                value = precioTotalActividad.toString(),
                onValueChange = {},
                label = { Text("Precio Total de Actividad") },
                enabled = false
            )

            Spacer(modifier = Modifier.size(16.dp))

            val db = FirebaseFirestore.getInstance() //instancia de Firestore para realizar operaciones como leer o escribir datos.
            val coleccion = "actividades"

            var mensajeConfirmacion by remember { mutableStateOf("") }

            val dato = hashMapOf(  // Crea un mapa de datos clave-valor que se enviará a Firestore

                "nombreActividad" to nombreActividad,
                "precioPorActividad" to precioPorActividad,
                "personasPorActividad" to personasPorActividad,
                "precioTotalActividad" to precioTotalActividad.toString()
            )

            Button( // Botón para guardar datos
                onClick = {
                    db.collection(coleccion) // acceder a la coleccion que hemos creado actividades
                        .document(nombreActividad)
                        .set(dato)
                        .addOnSuccessListener {
                            mensajeConfirmacion = "Datos guardados correctamente"

                            nombreActividad = ""
                            precioPorActividad = ""
                            personasPorActividad = ""


                        }
                        .addOnFailureListener {
                            mensajeConfirmacion = "No se ha guardado correctamente"

                            nombreActividad = ""
                            precioPorActividad = ""
                            personasPorActividad = ""

                        }
                },

                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary // Fond bleu
                ),
                shape = RoundedCornerShape(8.dp) // bordes dedondeados
            ) {
                Text(
                    text = "Guardar",
                    color = Color.Yellow, // texto color
                    fontSize = 16.sp
                )
            }


            // Button

            Spacer(modifier = Modifier.size(5.dp))
            Text(text = mensajeConfirmacion)

        } // Column
    }


} //cierre Funcion clienteGuardar

fun calcularTotal(precioPorActividad: Double, personasPorActividad: Int): Double {
    return precioPorActividad * personasPorActividad
}


@Preview(showBackground = true)
@Composable
fun CrearActividadPreview() {

    val fakeNavController = rememberNavController()

    CrearActividad(navController = fakeNavController)
}