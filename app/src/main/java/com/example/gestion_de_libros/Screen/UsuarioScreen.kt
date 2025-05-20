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
import com.example.gestion_de_libros.Repository.UsuarioRepository
import com.example.gestion_de_libros.ViewModel.UsuarioViewModel
import com.example.gestion_de_libros.Model.Usuario
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuarioScreen(
    navController: NavController,
    vm: UsuarioViewModel,
    token: String
) {
    var showDialog by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<Usuario?>(null) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    val usuarios by vm.usuarios.collectAsState()
    LaunchedEffect(Unit) { vm.loadUsuarios(token) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Usuarios") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isEditing = false
                        selectedUser = null
                        name = ""; email = ""; phone = ""
                        showDialog = true
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar usuario")
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
            items(usuarios) { user ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(user.nombre, style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(4.dp))
                            Text(user.email, style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.height(2.dp))
                            Text(user.telefono, style = MaterialTheme.typography.bodySmall)
                        }
                        Row {
                            IconButton(onClick = {
                                isEditing = true
                                selectedUser = user
                                name = user.nombre
                                email = user.email
                                phone = user.telefono
                                showDialog = true
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar usuario")
                            }
                            IconButton(onClick = {
                                selectedUser = user
                                vm.deleteUsuario(token, user.idUsuario!!)
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar usuario")
                            }
                        }
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(if (isEditing) "Editar Usuario" else "Nuevo Usuario") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Nombre") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Teléfono") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        val u = Usuario(
                            idUsuario = if (isEditing) selectedUser?.idUsuario else null,
                            nombre = name,
                            email = email,
                            telefono = phone,
                            prestamos = selectedUser?.prestamos ?: emptyList()
                        )
                        if (isEditing) vm.updateUsuario(token, u) else vm.addUsuario(token, u)
                        showDialog = false
                    }) { Text("Guardar") }
                },
                dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancelar") } }
            )
        }
    }
}