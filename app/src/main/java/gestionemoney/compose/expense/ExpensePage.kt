package gestionemoney.compose.expense

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import gestionemoney.compose.expense.components.NewExpenseButton
import gestionemoney.compose.homepage.components.NewCategoryButton
import gestionemoney.compose.navigation.Screens
import gestionemoney.compose.resource.expenselist
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.time.LocalDate

@Composable
fun ExpensePage(
    navController: NavController
) {
    // Mapping the expenselist to the Expense data class. (database implementation)
    val expenses = expenselist.map {
        Expense(
            name = it.key.toString(),
            items = it.value,
            date =  LocalDate.now(),
            shop = "",
            category = ""
        )
    }

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
                        onClick = { navController.navigate(Screens.NewExpense.route)},
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
                    text = "Abbigliamento",
                    fontSize = 25.sp ,
                    fontFamily = FontFamily.Monospace ,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                NewExpenseButton(navController)
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

// Data class to create Expense (db connection)
data class Expense(
    val name: String,
    val items: List<String>,
    val date: LocalDate,
    val shop: String,
    val category: String
){}

// Display the expense name.
@Composable
fun ExpenseName(
    text: String
){
    Text(
        text = text,
        fontSize = 20.sp ,
        fontFamily = FontFamily.Monospace ,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraBold
    )
}

// Create and display a single Expense item.
@Composable
fun ExpenseItem(
    text: String
){
    val image = painterResource(R.drawable.dress)
    Column(
        modifier = Modifier
            .padding(10.dp),
    ) {
        Text(
            text = text ,
            fontSize = 25.sp ,
            fontFamily = FontFamily.Monospace ,
            textAlign = TextAlign.Center ,
            fontWeight = FontWeight.ExtraBold
        )
        Row(
        ) {
            Text(
                text = "150 €" ,
                fontSize = 20.sp ,
                fontWeight = FontWeight.Bold ,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = "Padova" ,
                fontSize = 20.sp ,
                fontWeight = FontWeight.Bold ,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = "15 Aprile 2024" ,
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
    expenses: List<Expense>,
    modifier: Modifier = Modifier
){
    LazyColumn() {
        expenses.forEach{expense ->
            items(expense.items) { text ->
                ExpenseItem(text)
            }
        }
    }
}