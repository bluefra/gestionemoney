package gestionemoney.compose.navigation

sealed class Screens (val route: String) {
    object Homepage : Screens(route = "HomepageScreen")
    object NewCategory : Screens(route = "NewCategoryScreen")
    object ExpensePage : Screens(route = "ExpenseScreen")
    object Dashboard : Screens(route = "DashboardScreen")
    object UserPage : Screens(route = "UserPageScreen")
    object NewExpense : Screens(route = "AddActivityPage")

    object Register : Screens(route = "RegisterScreen")
    object Login : Screens(route = "LoginScreen")
    object Loading : Screens(route = "LoadingScreen")

    object SettingPage : Screens(route = "SettingPageScreen")
}