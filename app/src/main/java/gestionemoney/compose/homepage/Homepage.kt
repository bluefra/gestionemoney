package gestionemoney.compose.homepage

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import gestionemoney.compose.R
import gestionemoney.compose.components.MediumText
import gestionemoney.compose.components.NavigationDrawer
import gestionemoney.compose.components.NewCategoryButton
import gestionemoney.compose.components.NormalText
import gestionemoney.compose.components.TitlePageText
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.model.Category
import gestionemoney.compose.navigation.Screens
import gestionemoney.compose.newcategory.components.CategoryDelete
import gestionemoney.compose.ui.theme.Black
import gestionemoney.compose.ui.theme.Roboto

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homepage(
    navController: NavController
) {
    // Mapping the categorylist to the Category data class. (database implementation)
    val categorynames = UserWrapper.getInstance().getOrderedListByName(Category.ORDER.ASC)
    Log.w("homepage1", categorynames.toString())

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(id = R.string.your_expenses) ,
                            fontSize = 25.sp ,
                            color = Black ,
                            style = MaterialTheme.typography.bodyLarge ,
                            fontFamily = Roboto ,
                            fontWeight = FontWeight.Bold
                        )
                        ElevatedButton(
                            onClick = { navController.navigate(Screens.NewCategory.route) } ,
                            modifier = Modifier
                                .size(50.dp),
                            shape = CircleShape ,
                            border = BorderStroke(
                                3.dp ,
                                colorResource(id = R.color.orange)
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp) ,
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.elevatedButtonColors(
                                contentColor = Color.Black ,
                                containerColor = colorResource(id = R.color.orangeLight))
                        ) {
                            Icon(Icons.Default.Add , contentDescription = null)
                        }
                    }
                },
                )
        },
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .padding(innerPadding)
        ) {
            // RecyclerView(in compose) to view the category list
            LazyCategoryColumn(
                categories = categorynames ,
                navController = navController
            )
        }
    }
}

// Composable function to display a single Category Item
@OptIn(ExperimentalMaterial3Api::class)
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
    ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                ) {
                    Button(
                        onClick = { navController.navigate("${Screens.ExpensePage.route}/$name") },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orangeLight))
                    ) {
                        Image(
                            painter = image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(2f)
                ) {
                    Text(
                        text = name,
                        fontSize = 18.sp,
                        color = Black,
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = Roboto,
                        fontWeight = FontWeight.Bold
                    )
                    //MediumText(string = "$totalExpences €")
                    Text(
                        text = "€ $totalExpences",
                        fontSize = 20.sp,
                        color = Black,
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = Roboto,
                        fontWeight = FontWeight.Bold
                    )

                }
                Column(
                ){
                    Row {
                        Button(
                            onClick = { navController.navigate("${Screens.ExpensePage.route}/$name") } ,
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orangeLight))
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight ,
                                contentDescription = null ,
                                tint = Color.Black
                            )
                        }
                    }
                }

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
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.orangeLight))
            ) {
                Log.w("homepage2", category.getName())
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
