package com.example.gestion_de_libros.Screen

import androidx.compose.ui.tooling.preview.Preview
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestion_de_libros.Model.Categoria
import com.example.gestion_de_libros.ViewModel.CategoriaViewModel
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import android.net.Uri
import android.content.Intent
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.rememberNavController



@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriaScreen(
    navController: NavController = rememberNavController(),
    vm: CategoriaVie wModel = viewModel(),
    token: String = ""
) {
    val items by vm.categorias.collectAsState()
    LaunchedEffect(Unit) { vm.loadCategorias(token) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Categorías") }) }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .padding(8.dp)
        ) {
            items(items) { cat ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = cat.nombre,
                        modifier = Modifier
                            .padding(8.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}