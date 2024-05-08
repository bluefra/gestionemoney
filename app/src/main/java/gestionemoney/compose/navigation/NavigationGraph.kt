package gestionemoney.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import gestionemoney.compose.dashboard.Dashboard
import gestionemoney.compose.expense.ExpensePage
import gestionemoney.compose.expense.NewExpense
import gestionemoney.compose.homepage.Homepage
import gestionemoney.compose.newcategory.NewCategory
import gestionemoney.compose.userpage.Userpage

// Composable function used by the navController to change the displayed page.

@Composable
fun NavigationGraph (
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Homepage.route
    ) {
        // Every call to the following composable function link the Screen(page) to the corresponding composable function
        composable(route = Screens.Homepage.route) {
            Homepage(navController)
        }
        composable(route = Screens.NewCategory.route) {
            NewCategory(navController)
        }
        composable(route = Screens.ExpensePage.route) {
            ExpensePage(navController)
        }
        composable(route = Screens.UserPage.route) {
            Userpage(navController)
        }
        composable(route = Screens.Dashboard.route) {
            Dashboard(navController)
        }
        composable(route = Screens.NewExpense.route) {
            NewExpense(navController)
        }
    }
}