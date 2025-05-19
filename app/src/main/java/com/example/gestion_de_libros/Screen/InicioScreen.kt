package com.example.gestion_de_libros.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
        topBar = { TopAppBar(title = { Text("Menú principal") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { navController.navigate("usuarios/$token") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a Usuarios", style = MaterialTheme.typography.titleMedium)
            }
            Button(
                onClick = { navController.navigate("prestamos/$token") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a Préstamos", style = MaterialTheme.typography.titleMedium)
            }
            Button(
                onClick = { navController.navigate("libros/$token") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a Libros", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
