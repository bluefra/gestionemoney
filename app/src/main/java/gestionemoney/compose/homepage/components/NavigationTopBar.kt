package gestionemoney.compose.homepage.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableOpenTarget
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.navigation.Screens


// Composable function that display a navigation bar at the top of the screen.
@Composable
fun NavigationTopBar (
    navController: NavController
) {
    Row(
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Button(
                onClick = { navController.navigate(Screens.UserPage.route) },
                colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
            ) {
                Text(text = "Utente")
            }
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Button(
                onClick =  {navController.navigate(Screens.Dashboard.route)},
                colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
            ) {
                Text(text = "Dashboard")
            }
        }
    }
}

