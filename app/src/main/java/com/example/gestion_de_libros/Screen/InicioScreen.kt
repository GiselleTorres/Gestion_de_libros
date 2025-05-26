package com.example.gestion_de_libros.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    navController: NavController,
    token: String
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Inicio") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Menú Biblioteca Municipal",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val menuItems = listOf(
                "Categorías" to "categorias/$token",
                "Libros" to "libros/$token",
                "Préstamos" to "prestamos/$token",
                "Usuarios" to "usuarios/$token"
            )

            menuItems.sortedBy { it.first }.forEach { (title, route) ->
                Button(
                    onClick = { navController.navigate(route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(title)
                }
            }
        }
    }
}