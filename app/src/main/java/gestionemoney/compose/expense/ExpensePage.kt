package gestionemoney.compose.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.components.BackButton
import gestionemoney.compose.components.MediumText
import gestionemoney.compose.components.NavigationDrawer
import gestionemoney.compose.components.NewExpenseButton
import gestionemoney.compose.components.NormalText
import gestionemoney.compose.components.TitlePageText
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.model.DateAdapter
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

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(10.dp),
                onClick = { navController.navigate("${Screens.NewExpense.route}/$categoryName") },
                containerColor = colorResource(id = R.color.orange)
            ) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.new_expense_add))
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.fillMaxSize()
    ){
        innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    horizontalAlignment = Alignment.Start
                ){
                    TitlePageText(string = categoryName)
                }
                Column (
                    horizontalAlignment = Alignment.End
                ){
                    //NewExpenseButton(navController = navController, categoryName = categoryName)
                    Button(
                        onClick = { navController.navigate(Screens.Homepage.route) } ,
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
                    ) {
                        //Text(text = stringResource(id = R.string.back_button))
                        Icon(imageVector = Icons.Default.ArrowBack , contentDescription = stringResource(id = R.string.back_button), tint = Color.Black)
                    }
                }
            }
            //Spacer(modifier = Modifier.height(10.dp))

            // Display the list of all the expense of the current category.
            LazyActivityColumn(expenses = expenses, categoryName ,navController)

        }

    }
}


// Create and display a single Expense item.
@Composable
fun ExpenseItem(
    expense: Expense,
    categoryName: String,
    navController: NavController
){
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.background(color = colorResource(id = R.color.orangeLight))
    ){
       Row{
           Column(
               modifier = Modifier
                   .weight(1f)
                   .padding(20.dp),
               horizontalAlignment = Alignment.Start
           ) {
               MediumText(string = expense.getName())
           }
           Column(
               modifier = Modifier
                   .weight(1f)
                   .padding(10.dp),
               horizontalAlignment = Alignment.End,
           ){
               Button(
                   onClick = ({ showDialog = true }),
                   colors = ButtonDefaults.buttonColors(
                       colorResource(R.color.orange),
                       contentColor = Color.Black),
                   modifier = Modifier.padding(start = 80.dp)
               ) {
                   Icon(
                       painter = painterResource(id = R.drawable.delete_icon),
                       contentDescription = "delete_expense",
                       modifier = Modifier.size(24.dp))
               }
               if (showDialog) {
                   ExpenseDelete(
                       navController,
                       categoryName,
                       expenseDate = DateAdapter().getStringDate(expense.getDate()),
                       onButtonVisibilityChange = { isVisible -> showDialog = isVisible }
                   )
               }
           }
       }
        Row{
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MediumText(string = "${expense.getValue()} €")
            }
            Column (
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start
            ){
                MediumText(string =formatDate(expense.getDate()))
            }
        }
    }

}

// Function that display the Recyclerview(in compose) of the expense list of a selected category.
// Need database implementation.
@Composable
fun LazyActivityColumn(
    expenses: List<Expense>?,
    categoryName: String,
    navController: NavController
){
    LazyColumn() {
            items(expenses ?: emptyList()) { expense ->
                Card(
                    modifier = Modifier
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    ExpenseItem(expense, categoryName, navController)
                }
            }
    }
}


fun formatDate(date: Date): String {
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}