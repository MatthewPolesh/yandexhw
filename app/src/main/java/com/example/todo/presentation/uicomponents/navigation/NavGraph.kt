package com.example.todo.presentation.uicomponents.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo.MainActivity
import com.example.todo.presentation.MainViewModel
import com.example.todo.presentation.uicomponents.EditMenu
import com.example.todo.presentation.uicomponents.ErrorDialog
import com.example.todo.presentation.uicomponents.MainMenu
import com.example.todo.presentation.uicomponents.SettingsMenu
import com.example.todo.presentation.uicomponents.AboutMenu
import com.example.todo.presentation.uicomponents.theme.AppTheme


@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    context: MainActivity
) {
    val error by mainViewModel.error.collectAsState()
    val errorDialog = remember { mutableStateOf(false) }
    val themeSettings by mainViewModel.settings.collectAsState()

    AppTheme(themeSettings = themeSettings) {
        LaunchedEffect(error) {
            if (error != "Nothing") errorDialog.value = true
        }
        if (errorDialog.value) {
            ErrorDialog(
                showDialog = errorDialog,
                error = error,
                onErrorAccept = {
                    mainViewModel.updateError("Nothing")
                    errorDialog.value = !errorDialog.value
                }
            )
        }

        NavHost(
            navController = navHostController,
            startDestination = Screens.SCREEN1.value,

            )
        {
            composable(
                Screens.SCREEN1.value,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(900)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(900)
                    )
                }
            ) {
                MainMenu(
                    counter = mainViewModel.repository.completedItemsCount.collectAsState(),
                    itemsList = mainViewModel.repository.itemsList.collectAsState(),
                    onDeleteRequest = { mainViewModel.deleteItem(it) },
                    onAcceptRequest = { mainViewModel.completeItem(it) },
                    onClickCheckboxRequest = { mainViewModel.completeItem(it) },
                    onClickInfoRequest = {
                        mainViewModel.changeSelectedItem(it)
                        navHostController.navigate(Screens.SCREEN2.value)
                    },
                    onCreateRequest = {
                        mainViewModel.selectItem(mainViewModel.basicItem)
                        navHostController.navigate(Screens.SCREEN2.value)
                    },

                    onSettingsRequest = { navHostController.navigate(Screens.SCREEN3.value) }
                )
            }
            composable(
                Screens.SCREEN2.value,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(900)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(900)
                    )
                }
            ) {
                EditMenu(
                    item = mainViewModel.selectedItem.value,

                    onDismissRequest = { navHostController.navigate(Screens.SCREEN1.value) },
                    onDeleteRequest = {
                        mainViewModel.deleteItem(it)
                        navHostController.navigate(Screens.SCREEN1.value)
                    },
                    onSaveRequest = { importance, completed, description, deadLine ->
                        mainViewModel.addItem(
                            importance = importance,
                            completed = completed,
                            description = description,
                            deadline = deadLine
                        )
                        navHostController.navigate(Screens.SCREEN1.value)
                    },
                    onUpdateRequest = { id, importance, completed, description, deadLine, createdAt ->
                        mainViewModel.updateItem(
                            id = id,
                            importance = importance,
                            completed = completed,
                            description = description,
                            deadline = deadLine,
                            createdAt = createdAt
                        )
                        navHostController.navigate(Screens.SCREEN1.value)
                    }
                )
            }
            composable(
                Screens.SCREEN3.value,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(900)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(900)
                    )
                }
            )
            {
                SettingsMenu(
                    selectedSetting = mainViewModel.settings.value,
                    onThemeClickRequest = { setting ->
                        mainViewModel.setThemeSettings(context, setting)
                    },
                    onSaveRequest = { navHostController.navigate(Screens.SCREEN1.value) },
                    onAppClickRequest = { navHostController.navigate(Screens.SCREEN4.value) }
                )
            }
            composable(
                Screens.SCREEN4.value,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(900)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(900)
                    )
                }
            )
            {
                AboutMenu(
                    activity = context,
                    onClick = { navHostController.navigate(Screens.SCREEN3.value) }
                )
            }
        }
    }
}
