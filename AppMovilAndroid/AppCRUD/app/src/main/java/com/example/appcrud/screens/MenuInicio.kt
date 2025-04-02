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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appcrud.R
import com.example.appcrud.navigation.AppNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuInicio(navController: NavHostController) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.Yellow,
                ),
                title = { Text("Menu") },
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
                    horizontalArrangement = Arrangement.Center // centrar elementos
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
                            imageVector = Icons.Filled.Person, //
                            contentDescription = "Perfil"
                        )
                        Text(
                            text = "Perfil",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(32.dp))


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
                .padding(top = 100.dp)
                .padding(start = 10.dp)
                .padding(end = 10.dp)
        ) {

            Button(
                onClick = { navController.navigate("CrearActividad") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Crear Actividad",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Button(
                onClick = { navController.navigate("ListaActividad") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Consultar Actividades",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Button(
                onClick = { navController.navigate("EditarActividad") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Editar Actividades",
                    color =  Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Button(
                onClick = { navController.navigate("BorrarActividad") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Borrar Actividades",
                    color = Color.White, // Couleur du texte
                    fontSize = 16.sp
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuInicialPreview() {
    val fakeNavController = rememberNavController()
    MenuInicio(navController = fakeNavController)
}
