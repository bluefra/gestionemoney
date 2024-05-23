package gestionemoney.compose.expense

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.controller.DBUserConnection
import gestionemoney.compose.controller.StandardInfo
import gestionemoney.compose.controller.UserChangeObserver
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.navigation.Screens


@Composable
fun ExpenseDelete(
    navController: NavController,
    categoryName: String,
    expenseDate: String,
    onButtonVisibilityChange: (Boolean) -> Unit
) {
    var isButtonVisible by remember { mutableStateOf(true) }
    val confirmationText: String = stringResource(id = R.string.delete_category)
    val deletingText: String = stringResource(id = R.string.deleting)
    var text by remember { mutableStateOf(confirmationText) }
    AlertDialog(
        properties = DialogProperties(dismissOnClickOutside = false),
        icon = {
            Icon(painterResource(id = R.drawable.delete_icon), contentDescription = "Delete icon")
        },
        title = {
            Text(text = text)
        },
        onDismissRequest = {
            onButtonVisibilityChange(false)
        },
        confirmButton = {
            if(isButtonVisible) {
                TextButton(
                    onClick = {
                        isButtonVisible = false
                        text = deletingText
                        Log.w("delete expense", UserWrapper.getInstance().toString())
                        DBUserConnection.getInstance().deleteExpense(categoryName, expenseDate)
                        StandardInfo.expenseUpdate()
                        navController.navigate("${Screens.ExpensePage.route}/$categoryName")
                    }
                ) {
                    Text(text = stringResource(id = R.string.confirmation_string))
                }
            }
        },
        dismissButton = {
            if(isButtonVisible) {
                TextButton(
                    onClick = {
                        onButtonVisibilityChange(false)
                    }
                ) {
                    Text(text = stringResource(id = R.string.negation_string))
                }
            }
        }
    )
}