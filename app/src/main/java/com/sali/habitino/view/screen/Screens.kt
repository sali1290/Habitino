package com.sali.habitino.view.screen

sealed class Screens(val route: String) {

    companion object {
        //        private const val ROUTE_MAIN_HABIT_SCREEN = "MainHabitScreen"
        private const val ROUTE_TRACKED_APPS_SCREEN = "TrackedAppsScreen"
        private const val ROUTE_INSTALLED_APPS_SCREEN = "InstalledAppsScreen"
    }

    // Uncomment all comments when you decide to add main screen
//    data object MainHabitScreen : Screens(route = ROUTE_MAIN_HABIT_SCREEN)

    data object TrackedAppsScreen : Screens(route = ROUTE_TRACKED_APPS_SCREEN)

    data object InstalledAppsScreen : Screens(route = ROUTE_INSTALLED_APPS_SCREEN)

}