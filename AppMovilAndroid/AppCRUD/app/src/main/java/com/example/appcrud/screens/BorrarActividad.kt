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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorrarActividad(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance();
    val coleccion = "actividades"
    var nombreActividad by remember { mutableStateOf("") }

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
                        .padding(horizontal = 16.dp), //
                    horizontalArrangement = Arrangement.Center //
                ) {
                    // Item Inicio con el texto
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

                    Spacer(modifier = Modifier.width(32.dp)) // Para poner espacio


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
                            imageVector = Icons.Filled.Menu, // Icône "Perfil"
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

            Spacer(modifier = Modifier.size(16.dp))

            OutlinedTextField(
                value = nombreActividad,
                onValueChange = { nombreActividad = it },
                label = { Text("Introduce el nombre a borrar") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )



            var mensajeConfirmacion by remember { mutableStateOf("") }

            Button(
                onClick = {

                    db.collection(coleccion)
                        .document(nombreActividad)
                        .delete()
                        .addOnSuccessListener {
                            Log.d("Firestore", "Document supprimé")
                            mensajeConfirmacion = "Datos borados correctamente"

                            nombreActividad = ""

                        }
                        .addOnFailureListener {
                            mensajeConfirmacion = "No se ha podido borrar"

                            nombreActividad = ""
                        }

                },

                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary // Fond bleu
                ),
                shape = RoundedCornerShape(8.dp) // Coins arrondis
            ) {
                Text(
                    text = "Borrar",
                    color = Color.Yellow, // Texte de la couleur de la bordure
                    fontSize = 16.sp
                )
            }


            // Button

            Spacer(modifier = Modifier.size(5.dp))
            Text(text = mensajeConfirmacion)

        } // Column


    }
}

@Preview(showBackground = true)
@Composable
fun BorrarActividadPreview() {
    val fakeNavController = rememberNavController()
    BorrarActividad(navController = fakeNavController)
}