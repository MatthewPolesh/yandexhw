package com.example.todo.presentation.uicomponents.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo.presentation.MainViewModel
import com.example.todo.presentation.uicomponents.EditMenu
import com.example.todo.presentation.uicomponents.MainMenu


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
            MainMenu(
                counter = mainViewModel.repository.completedItemsCount.collectAsState(),
                itemsList = mainViewModel.repository.itemsList.collectAsState(),
                onDeleteRequest = { mainViewModel.deleteItem(it) },
                onAcceptRequest = { mainViewModel.completeItem(it) },
                onClickCheckboxRequest = { mainViewModel.completeItem(it) },
                onClickInfoRequest = {
                    mainViewModel.changeSelectedItem(it)
                    navHostController.navigate(Screens.SCREEN2.value)},
                onCreateRequest = {
                    mainViewModel.selectItem(mainViewModel.basicItem)
                    navHostController.navigate(Screens.SCREEN2.value)}
                )
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
                                importance, completed, description, deadLine ->
                    mainViewModel.addItem(importance = importance, completed = completed, description = description, deadline = deadLine)
                    navHostController.navigate(Screens.SCREEN1.value)
                },
                onUpdateRequest = {
                        id, importance, completed, description, deadLine, createdAt ->
                    mainViewModel.updateItem(id = id , importance = importance, completed = completed, description = description, deadline = deadLine, createdAt = createdAt)
                    navHostController.navigate(Screens.SCREEN1.value)
                }
            )
        }

    }
}
