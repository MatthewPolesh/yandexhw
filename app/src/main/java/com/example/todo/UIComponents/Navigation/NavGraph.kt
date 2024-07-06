package com.example.todo.UIComponents.Navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo.MainViewModel
import com.example.todo.UIComponents.EditMenu
import com.example.todo.UIComponents.MainMenu
import kotlinx.coroutines.launch


@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navHostController: NavHostController, mainViewModel: MainViewModel) {


    NavHost(
        navController = navHostController,
        startDestination = Screens.SCREEN1.value,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },

        )
    {
        composable(Screens.SCREEN1.value) {
            MainMenu(viewModel = mainViewModel, navHostController)
        }
        composable(Screens.SCREEN2.value) {

            EditMenu(
                item = mainViewModel.selectedItem.value,
                onDismissRequest = {navHostController.navigate(Screens.SCREEN1.value)},
                onDeleteRequest = {
                    mainViewModel.deleteItem(it)
                    navHostController.navigate(Screens.SCREEN1.value)
                                  },
                onSaveRequest = {
                    navHostController.navigate(Screens.SCREEN1.value)
                    mainViewModel.addItem(it)
                },
                onUpdateRequest = {
                    mainViewModel.updateItem(it)
                    navHostController.navigate(Screens.SCREEN1.value)
                }
            )
        }

    }
}
