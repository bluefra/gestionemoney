package gestionemoney.compose.expense

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
import gestionemoney.compose.newcategory.components.CategoryDelete
import gestionemoney.compose.ui.theme.Black
import gestionemoney.compose.ui.theme.Roboto
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

/*
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun puttolo() {
    val navController: NavController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Card(
            onClick = { /*navController.navigate("${Screens.ExpensePage.route}/${category.getName()}")*/ },
            modifier = Modifier.padding(bottom = 10.dp),
            shape = RoundedCornerShape(20.dp),
            //border = BorderStroke(2.dp, colorResource(id = R.color.orangeLight)) ,
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.orangeLight))
        ) {

        }
    }
}
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensePage(
    navController: NavController,
    categoryName: String
) {
    // Mapping the expenselist to the Expense data class. (database implementation)
    val expenses = UserWrapper.getInstance().getCategory(categoryName)?.getList()
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            //Back button. Return to homepage
                            ElevatedButton(
                                onClick = { navController.navigate(Screens.Homepage.route) } ,
                                modifier = Modifier
                                    .size(45.dp) ,
                                shape = CircleShape ,
                                border = BorderStroke(
                                    3.dp ,
                                    colorResource(id = R.color.orange)
                                ) ,
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp) ,
                                contentPadding = PaddingValues(0.dp) ,
                                colors = ButtonDefaults.elevatedButtonColors(
                                    contentColor = Color.Black ,
                                    containerColor = colorResource(id = R.color.orangeLight)
                                )
                            ) {
                                Icon(Icons.Default.ArrowBack , contentDescription = null)
                            }
                        }

                        Column(
                            modifier = Modifier
                                .weight(4f)
                                .padding(start = 5.dp)
                        ) {
                            Text(
                                text = categoryName ,
                                fontSize = 20.sp ,
                                color = Black ,
                                style = MaterialTheme.typography.bodyLarge ,
                                fontFamily = Roboto ,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            //Delete Category Button
                            ElevatedButton(
                                onClick = ({ showDialog = true }) ,
                                modifier = Modifier
                                    .size(45.dp) ,
                                shape = CircleShape ,
                                border = BorderStroke(
                                    3.dp ,
                                    colorResource(id = R.color.orange)
                                ) ,
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp) ,
                                contentPadding = PaddingValues(0.dp) ,
                                colors = ButtonDefaults.elevatedButtonColors(
                                    contentColor = Color.Black ,
                                    containerColor = colorResource(id = R.color.orangeLight)
                                )
                            ) {
                                Icon(Icons.Default.Delete , contentDescription = null)
                            }
                            if (showDialog) {
                                CategoryDelete(
                                    onDismissRequest = { navController.navigate(Screens.Homepage.route) },
                                    navController,
                                    categoryName = categoryName
                                )
                            }
                        }
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            //New expense page
                            ElevatedButton(
                                onClick = { navController.navigate("${Screens.NewExpense.route}/$categoryName") } ,
                                modifier = Modifier
                                    .size(50.dp) ,
                                shape = CircleShape ,
                                border = BorderStroke(
                                    3.dp ,
                                    colorResource(id = R.color.orange)
                                ) ,
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp) ,
                                contentPadding = PaddingValues(0.dp) ,
                                colors = ButtonDefaults.elevatedButtonColors(
                                    contentColor = Color.Black ,
                                    containerColor = colorResource(id = R.color.orangeLight)
                                )
                            ) {
                                Icon(Icons.Default.Add , contentDescription = null)
                            }
                        }
                    }
                },
            )
        },
        modifier = Modifier.fillMaxSize()
    ){
        innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
        ) {

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
        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
            ) {
                Text(
                    text = expense.getName() ,
                    fontSize = 18.sp ,
                    color = Black ,
                    style = MaterialTheme.typography.bodyLarge ,
                    fontFamily = Roboto ,
                    fontWeight = FontWeight.Bold
                )
                //
                Text(
                    text = formatDate(expense.getDate()) ,
                    fontSize = 15.sp ,
                    color = Black ,
                    style = MaterialTheme.typography.bodyLarge ,
                    fontFamily = Roboto ,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier.padding(start = 10.dp, end = 2.dp)
                    .weight(1f)
            ){
                Text(
                    text = "€ ${expense.getValue()}" ,
                    fontSize = 20.sp ,
                    color = Black ,
                    style = MaterialTheme.typography.bodyLarge ,
                    fontFamily = Roboto ,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
            ) {
                //Delete Expense Button
                Button(
                    onClick = ({ showDialog = true }) ,
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orangeLight)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete ,
                        contentDescription = null ,
                        tint = Color.Black,
                    )
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
    }

}


// Function that display the Recyclerview(in compose) of the expense list of a selected category.
// Need database implementation.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyActivityColumn(
    expenses: List<Expense>?,
    categoryName: String,
    navController: NavController
){
    LazyColumn() {
            items(expenses ?: emptyList()) { expense ->
                Card(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.orangeLight))
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