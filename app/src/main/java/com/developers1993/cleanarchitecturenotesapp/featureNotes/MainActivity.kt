package com.developers1993.cleanarchitecturenotesapp.featureNotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.developers1993.cleanarchitecturenotesapp.featureNotes.presentation.add_edit.AddEditNotesScreen
import com.developers1993.cleanarchitecturenotesapp.featureNotes.presentation.list.NotesListScreen
import com.developers1993.cleanarchitecturenotesapp.featureNotes.presentation.util.Screen
import com.developers1993.cleanarchitecturenotesapp.ui.theme.CleanArchitectureNotesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleanArchitectureNotesAppTheme {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesListScreen.route
                    ) {
                        composable(Screen.NotesListScreen.route) {
                            NotesListScreen(navController)
                        }
                        composable(
                            route = Screen.NotesAddEditScreen.route + "?noteId={noteId}",
                            arguments = listOf(navArgument(name = "noteId") {
                                type = NavType.IntType
                                defaultValue = -1
                            })
                        ) {
                            AddEditNotesScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

