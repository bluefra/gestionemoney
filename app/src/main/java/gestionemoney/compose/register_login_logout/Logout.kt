package gestionemoney.compose.register_login_logout

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.controller.DBUserConnection
import gestionemoney.compose.controller.DBauthentication
import gestionemoney.compose.navigation.Screens
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@Composable
fun Logout(
    onDismissRequest: () -> Unit,
    navController: NavController,
) {
    var showDialog by remember { mutableStateOf(false) }

    NavigationDrawerItem(
        label = { Text(text = "Logout") } ,
        selected = false ,
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "delete_category",)
        } ,
        onClick = ({ showDialog = true })
    )
    if (showDialog) {
        AlertDialog(
            icon = {
                Icon(painterResource(id = R.drawable.logout), contentDescription = "Logout icon")
            },
            title = {
                Text(text = "Sei sicuro di voler uscire?")
            },
            onDismissRequest = {
                showDialog=false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog=false
                        DBauthentication.getInstance().logOut()
                        navController.navigate(Screens.Login.route)
                    }
                ) {
                    Text("Conferma")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog=false
                        onDismissRequest()
                    }
                ) {
                    Text("Annulla")
                }
            }
        )

    }


}