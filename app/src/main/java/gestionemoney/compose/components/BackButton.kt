package gestionemoney.compose.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.navigation.Screens

// Generic composable function that display a back button used all over the app. (now it just point to the homepage)
@Composable
fun BackButton(
    navController: NavController
) {
    Row {
        Button(
            onClick = { navController.navigate(Screens.Homepage.route)},
            colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
        ) {
            Text(text = stringResource(id = R.string.back_button))
        }
    }
}