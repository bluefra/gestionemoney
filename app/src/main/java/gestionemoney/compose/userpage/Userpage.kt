package gestionemoney.compose.userpage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.navigation.Screens

// Composable function to display the user page.
@Composable
fun Userpage(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "user page")
        Button(
            onClick = { navController.navigate(Screens.Homepage.route){
                popUpTo(0)
            } },
            colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
        ) {
            Text(text = "homepage")
        }
    }
}