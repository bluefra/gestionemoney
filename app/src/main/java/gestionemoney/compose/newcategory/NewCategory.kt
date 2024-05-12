package gestionemoney.compose.newcategory

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.components.BackButton
import gestionemoney.compose.controller.DBconnection
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.navigation.Screens
import gestionemoney.compose.newcategory.components.NewCategoryNameTextField

private var newCategory = ""
@Composable
fun NewCategory(
    navController: NavController
) {
    Column(
        modifier = Modifier.padding((10.dp))
    ) {

        // Back button at the top of the screen.
        BackButton(navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Call to new category text field
            NewCategoryNameTextField(onChange = { newCategory = it})

            // Database connection need to be implemented (save on db).
            // Currently point to the homepage.
            Button(
                modifier = Modifier.padding(top = 10.dp),
                onClick = { addCategory(navController)},
                colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
            ) {
                Text(text = "Conferma")
            }
        }
    }
}

fun addCategory(navController: NavController) {
    if(newCategory == "") {
        Log.w("NewCategory", "campo vuoto")
        return
    }
    Log.w("NewCategory", "adding $newCategory")
    UserWrapper.getInstance().addCategory(newCategory)
    DBconnection.getInstance().writeUser()
    navController.navigate(Screens.Homepage.route)
}