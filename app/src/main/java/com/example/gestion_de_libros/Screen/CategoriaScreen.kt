package com.example.gestion_de_libros.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.gestion_de_libros.Model.Categoria
import com.example.gestion_de_libros.ViewModel.CategoriaViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.rememberCoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CategoriaScreen(
    navController: NavController = rememberNavController(),
    vm: CategoriaViewModel = viewModel(),
    token: String = ""
) {
    val categorias by vm.categorias.collectAsState()
    LaunchedEffect(Unit) { vm.loadCategorias(token) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Categorías") }) }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(categorias) { categoria ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            // Aquí podrías navegar, por ejemplo:
                            // navController.navigate("libros/${token}?categoria=${categoria.id}")
                        },
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = categoria.nombre,
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
