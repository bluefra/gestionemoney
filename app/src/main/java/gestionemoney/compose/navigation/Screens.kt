package gestionemoney.compose.navigation

sealed class Screens (val route: String) {
    data object Homepage : Screens(route = "HomepageScreen")
    data object NewCategory : Screens(route = "NewCategoryScreen")
    data object ExpensePage : Screens(route = "ExpenseScreen")
    data object Dashboard : Screens(route = "DashboardScreen")
    data object UserPage : Screens(route = "UserPageScreen")
    data object NewExpense : Screens(route = "AddActivityPage")
    data object Register : Screens(route = "RegisterScreen")
    data object Login : Screens(route = "LoginScreen")
    data object Loading : Screens(route = "LoadingScreen")
    data object SettingPage : Screens(route = "SettingPageScreen")
    data object StatisticPage : Screens(route = "StatisticPage")
}