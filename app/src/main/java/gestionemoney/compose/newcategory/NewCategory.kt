package gestionemoney.compose.newcategory

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import gestionemoney.compose.R
import gestionemoney.compose.components.BackButton
import gestionemoney.compose.expense.components.CategoryMenu
import gestionemoney.compose.components.NavigationDrawer
import gestionemoney.compose.controller.DBInfoConnection
import gestionemoney.compose.controller.DBUserConnection
import gestionemoney.compose.controller.InfoWrapper
import gestionemoney.compose.controller.StandardInfo
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.controller.WriteLog
import gestionemoney.compose.expense.AddExpense
import gestionemoney.compose.model.DateAdapter
import gestionemoney.compose.model.getDateDifferenceFromNowDay
import gestionemoney.compose.model.weekDay

import gestionemoney.compose.navigation.Screens
import gestionemoney.compose.newcategory.components.NewCategoryNameTextField


private var newCategory = ""
private var newCategoryImage = ""
private const val imageSuffix = "cat"
private var message: Toast? = null

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


@Preview(showBackground = true)
@Composable
fun popo() {
    val navController : NavController = rememberNavController()
    NewCategory(navController = navController)
}

@Composable
fun NewCategory(
    navController: NavController
) {
    val standardImage = stringResource(id = R.string.standard_image_selection)
    val scrollState = rememberScrollState()
    message = Toast.makeText(LocalContext.current, "", Toast.LENGTH_SHORT)

    Box(modifier = Modifier.fillMaxSize()){
        Row (
            modifier = Modifier.padding(10.dp)
        ) {
            ElevatedButton(
                onClick = { navController.navigate(Screens.Homepage.route) } ,
                modifier = Modifier
                    .size(45.dp) ,
                shape = CircleShape ,
                border = BorderStroke(
                    3.dp ,
                    colorResource(id = R.color.orange)
                ) ,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp) ,
                contentPadding = PaddingValues(0.dp) ,
                colors = ButtonDefaults.elevatedButtonColors(
                    contentColor = Color.Black ,
                    containerColor = colorResource(id = R.color.orangeLight)
                )
            ) {
                Icon(Icons.Default.ArrowBack , contentDescription = null)
            }
        }
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
            Row(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                ElevatedButton(
                    onClick = { addCategory(navController , standardImage) } ,
                    modifier = Modifier
                        .size(45.dp) ,
                    shape = CircleShape ,
                    border = BorderStroke(
                        3.dp ,
                        colorResource(id = R.color.orange)
                    ) ,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp) ,
                    contentPadding = PaddingValues(0.dp) ,
                    colors = ButtonDefaults.elevatedButtonColors(
                        contentColor = Color.Black ,
                        containerColor = colorResource(id = R.color.orangeLight)
                    )
                ) {
                    Icon(Icons.Default.Done , contentDescription = null)
                }
            }
        }
    }
}


fun addCategory(navController: NavController, standardImage: String) {

    if(newCategory == "" || newCategoryImage == standardImage || newCategoryImage == "") {
        Log.w("NewCategory", "campo vuoto")
        message?.setText("Inserisci il nome della categoria")
        message?.show()
        return
    }
    if(UserWrapper.getInstance().getCategory(newCategory) != null) {
        Log.w("NewCategory", "categoria gia esistente")
        message?.setText("CAtegoria già presente")
        message?.show()
        return
    }
    Log.w("NewCategory", "adding $newCategory $newCategoryImage")
    UserWrapper.getInstance().addCategory(newCategory, newCategoryImage)
    DBUserConnection.getInstance().writeCategoryName(newCategory)
    val lastCategory = InfoWrapper.getInstance().getInfo(StandardInfo.lastCatUpdate)
    if(lastCategory != "") {
        WriteLog.getInstance()
            .writeValue("NCAT_lastAddedCategory", getDateDifferenceFromNowDay(DateAdapter().buildDate(lastCategory)), "day")
    }
    WriteLog.getInstance().writeValue("NCAT_newCategory_weekDay", weekDay().toDouble(), "day")
    StandardInfo.categoryUpdate(true)
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