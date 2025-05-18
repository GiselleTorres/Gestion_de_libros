package com.example.gestion_de_libros.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestion_de_libros.ViewModel.LibroViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.gestion_de_libros.Screen.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gestion_de_libros.Interfaces.*
import com.example.gestion_de_libros.Repository.*
import com.example.gestion_de_libros.ViewModel.*
import com.example.gestion_de_libros.Screen.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.runtime.LaunchedEffect
import com.google.gson.GsonBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configura Retrofit con Gson lenient para aceptar JSON no estrictamente formateado
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        // Instancia repositorios y viewmodels
        val api = retrofit.create(ApiService::class.java)
        val catRepo = CategoriaRepository(api)
        val libRepo = LibroRepository(api)
        val presRepo = PrestamoRepository(api)
        val usrRepo = UsuarioRepository(api)
        val catVm = CategoriaViewModel(catRepo)
        val libVm = LibroViewModel(libRepo)
        val presVm = PrestamoViewModel(presRepo)
        val usrVm = UsuarioViewModel(usrRepo)

        // Token de ejemplo; reemplaza con autenticación real
        val token = "TU_TOKEN_AQUI"

        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "start") {
                        // Ruta inicial que redirige a Categorías con token
                        composable("start") {
                            LaunchedEffect(Unit) {
                                navController.navigate("categorias/$token") {
                                    popUpTo("start") { inclusive = true }
                                }
                            }
                        }
                        composable(
                            "categorias/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStack ->
                            val argToken = backStack.arguments!!.getString("token")!!
                            CategoriaScreen(navController, catVm, argToken)
                        }
                        composable(
                            "libros/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStack ->
                            val argToken = backStack.arguments!!.getString("token")!!
                            LibroScreen(navController, libVm, argToken)
                        }
                        composable(
                            "prestamos/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStack ->
                            val argToken = backStack.arguments!!.getString("token")!!
                            PrestamoScreen(navController, presVm, argToken)
                        }
                        composable(
                            "usuarios/{token}",
                            arguments = listOf(navArgument("token") { type = NavType.StringType })
                        ) { backStack ->
                            val argToken = backStack.arguments!!.getString("token")!!
                            UsuarioScreen(navController, usrVm, argToken)
                        }
                    }
                }
            }
        }
    }
}




