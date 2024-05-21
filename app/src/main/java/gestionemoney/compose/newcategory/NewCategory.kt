package gestionemoney.compose.newcategory

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.components.BackButton
import gestionemoney.compose.components.CategoryMenu
import gestionemoney.compose.components.NavigationDrawer
import gestionemoney.compose.controller.DBUserConnection
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.homepage.Homepage
import gestionemoney.compose.navigation.Screens
import gestionemoney.compose.newcategory.components.NewCategoryNameTextField

private var newCategory = ""
private var newCategoryImage = ""

@Composable
fun NewCategoryNavigation(
    navController: NavController
){
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    NavigationDrawer(drawerState = drawerState , coroutineScope = coroutineScope , navController = navController,
        { NewCategory(navController = navController) }
    )
}


@Composable
fun NewCategory(
    navController: NavController
) {
    val context = LocalContext.current
    newCategoryImage = stringResource(R.string.standard_image)
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
            CategoryMenu(categoryList = getDrawableResources(context),
                         standardOption = newCategoryImage,
                         onChange = { newCategoryImage = it})
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
    Log.w("NewCategory", "adding $newCategory $newCategoryImage")
    UserWrapper.getInstance().addCategory(newCategory, newCategoryImage)
    DBUserConnection.getInstance().writeCategoryName(newCategory)
    navController.navigate(Screens.Homepage.route)
}

fun getDrawableResources(context: Context): List<String> {
    val drawables = mutableListOf<String>()
    try {
        val fields = R.drawable::class.java.fields
        for (field in fields) {
            if(field.name.contains("ico")) {
                drawables.add(field.name)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return drawables
}