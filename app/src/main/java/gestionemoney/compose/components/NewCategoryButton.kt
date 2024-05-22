package gestionemoney.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.navigation.Screens

@Composable
fun NewCategoryButton (
    navController: NavController
) {
    Button(
        onClick = { navController.navigate(Screens.NewCategory.route) {
            popUpTo(0)
        } },
        colors = ButtonDefaults.buttonColors(colorResource(R.color.orange)),
    ) {
        Text(
            text = stringResource(id = R.string.new_category_button),
        )
    }
}