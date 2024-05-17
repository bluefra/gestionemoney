package gestionemoney.compose.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.controller.DBInfoConnection
import gestionemoney.compose.controller.DBUserConnection
import gestionemoney.compose.controller.DBauthentication
import gestionemoney.compose.navigation.Screens

@Composable
fun ErrorAlert(
    navController: NavController,
    errorText: String
) {
    AlertDialog(
        properties = DialogProperties(dismissOnClickOutside = false),
        icon = {
            Icon(painterResource(id = R.drawable.delete_icon), contentDescription = "Delete icon")
        },
        title = {
            Text(text = errorText)
        },
        onDismissRequest = {
            navController.navigate(Screens.Login.route)
        },
        confirmButton = {
                TextButton(
                    onClick = {
                        DBauthentication.getInstance().logOut()
                        DBUserConnection.getInstance().close()
                        DBInfoConnection.getInstance().close()
                        navController.navigate(Screens.Login.route)
                    }
                ) {
                    Text("Conferma")
                }
            }
    )
}