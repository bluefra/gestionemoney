package gestionemoney.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import gestionemoney.compose.dashboard.Dashboard
import gestionemoney.compose.dashboard.DashboardNavigation
import gestionemoney.compose.expense.ExpenseNavigation
import gestionemoney.compose.expense.ExpensePage
import gestionemoney.compose.expense.NewExpense
import gestionemoney.compose.expense.NewExpenseNavigation
import gestionemoney.compose.homepage.HomeNavigation
import gestionemoney.compose.homepage.Homepage
import gestionemoney.compose.loading.CreateLoading
import gestionemoney.compose.newcategory.NewCategory
import gestionemoney.compose.newcategory.NewCategoryNavigation
import gestionemoney.compose.register_login.Login
import gestionemoney.compose.register_login.Register
import gestionemoney.compose.setting_page.SettingPageNavigation
import gestionemoney.compose.statistic_page.StatisticPageNavigation
import gestionemoney.compose.userpage.Userpage
import gestionemoney.compose.userpage.UserpageNavigation

// Composable function used by the navController to change the displayed page.

@Composable
fun NavigationGraph (
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route
    ) {
        // Every call to the following composable function link the Screen(page) to the corresponding composable function
        composable(route = Screens.Homepage.route) {
            HomeNavigation(navController)
        }
        composable(route = Screens.NewCategory.route) {
            NewCategoryNavigation(navController)
        }

        composable(Screens.ExpensePage.route + "/{categoryName}") {backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName")
            categoryName?.let {
                ExpenseNavigation(navController, categoryName)
            } ?: run {
                HomeNavigation(navController)
            }
        }

        composable(route = Screens.UserPage.route) {
            UserpageNavigation(navController)
        }
        composable(route = Screens.Dashboard.route) {
            DashboardNavigation(navController)
        }
        composable(route = Screens.NewExpense.route + "/{categoryName}") {backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName")
            NewExpenseNavigation(navController, categoryName)
        }
        composable(route = Screens.Register.route) {
            Register(navController)
        }
        composable(route = Screens.Login.route) {
            Login(navController)
        }
        composable(route = Screens.Loading.route + "?name={name}&surname={surname}") {backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            val surname = backStackEntry.arguments?.getString("surname")
            CreateLoading(navController, name, surname)
        }

        composable(route = Screens.SettingPage.route){
            SettingPageNavigation(navController)
        }

        composable(route = Screens.StatisticPage.route){
            StatisticPageNavigation(navController)
        }

    }
}