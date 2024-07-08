package com.sali.habitino.view.screen

sealed class Screens(val route: String) {

    data object MainHabitScreen : Screens(route = "MainHabitScreen")

    data object HabitScreen : Screens(route = "HabitScreen/{title}/{description}/{state}/{isDone}")

}