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
import com.example.gestion_de_libros.Model.Prestamo
import com.example.gestion_de_libros.Model.Usuario
import com.example.gestion_de_libros.Model.Libro
import com.example.gestion_de_libros.ViewModel.PrestamoViewModel
import com.example.gestion_de_libros.ViewModel.UsuarioViewModel
import com.example.gestion_de_libros.ViewModel.LibroViewModel
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import java.time.LocalDate
import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun PrestamoScreen(
    navController: NavController,
    prestamoVm: PrestamoViewModel,
    usuarioVm: UsuarioViewModel,
    libroVm: LibroViewModel,
    token: String
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<Usuario?>(null) }
    var selectedBook by remember { mutableStateOf<Libro?>(null) }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    val prestamos by prestamoVm.prestamos.collectAsState()
    val usuarios by usuarioVm.usuarios.collectAsState()
    val libros by libroVm.libros.collectAsState()
    LaunchedEffect(Unit) {
        prestamoVm.loadPrestamos(token)
        usuarioVm.loadUsuarios(token)
        libroVm.loadLibros(token)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Préstamos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Nuevo préstamo")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.padding(8.dp)
        ) {
            items(prestamos) { p ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("ID: ${p.idPrestamo}")
                        Text("Usuario: ${p.usuario.firstOrNull()?.nombre}")
                        Text("Libro: ${p.libro.firstOrNull()?.titulo}")
                        Text("Inicio: ${p.fechaInicio} - Fin: ${p.fechaFin}")
                    }
                }
            }
        }

        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Nuevo Préstamo") },
                text = {
                    Column {
                        // Usuario dropdown
                        var expandedU by remember { mutableStateOf(false) }
                        OutlinedTextField(
                            value = selectedUser?.nombre ?: "Selecciona usuario",
                            onValueChange = {},
                            label = { Text("Usuario") },
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expandedU = true }
                        )
                        DropdownMenu(
                            expanded = expandedU,
                            onDismissRequest = { expandedU = false }
                        ) {
                            usuarios.forEach { u ->
                                DropdownMenuItem(
                                    text = { Text(u.nombre) },
                                    onClick = {
                                        selectedUser = u
                                        expandedU = false
                                    }
                                )
                            }
                        }

                        Spacer(Modifier.height(8.dp))
                        // Libro dropdown
                        var expandedL by remember { mutableStateOf(false) }
                        OutlinedTextField(
                            value = selectedBook?.titulo ?: "Selecciona libro",
                            onValueChange = {},
                            label = { Text("Libro") },
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expandedL = true }
                        )
                        DropdownMenu(
                            expanded = expandedL,
                            onDismissRequest = { expandedL = false }
                        ) {
                            libros.forEach { l ->
                                DropdownMenuItem(
                                    text = { Text(l.titulo) },
                                    onClick = {
                                        selectedBook = l
                                        expandedL = false
                                    }
                                )
                            }
                        }

                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = startDate,
                            onValueChange = { startDate = it },
                            label = { Text("Fecha inicio (YYYY-MM-DD)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = endDate,
                            onValueChange = { endDate = it },
                            label = { Text("Fecha fin (YYYY-MM-DD)") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (selectedUser != null && selectedBook != null) {
                            val pr = Prestamo(
                                idPrestamo = 0L,
                                usuario = listOf(selectedUser!!),
                                libro = listOf(selectedBook!!),
                                fechaInicio = LocalDate.parse(startDate),
                                fechaFin = LocalDate.parse(endDate)
                            )
                            prestamoVm.addPrestamo(token, pr)
                        }
                        showAddDialog = false
                    }) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

