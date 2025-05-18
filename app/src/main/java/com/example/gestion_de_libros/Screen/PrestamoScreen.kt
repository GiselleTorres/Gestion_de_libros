package com.example.gestion_de_libros.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestion_de_libros.ViewModel.PrestamoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrestamoScreen(navController: NavController, vm: PrestamoViewModel, token: String) {
    val items by vm.prestamos.collectAsState()
    LaunchedEffect(Unit) { vm.loadPrestamos(token) }

    Scaffold(topBar = { TopAppBar(title = { Text("Préstamos") }) }) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.padding(8.dp)
        ) {
            items(items) { p ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("ID: ${p.idPrestamo}")
                        Text("Inicio: ${p.fechaInicio} - Fin: ${p.fechaFin}")
                    }
                }
            }
        }
    }
}
