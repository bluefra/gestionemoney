package gestionemoney.compose.newcategory.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.controller.DBUserConnection
import gestionemoney.compose.navigation.Screens


@Composable
fun CategoryDelete(
    onDismissRequest: () -> Unit,
    navController: NavController,
    categoryName: String
) {
    AlertDialog(
        icon = {
            Icon(painterResource(id = R.drawable.delete_icon), contentDescription = "Delete icon")
        },
        title = {
            Text(text = "Sei sicuro di voler cancellare?")
        },
        onDismissRequest = {
            navController.navigate(Screens.Homepage.route)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    DBUserConnection.getInstance().deleteCategory(categoryName)
                    navController.navigate(Screens.Homepage.route)
                }
            ) {
                Text("Conferma")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancella")
            }
        }
    )
}