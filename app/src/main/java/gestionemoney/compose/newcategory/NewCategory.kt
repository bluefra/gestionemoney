package gestionemoney.compose.newcategory

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import gestionemoney.compose.expense.components.CategoryMenu
import gestionemoney.compose.components.NavigationDrawer
import gestionemoney.compose.controller.DBInfoConnection
import gestionemoney.compose.controller.DBUserConnection
import gestionemoney.compose.controller.StandardInfo
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.expense.AddExpense

import gestionemoney.compose.navigation.Screens
import gestionemoney.compose.newcategory.components.NewCategoryNameTextField

private var newCategory = ""
private var newCategoryImage = ""
private const val imageSuffix = "cat"
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
    val standardImage = stringResource(id = R.string.standard_image_selection)
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            ){
            // Call to new category text field
            NewCategoryNameTextField(onChange = { newCategory = it})
            Spacer(modifier = Modifier.height(15.dp))

            CategoryMenu(categoryList = getDrawableResources(),
                    standardOption = standardImage,
                    onChange = { newCategoryImage = it + imageSuffix})

            // Database connection need to be implemented (save on db).
            // Currently point to the homepage.
            Button(
                modifier = Modifier.padding(top = 10.dp),
                onClick = { addCategory(navController, standardImage)},
                colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
            ) {
                Text(text = stringResource(id = R.string.confirmation_string))
            }
        }
        Button(
            onClick = {navController.navigate(Screens.Homepage.route)  },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
        ) {
            Text(text = stringResource(id = R.string.back_button))
        }

    }
}





fun addCategory(navController: NavController, standardImage: String) {

    if(newCategory == "" || newCategoryImage == standardImage || newCategoryImage == "") {
        Log.w("NewCategory", "campo vuoto")
        return
    }
    if(UserWrapper.getInstance().getCategory(newCategory) != null) {
        Log.w("NewCategory", "categoria gia esistente")

        return
    }
    Log.w("NewCategory", "adding $newCategory $newCategoryImage")
    UserWrapper.getInstance().addCategory(newCategory, newCategoryImage)
    DBUserConnection.getInstance().writeCategoryName(newCategory)
    StandardInfo.categoryUpdate()
    navController.navigate(Screens.Homepage.route)
}

fun getDrawableResources(): List<String> {
    val drawables = mutableListOf<String>()
    try {
        val fields = R.drawable::class.java.fields
        for (field in fields) {
            if(field.name.contains(imageSuffix)) {
                drawables.add(field.name.removeSuffix(imageSuffix))
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return drawables
}