package gestionemoney.compose.expense

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import gestionemoney.compose.controller.DBconnection
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.expense.components.CategoryOfExpense
import gestionemoney.compose.expense.components.CostOfExpense
import gestionemoney.compose.expense.components.DatePicker
import gestionemoney.compose.model.Expense
import gestionemoney.compose.navigation.Screens
import java.util.Calendar

import java.util.Date

private var categoryName = ""
private var expense: String = ""
private var date = Date()

@Composable
fun NewExpense(
    navController: NavController
) {
    val categorylist = UserWrapper.getInstance().getCategoriesNames()
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
            CategoryOfExpense(
                categorylist,
                AddExpense.standardOption,
                onChange = { categoryName = it
                                Log.w("NewExpense", it)})
            CostOfExpense(onChange = { expense = it })
            DatePicker(initialDate = AddExpense.initialDate, onDateChanged = { date = it })

            Button(
                onClick = { AddExpense().addExpense(navController) },
                colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
            ) {
                Text(text = "Conferma")
            }

        }
    }
}

class AddExpense {

    fun getCost(cost: String): Double {
        return if(cost.toDoubleOrNull() == null) {
            0.0
        } else {
            cost.toDouble()
        }
    }
    fun addExpense(navController: NavController) {
        if(!verifyExpense()) {
            return
        }
        UserWrapper.getInstance().getCategory(categoryName)?.addExpenses(
            Expense(date, getCost(expense))
        )
        DBconnection.getInstance().writeUser()
        navController.navigate(Screens.ExpensePage.route)
    }

    fun verifyExpense(): Boolean {
        if(categoryName == standardOption) {
            Log.w("NewExpenses","chose a category")
            return false
        }
        val cost = getCost(expense)
        if(cost == 0.0) {
            Log.w("NewExpenses", "insert a valid expense")
            return false
        }
        return true
    }
    companion object {
        const val standardOption = "chose a category..."
        val initialDate = getDateOneYearAgo()
        fun getDateOneYearAgo(): Date {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -1)
            return calendar.time
        }
    }
}