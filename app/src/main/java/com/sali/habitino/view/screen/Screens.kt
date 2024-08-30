package com.sali.habitino.view.screen

sealed class Screens(val route: String) {

    companion object {
        private const val ROUTE_MAIN_HABIT_SCREEN = "MainHabitScreen"
        private const val ROUTE_APP_TRACK_SCREEN = "AppTrackScreen"
        private const val ROUTE_INSTALLED_APP_SCREEN = "InstalledAppScreen"
    }

    data object MainHabitScreen : Screens(route = ROUTE_MAIN_HABIT_SCREEN)

    data object AppTrackScreen : Screens(route = ROUTE_APP_TRACK_SCREEN)

    data object InstalledAppScreen : Screens(route = ROUTE_INSTALLED_APP_SCREEN)

}