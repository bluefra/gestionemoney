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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.components.BackButton
import gestionemoney.compose.controller.DBUserConnection
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.components.CategoryMenu
import gestionemoney.compose.expense.components.CostOfExpense
import gestionemoney.compose.expense.components.DatePicker
import gestionemoney.compose.expense.components.ExpenseName
import gestionemoney.compose.model.Expense
import gestionemoney.compose.navigation.Screens
import java.util.Calendar

import java.util.Date

private var categoryName = ""
private var expense_name: String = ""
private var expense_value: String = ""
private var date = Date()

@Composable
fun NewExpense(
    navController: NavController,
    category: String?
) {
    val categorylist = UserWrapper.getInstance().getCategoriesNames()
    val baseOption = category?: AddExpense.standardOption
    AddExpense.setStandardCategory(stringResource(R.string.standard_category_selection))
    categoryName = category?: categoryName
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
            CategoryMenu(
                categorylist,
                baseOption,
                onChange = { categoryName = it})
            ExpenseName { expense_name = it }
            CostOfExpense(onChange = { expense_value = it })
            DatePicker(defaultDate = AddExpense.currDate,initialDate = AddExpense.initialDate, onDateChanged = { date = it })

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

    private fun getCost(cost: String): Double {
        return if(cost.toDoubleOrNull() == null) {
            0.0
        } else {
            cost.toDouble()
        }
    }
    //the name shouldn't contain space
    private fun verifyIntegrity(name: String): Boolean {
        return !name.contains(Expense.DBtoken)
    }
    fun addExpense(navController: NavController) {
        if(!verifyExpense()) {
            return
        }
        val expense = Expense(date, getCost(expense_value))
        expense.setName(expense_name)
        UserWrapper.getInstance().getCategory(categoryName)?.addExpenses(expense)
        DBUserConnection.getInstance().writeLastExpense(categoryName)
        navController.navigate(Screens.Homepage.route)
    }

    private fun verifyExpense(): Boolean {
        if(categoryName == standardOption) {
            Log.w("NewExpenses","chose a category")
            return false
        }
        val cost = getCost(expense_value)
        if(cost == 0.0) {
            Log.w("NewExpenses", "insert a valid expense")
            return false
        }
        return verifyIntegrity(expense_name)
    }
    companion object {
        var standardOption = ""
        val initialDate = getDateOneYearAgo()
        val currDate = Date()
        fun setStandardCategory(option: String) {
            standardOption = option
        }
        private fun getDateOneYearAgo(): Date {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -1)
            return calendar.time
        }
    }
}