package gestionemoney.compose.homepage

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.components.NavigationDrawer
import gestionemoney.compose.components.TitlePageText
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.homepage.components.NewCategoryButton
import gestionemoney.compose.model.Category
import gestionemoney.compose.navigation.Screens
import gestionemoney.compose.newcategory.components.CategoryDelete

@Composable
fun HomeNavigation(
    navController: NavController
){
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    NavigationDrawer(drawerState = drawerState , coroutineScope = coroutineScope , navController = navController,
        { Homepage(navController = navController)}
    )
}

@Composable
fun Homepage(
    navController: NavController
) {
    // Mapping the categorylist to the Category data class. (database implementation)
    val categorynames = UserWrapper.getInstance().getOrderedList(Category.ORDER.ASC)
    Log.w("homepage", categorynames.toString())


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        // Homepage navigation bar at the top of the screen. Include 2 buttons: UserPage and Dashboard
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                TitlePageText(string = stringResource(id = R.string.your_expenses))
                /*Text(
                    text = stringResource(id = R.string.your_expenses),
                    fontSize = 25.sp ,
                    fontFamily = FontFamily.Monospace ,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold

                )

                 */
            }
            Column(
                horizontalAlignment = Alignment.End,

            ) {
                NewCategoryButton(navController)
            }
        }

        // RecyclerView(in compose) to view the category list
        LazyCategoryColumn(
            categories = categorynames,
            navController = navController
        )
    }
}

// Need to be changed with database connection)
@Composable
fun CategoryName(
    text: String
){
    Text(
        text = text,
        fontSize = 25.sp ,
        fontFamily = FontFamily.Monospace ,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraBold
    )
}


// Composable function to display a single Category Item
@SuppressLint("DiscouragedApi")
@Composable
fun CategoryItem(
    name: String,
    imageUri: String?,
    totalExpences: Double,
    navController: NavController
){
    val context = LocalContext.current
    var showDialog by rememberSaveable { mutableStateOf(false) }

    var imageName: String = imageUri?: stringResource(R.string.standard_image)
    if(imageName == "") {
        imageName = stringResource(R.string.standard_image)
    }
    Log.w("crash", imageName)
    // Ottieni l'ID dell'immagine utilizzando il nome dell'immagine come stringa
    val image = painterResource(context.resources.getIdentifier(imageName, "drawable", context.packageName))
    Column(
        modifier = Modifier
            .background(color = colorResource(R.color.orangeLight))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = name,
                modifier = Modifier
                    .offset(x = 6.dp)
                    .weight(1f),
                fontSize = 25.sp,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.ExtraBold
            )
            Button(
                onClick = ({ showDialog = true }),
                colors = ButtonDefaults.buttonColors(
                    colorResource(R.color.orange),
                    contentColor = Color.Black),
                modifier = Modifier.padding(start = 80.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.delete_icon),
                    contentDescription = "delete_category",
                    modifier = Modifier.size(24.dp))
            }
            if (showDialog) {
                CategoryDelete(
                    onDismissRequest = { navController.navigate(Screens.Homepage.route) },
                    navController,
                    categoryName = name
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate("${Screens.ExpensePage.route}/$name") },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orange))
            ) {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                )
            }
            Text(
                text = "$totalExpences €",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }
}

// Composable function to generate the Recyclerview (in compose: LazyColumn) for the category list
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyCategoryColumn(
    categories: List<Category>?,
    navController: NavController
) {
    LazyColumn {
        items(categories ?: emptyList()) { category ->
            Card(
                onClick = { navController.navigate("${Screens.ExpensePage.route}/${category.getName()}") },
                modifier = Modifier.padding(bottom = 10.dp),
            ) {
                Log.w("homepage", category.getName())
                CategoryItem(
                    category.getName(),
                    category.getImageURI(),
                    category.GetTotalExpences(),
                    navController = navController
                )
            }
        }
    }
}