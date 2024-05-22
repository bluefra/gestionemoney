package gestionemoney.compose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.navigation.Screens

// Generic composable function that display a back button used all over the app. (now it just point to the homepage)
@Composable
fun BackButton(
    navController: NavController,
) {
       Button(
           onClick = { navController.navigate(Screens.Homepage.route) },
           modifier = Modifier
              // .align(Alignment.BottomEnd)
               .padding(16.dp),
           colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
       ) {
           Text(text = stringResource(id = R.string.back_button))
       }
}


@Composable
fun NewCategoryButton (
    navController: NavController
) {
    Button(
        onClick = { navController.navigate(Screens.NewCategory.route) {
            popUpTo(0)
        } },
        colors = ButtonDefaults.buttonColors(colorResource(R.color.orange)),
    ) {
        Text(
            text = stringResource(id = R.string.new_category_button),
        )
    }
}

@Composable
fun NewExpenseButton (
    navController: NavController,
    categoryName : String
) {
    Button(
        onClick = {navController.navigate("${Screens.NewExpense.route}/$categoryName")},
        colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
    ) {
        Text(
            text = stringResource(id = R.string.new_expense_add) ,
        )
    }
}


