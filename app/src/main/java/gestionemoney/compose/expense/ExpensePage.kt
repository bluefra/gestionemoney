package gestionemoney.compose.expense

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.components.BackButton
import gestionemoney.compose.components.NavigationDrawer
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.expense.components.NewExpenseButton
import gestionemoney.compose.homepage.Homepage
import gestionemoney.compose.model.Expense
import gestionemoney.compose.navigation.Screens
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ExpenseNavigation(
    navController: NavController,
    categoryName: String
){
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    NavigationDrawer(drawerState = drawerState , coroutineScope = coroutineScope , navController = navController,
        { ExpensePage(navController = navController, categoryName)}
    )
}

@Composable
fun ExpensePage(
    navController: NavController,
    categoryName: String
) {
    // Mapping the expenselist to the Expense data class. (database implementation)
    val expenses = UserWrapper.getInstance().getCategory(categoryName)?.getList()
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        // Back button function called.
        Row {
            Column (
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ){
                BackButton(navController)
            }
            Column (
                horizontalAlignment = Alignment.End
            ){
                Button(
                        onClick = { navController.navigate("${Screens.NewExpense.route}/$categoryName")},
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
                    ) {
                    Text(text = "new Expense")
                }
            }
        }


        // Draw a line to separate the two navigation bar
        Divider(
            color = colorResource(R.color.orangeUltraLight) ,
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )

        // Homepage navigation bar at the top of the screen. Include 2 buttons: UserPage and Dashboard
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = categoryName,
                    fontSize = 25.sp ,
                    fontFamily = FontFamily.Monospace ,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        // Draw a line to separate the content from the name category
        Divider(
            color = colorResource(R.color.orangeUltraLight) ,
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )

        // Display the list of all the expense of the current category.
        LazyActivityColumn(
            expenses = expenses
        )

    }
}


// Create and display a single Expense item.
@Composable
fun ExpenseItem(
    expense: Expense
){
    val image = painterResource(R.drawable.dress)
    Column(
        modifier = Modifier
            .padding(10.dp),
    ) {
        Text(
            text = expense.getName() ,
            fontSize = 25.sp ,
            fontFamily = FontFamily.Monospace ,
            textAlign = TextAlign.Center ,
            fontWeight = FontWeight.ExtraBold
        )
        Row(
        ) {
            Text(
                text = "${expense.getValue()} €" ,
                fontSize = 20.sp ,
                fontWeight = FontWeight.Bold ,
                modifier = Modifier.padding(start = 10.dp)
            )

            Text(
                text = formatDate(expense.getDate()) ,
                fontSize = 20.sp ,
                fontWeight = FontWeight.Bold ,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }

}

// Function that display the Recyclerview(in compose) of the expense list of a selected category.
// Need database implementation.
@Composable
fun LazyActivityColumn(
    expenses: List<Expense>?,
    modifier: Modifier = Modifier
){
    LazyColumn() {
            items(expenses ?: emptyList()) { expense ->
                ExpenseItem(expense)
            }
    }
}


fun formatDate(date: Date): String {
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}