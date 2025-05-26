package com.example.gestion_de_libros.Screen

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestion_de_libros.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    navController: NavController,
    token: String
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Inicio") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Imagen de fondo tipo marca de agua
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.udec_logo),
                    contentDescription = "Marca de agua",
                    modifier = Modifier.size(180.dp),
                    contentScale = ContentScale.Fit,
                    alpha = 0.07f
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(title)
                            }
                        }
                    }
                }

                // Botón cerrar sesión
                Button(
                    onClick = {
                        val sharedPref = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
                        sharedPref.edit().remove("token").apply()

                        scope.launch {
                            navController.navigate("splash") {
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {
                    Text("Cerrar sesión", color = MaterialTheme.colorScheme.onError)
                }

                // Botón salir de la aplicación
                Button(
                    onClick = {
                        val activity = context as? Activity
                        activity?.finishAffinity()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Text("Salir de la aplicación", color = MaterialTheme.colorScheme.onSecondary)
                }
            }
        }
    }
}
