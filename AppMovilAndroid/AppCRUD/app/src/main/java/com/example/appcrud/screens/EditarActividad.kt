package com.example.appcrud.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.appcrud.navigation.AppNavigation
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.E


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarActividad(navController: NavHostController) {

    var datos by remember { mutableStateOf("") }
    var nombreActividad by remember { mutableStateOf("") }
    var precioPorActividad by remember { mutableStateOf("") }
    var nombreActividadBusqueda by remember { mutableStateOf("") }
    var personasPorActividad by remember { mutableStateOf("") }
    val db = FirebaseFirestore.getInstance()
    val coleccion = "actividades"
    var mensajeConfirmacion by remember { mutableStateOf("") }

    val precioTotalActividad = calcularTotal(
        precioPorActividad.toDoubleOrNull() ?: 0.0,
        personasPorActividad.toIntOrNull() ?: 0
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.Yellow,
                ),
                title = { Text("Editar Actividad") },
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
                        .padding(horizontal = 16.dp), // Ajoute un peu de padding horizontal
                    horizontalArrangement = Arrangement.Center // Centre les éléments dans la ligne
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.clickable { navController.navigate("MenuInicio") }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home, // Icône "Inicio"
                            contentDescription = "Inicio"
                        )
                        Text(
                            text = "Inicio",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontSize = 14.sp

                        )
                    }

                    Spacer(modifier = Modifier.width(32.dp)) // Espace entre les boutons


                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.clickable { navController.navigate("") }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person, // Icône "Perfil"
                            contentDescription = "Perfil"
                        )
                        Text(
                            text = "Perfil",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(32.dp)) // Espace entre les boutons


                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.clickable { navController.navigate("") }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
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
                .padding(horizontal = 10.dp)
        ) {
            // Demander le nom de l'activité à rechercher
            OutlinedTextField(
                value = nombreActividadBusqueda,
                onValueChange = { nombreActividadBusqueda = it },
                label = { Text("Introduce el nombre de actividad") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Bouton pour charger les données
            Button(
                onClick = {
                    nombreActividad = ""
                    precioPorActividad = ""
                    personasPorActividad = ""
                    datos = ""

                    db.collection(coleccion)
                        .whereEqualTo("nombreActividad", nombreActividadBusqueda.trim())
                        .get()
                        .addOnSuccessListener { resultado ->
                            Log.d("FIRESTORE", "Consulta realizada correctamente")
                            if (resultado.isEmpty) {
                                datos = "No existen datos"
                                Log.d("FIRESTORE", "Resultado vacío")
                            } else {
                                for (encontrado in resultado) {
                                    Log.d("FIRESTORE", "Documento encontrado: ${encontrado.id}")
                                    datos += "${encontrado.id}: ${encontrado.data}\n"
                                    nombreActividad = encontrado.getString("nombreActividad").orEmpty()
                                    precioPorActividad = encontrado.getString("precioPorActividad").orEmpty()
                                    personasPorActividad = encontrado.getString("personasPorActividad").orEmpty()
                                }
                            }
                        }
                        .addOnFailureListener {
                            datos = "Error de conexión Firestore"
                            Log.e("FIRESTORE", "Error en la consulta", it)
                        }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Yellow
                ),

            ) {
                Text(text = "Cargar Datos")
            }

            // mostrar datos if cargados
            if (datos.isNotBlank()) {
                Spacer(modifier = Modifier.size(16.dp))

                InputField(
                    value = nombreActividad,
                    onValueChange = { nombreActividad = it },
                    label = "Nombre Actividad"
                )

                Spacer(modifier = Modifier.size(16.dp))

                InputField(
                    value = precioPorActividad,
                    onValueChange = {
                        if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                            precioPorActividad = it
                        }
                    },
                    label = "Precio",
                    isNumeric = true
                )

                Spacer(modifier = Modifier.size(16.dp))

                InputField(
                    value = personasPorActividad,
                    onValueChange = {
                        if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                            personasPorActividad = it
                        }
                    },
                    label = "Número de Personas",
                    isNumeric = true
                )

                Spacer(modifier = Modifier.size(16.dp))

                OutlinedTextField(
                    value = precioTotalActividad.toString(),
                    onValueChange = {},
                    label = { Text("Precio Total de Actividad") },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(16.dp))

                // Buton para guardar
                Button(
                    onClick = {
                        // los campos debe ser completados, todos
                        if (nombreActividad.isBlank() || precioPorActividad.isBlank() || personasPorActividad.isBlank()) {
                            mensajeConfirmacion = "Error: Todos los campos deben estar completos"
                            return@Button
                        }

                        // verificar los campos
                        val precioValido = precioPorActividad.toDoubleOrNull()
                        val personasValido = personasPorActividad.toIntOrNull()
                        if (precioValido == null || personasValido == null) {
                            mensajeConfirmacion = "Error: Introduce valores numéricos válidos"
                            return@Button
                        }


                        val dato = hashMapOf(
                            "nombreActividad" to nombreActividad.trim(),
                            "precioPorActividad" to precioPorActividad,
                            "personasPorActividad" to personasPorActividad,
                            "precioTotalActividad" to precioTotalActividad.toString(),
                        )

                        // Para guardar los datos en la db
                        db.collection(coleccion)
                            .document(nombreActividad)
                            .set(dato)
                            .addOnSuccessListener {
                                mensajeConfirmacion = "Actividad modificada con éxito"
                                // poner los datos sin nada despues de guardalos/ modificarlos
                                nombreActividad = ""
                                precioPorActividad = ""
                                personasPorActividad = ""
                            }
                            .addOnFailureListener {
                                mensajeConfirmacion = "Error al modificar la actividad"
                            }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Modificar y Guardar",
                        color = Color.Yellow,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.size(5.dp))

                // Message de confirmation
                Text(
                    text = mensajeConfirmacion,
                    color = if (mensajeConfirmacion.contains("éxito")) Color.Black else Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isNumeric: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = if (isNumeric) KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) else KeyboardOptions.Default,
        modifier = Modifier.fillMaxWidth()
    )
}

