package gestionemoney.compose.newcategory.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.controller.DBUserConnection
import gestionemoney.compose.controller.UserChangeObserver
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.navigation.Screens


@Composable
fun CategoryDelete(
    onDismissRequest: () -> Unit,
    navController: NavController,
    categoryName: String
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
            navController.navigate(Screens.Homepage.route)
        },
        confirmButton = {
            if(isButtonVisible) {
                TextButton(
                    onClick = {
                        isButtonVisible = false
                        text = deletingText
                        DeleteCategory(navController).delete(categoryName)
                    }
                ) {
                    Text("Conferma")
                }
            }
        },
        dismissButton = {
            if(isButtonVisible) {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Annulla")
                }
            }
        }
    )
}


class DeleteCategory(val navController: NavController): UserChangeObserver {
    fun delete(categoryName: String) {
        DBUserConnection.getInstance().addUserObserver(this)
        DBUserConnection.getInstance().deleteCategory(categoryName)
    }
    override fun updateUser(user: UserWrapper) {
        DBUserConnection.getInstance().removeUserObserver(this)
        navController.navigate(Screens.Homepage.route)
    }

    override fun updateError(error: String) {

    }


}
