package gestionemoney.compose.expense

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.controller.DBUserConnection
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.expense.components.CategoryMenu
import gestionemoney.compose.components.NavigationDrawer
import gestionemoney.compose.controller.InfoWrapper
import gestionemoney.compose.controller.StandardInfo
import gestionemoney.compose.controller.WriteLog
import gestionemoney.compose.expense.components.CostOfExpense
import gestionemoney.compose.expense.components.DatePicker
import gestionemoney.compose.expense.components.ExpenseName
import gestionemoney.compose.model.DateAdapter
import gestionemoney.compose.model.Expense
import gestionemoney.compose.model.getDateDifferenceFromNowDay
import gestionemoney.compose.model.weekDay
import gestionemoney.compose.navigation.Screens
import java.util.Calendar

import java.util.Date

private var categoryName = ""
private var expense_name: String = ""
private var expense_value: String = ""
private var date = Date()
@Composable
fun NewExpenseNavigation(
    navController: NavController,
    category: String?
){
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    NavigationDrawer(drawerState = drawerState , coroutineScope = coroutineScope , navController = navController,
        { NewExpense(navController = navController, category)}
    )
}

@Composable
fun NewExpense(
    navController: NavController,
    category: String?
) {
    date = Date()
    val categorylist = UserWrapper.getInstance().getCategoriesNames()
    val baseOption = category?: AddExpense.standardOption
    val scrollState = rememberScrollState()
    AddExpense.setStandardCategory(stringResource(R.string.standard_category_selection))
    categoryName = category?: categoryName

    Box(modifier = Modifier.fillMaxSize()){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 90.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            // Functions for user input data to create new expense.
            CategoryMenu(
                categorylist,
                baseOption,
                onChange = { categoryName = it })
            Spacer(modifier = Modifier.height(15.dp))
            ExpenseName { expense_name = it }
            Spacer(modifier = Modifier.height(15.dp))
            CostOfExpense(onChange = { expense_value = it })
            Spacer(modifier = Modifier.height(15.dp))
            DatePicker(
                defaultDate = AddExpense.currDate,
                initialDate = AddExpense.initialDate,
                onDateChanged = { date = it })
            //Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = { AddExpense().addExpense(navController) },
                colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
            ) {
                Text(text = stringResource(id = R.string.confirmation_string), color = Color.Black)
            }
        }
        Button(
            onClick = { navController.navigate(Screens.Homepage.route) },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
        ) {
            //Text(text = stringResource(id = R.string.back_button))
            Icon(imageVector = Icons.Default.ArrowBack , contentDescription = stringResource(id = R.string.back_button), tint = Color.Black)
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
        Log.w("date value", DateAdapter().getStringDate(date))
        if(!verifyExpense()) {
            WriteLog.getInstance().writeError("NEXP_newExpense_error", "expense name contains DB tokens")
            Log.w("New expense", "error")//add a toast here
            return
        }
        val expense = Expense(date, getCost(expense_value))
        expense.setName(expense_name)
        val lastExpense = InfoWrapper.getInstance().getInfo(StandardInfo.lastExpUpdate)
        if(lastExpense != "") {
            WriteLog.getInstance()
                .writeValue("NEXP_lastAddedExpense", getDateDifferenceFromNowDay(DateAdapter().buildDate(lastExpense)), "day")
        }
        UserWrapper.getInstance().getCategory(categoryName)?.addExpenses(expense)
        DBUserConnection.getInstance().writeLastExpense(categoryName)
        WriteLog.getInstance().writeValue("NEXP_newExpense_weekDay", weekDay().toDouble(), "day")
        StandardInfo.expenseUpdate(true)
        navController.navigate("${Screens.ExpensePage.route}/$categoryName")
    }

    private fun verifyExpense(): Boolean {
        if(categoryName == standardOption) {
            Log.w("NewExpenses","chose a category")
            WriteLog.getInstance().writeError("NEXP_newExpense-error", "categoryName == standardOption")
            return false
        }
        val cost = getCost(expense_value)
        if(cost == 0.0) {
            Log.w("NewExpenses", "insert a valid expense")
            WriteLog.getInstance().writeError("NEXP_newExpense-error", "cost == 0.0")
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