package com.sali.habitino.view.screen

sealed class Screens(val route: String) {

    companion object {
        const val ROUTE_MAIN_HABIT_SCREEN = "MainHabitScreen"
    }

    data object MainHabitScreen : Screens(route = ROUTE_MAIN_HABIT_SCREEN)

}