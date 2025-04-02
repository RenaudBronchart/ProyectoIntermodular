package com.example.appcrud.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appcrud.R

@Composable
fun PaginaBienvenida(navController: NavHostController) {
    // Box para el contenido de la pagina
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Padding autour du contenu
    ) {
        // back pagina con color prmaria MaterialTheme
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        )

        // contenido centrado
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),
            verticalArrangement = Arrangement.Center, // centrado verticalmente
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Titre "Activi+" en haut
            Text(
                text = "Activi+",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Yellow,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Logo centré
            Image(
                painter = painterResource(id = R.drawable.icontactivi), // reemplacar el logo
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(150.dp) // Ajustar el logo, el size
                    .padding(bottom = 32.dp) // espacio, logo buton
            )

            // Bouton "Entrar" sous le logo
            Button(
                onClick = { navController.navigate("MenuInicio") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .widthIn(min = 200.dp)
                    .height(50.dp) //
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Entrar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaginaBienvenidaPreview() {
    val fakeNavController = rememberNavController()
    PaginaBienvenida(navController = fakeNavController)
}