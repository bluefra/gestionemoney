package gestionemoney.compose.expense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.components.BackButton
import gestionemoney.compose.expense.components.CategoryOfExpense
import gestionemoney.compose.expense.components.CostOfExpense
import gestionemoney.compose.expense.components.DateOfExpense
import gestionemoney.compose.expense.components.LocationOfExpense
import gestionemoney.compose.expense.components.NewExpenseButton
import gestionemoney.compose.navigation.Screens

@Composable
fun NewExpense(
    navController: NavController
) {


    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        // Back button function.
        BackButton(navController)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            // Functions for user input data to create new expense.
            CategoryOfExpense()
            CostOfExpense()
            DateOfExpense()
            LocationOfExpense()
        }
        Row (
            horizontalArrangement = Arrangement.Center
        ){
            Button(
                onClick = { navController.navigate(Screens.ExpensePage.route)},
                colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
            ) {
                Text(text = "Conferma")
            }
        }

    }
}