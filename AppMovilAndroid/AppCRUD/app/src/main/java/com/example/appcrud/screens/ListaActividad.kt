package com.example.appcrud.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appcrud.R
import com.example.appcrud.navigation.AppScreens
import com.example.appcrud.navigation.DocumentUtil
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ListaActividad(navController: NavHostController) {

    // instancia de FireBaseFireStore
    val db = FirebaseFirestore.getInstance()
    // Para poder almacenar la lista de actividades
    var listaActividades by remember { mutableStateOf(emptyList<Actividad>())} // emptyList() devuelve una lista inmutable vacía
    var totalMontant by remember { mutableStateOf(0.0) }
    var sortBy by remember { mutableStateOf("nombre") }
    var isAscending by remember { mutableStateOf(true) }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.Yellow,
                ),
                title = { Text("Lista Actividades") },
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
                // creacion Row, para luego, poner los colum, puesto de manera center
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // creacion Column y Icon de manera a tener cada Icon en el bottomApp en la misma linea
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

                    Spacer(modifier = Modifier.width(32.dp)) // espacio entre los Icons

                    // Icône "Perfil" avec texte sous l'icône
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
                    Spacer(modifier = Modifier.width(32.dp)) // espacio entre los Icons

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.clickable { navController.navigate("") }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu, //
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
        },
                floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("CrearActividad") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.Yellow
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar actividad")
            }
        },
        floatingActionButtonPosition = FabPosition.End // posicion centrado arriba de bottotamAppbar
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 10.dp)
        ) {
            DisposableEffect(true) {
                val job = CoroutineScope(Dispatchers.IO).launch {
                    // Obtener la lista de actividades de forma asincrona de la bdd
                    val actividades = getActividades()
                    listaActividades = actividades
                    totalMontant = actividades.sumOf { it.precioTotalActividad.toDoubleOrNull() ?: 0.0 }
                }

                onDispose {
                    // cancelar la coroutine cuando el DisponableEffect se desecha
                    job.cancel()
                }
            } // cierre DiposableEffect

            // Carta del total amount
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Costo total de las actividades",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = String.format("%.2f€", totalMontant),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        sortBy = "nombreActividad"
                        listaActividades = sortActividades(listaActividades, sortBy, isAscending)
                        isAscending = !isAscending // Alterner l'ordre à chaque clic
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.Yellow
                    )
                ) {
                    Text(text = if (sortBy == "nombreActividad" && isAscending) "Ordenar: Nombre A-Z" else "Ordenar: Nombre Z-A")
                }

                Button(
                    onClick = {
                        sortBy = "precioTotalActividad"
                        listaActividades = sortActividades(listaActividades, sortBy, isAscending)
                        isAscending = !isAscending // Alterner l'ordre à chaque clic
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.Yellow
                    )
                ) {
                    Text(text = if (sortBy == "precioTotalActividad" && isAscending) "Ordenar: Precio ⬆" else "Ordenar: Precio ⬇")
                }
            }



            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(listaActividades) { actividad ->
                    // mostrar cad actividad
                    ActividadItem(actividad, navController)
                }
            } // cierre lazyColum
        }
    }


} //cierre Funcion listaActividad


suspend fun getActividades(): List<Actividad> {
    val db = FirebaseFirestore.getInstance()
    val actividadesRef = db.collection("actividades")

    return try {
            // obtener un snapshot de la collecion de actividades de form asincrona
            val querySnapshot = actividadesRef.get().await()
            val actividades = mutableListOf<Actividad>()


        for(document in querySnapshot.documents) {
            val actividad = document.toObject(Actividad::class.java)

            actividad?.let {
                Log.d("DEBUG_FIRESTORE", "Actividad cargada: ${it.nombreActividad}")
                actividades.add(it) }

        }
        actividades
    }
    catch (e:Exception) {

        emptyList()
    }


}


@Composable
fun ActividadItem(actividad: Actividad, navController: NavHostController) {
    val context = LocalContext.current //

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Titlo actividad
            Text(
                text = actividad.nombreActividad,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // detalles actividad
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Precio: ${actividad.precioPorActividad}€",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Personas: ${actividad.personasPorActividad}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }


                Text(
                    text = "Total: ${actividad.precioTotalActividad}€",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // padar descargar PDF
            Button(
                onClick = {
                    val pdfFile = DocumentUtil().createPdfFile(context, actividad)
                    if (pdfFile != null) {
                        Toast.makeText(context, "PDF descargado : ${pdfFile.absolutePath}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Error porla creacion del PDF", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Yellow
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Descargar PDF")
            }
        }
    }
}
// funcion para sortear
fun sortActividades(
    actividades: List<Actividad>,
    sortBy: String,
    ascending: Boolean
): List<Actividad> {
    return when (sortBy) {
        "nombreActividad" -> if (ascending) {
            actividades.sortedBy { it.nombreActividad.lowercase()}
        } else {
            actividades.sortedByDescending { it.nombreActividad.lowercase() }
        }
        "precioTotalActividad" -> if (ascending) {
            actividades.sortedBy { it.precioTotalActividad.toDoubleOrNull() ?: 0.0 }
        } else {
            actividades.sortedByDescending { it.precioTotalActividad.toDoubleOrNull() ?: 0.0 }
        }
        else -> actividades
    }
}


@Preview(showBackground = true)
@Composable
fun ListaActividadPreview() {
    val fakeNavController = rememberNavController()

    ListaActividad(navController = fakeNavController)
}