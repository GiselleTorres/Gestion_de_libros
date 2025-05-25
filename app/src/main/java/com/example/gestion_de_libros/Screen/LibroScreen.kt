package com.example.gestion_de_libros.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestion_de_libros.Model.Categoria
import com.example.gestion_de_libros.Repository.CategoriaRepository
import com.example.gestion_de_libros.ViewModel.CategoriaViewModel
import com.example.gestion_de_libros.ViewModel.LibroViewModel
import com.example.gestion_de_libros.Model.Libro
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibroScreen(
    navController: NavController,
    vm: LibroViewModel,
    catVm: CategoriaViewModel,
    token: String
) {
    // Estados para diálogos y formulario
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var selectedLibro by remember { mutableStateOf<Libro?>(null) }
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }

    // Carga libros y categorías
    val libros by vm.libros.collectAsState()
    val categorias by catVm.categorias.collectAsState()
    LaunchedEffect(Unit) {
        vm.loadLibros(token)
        catVm.loadCategorias(token)
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategoria by remember { mutableStateOf<Categoria?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Libros") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Preparar formulario para nuevo libro
                        selectedLibro = null
                        title = ""; author = ""; year = ""; isbn = ""; selectedCategoria = null
                        showAddDialog = true
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar libro")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(libros) { libro ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(libro.titulo, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Autor: ${libro.autor}", style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("Año: ${libro.añoPublicacion}", style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("ISBN: ${libro.isbn}", style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                "Categoría: ${libro.categoria?.firstOrNull()?.nombre ?: "N/A"}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Row {
                            IconButton(onClick = {
                                // Editar libro
                                selectedLibro = libro
                                title = libro.titulo
                                author = libro.autor
                                year = libro.añoPublicacion.toString()
                                isbn = libro.isbn
                                selectedCategoria = libro.categoria?.firstOrNull()
                                showEditDialog = true
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar libro")
                            }
                            IconButton(onClick = {
                                selectedLibro = libro
                                showDeleteConfirm = true
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar libro")
                            }
                        }
                    }
                }
            }
        }

        // Diálogo para añadir/editar
        val isEditing = showEditDialog
        if (showAddDialog || showEditDialog) {
            AlertDialog(
                onDismissRequest = {
                    showAddDialog = false
                    showEditDialog = false
                },
                title = { Text(if (isEditing) "Editar Libro" else "Nuevo Libro") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Título") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = author,
                            onValueChange = { author = it },
                            label = { Text("Autor") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                        OutlinedTextField(
                            value = year,
                            onValueChange = { year = it },
                            label = { Text("Año") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                        OutlinedTextField(
                            value = isbn,
                            onValueChange = { isbn = it },
                            label = { Text("ISBN") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
                                if (expanded) catVm.loadCategorias(token)
                            }
                        ) {
                            OutlinedTextField(
                                value = selectedCategoria?.nombre
                                    ?: "Selecciona categoría",
                                onValueChange = {},
                                label = { Text("Categoría") },
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                                modifier = Modifier.fillMaxWidth()
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                categorias.forEach { cat ->
                                    DropdownMenuItem(
                                        text = { Text(cat.nombre) },
                                        onClick = {
                                            selectedCategoria = cat
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        val libroObj = Libro(
                            idLibro = selectedLibro?.idLibro,
                            titulo = title,
                            autor = author,
                            añoPublicacion = year.toIntOrNull() ?: 0,
                            isbn = isbn,
                            categoria = selectedCategoria?.let { listOf(it) }
                        )
                        if (isEditing) vm.updateLibro(token, libroObj) else vm.addLibro(token, libroObj)
                        showAddDialog = false
                        showEditDialog = false
                    }) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showAddDialog = false
                        showEditDialog = false
                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // Confirmación eliminación
        if (showDeleteConfirm) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirm = false },
                title = { Text("Eliminar Libro") },
                text = { Text("¿Estás seguro de eliminar '${selectedLibro?.titulo}'?") },
                confirmButton = {
                    TextButton(onClick = {
                        selectedLibro?.idLibro?.let { vm.deleteLibro(token, it) }
                        showDeleteConfirm = false
                    }) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteConfirm = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
