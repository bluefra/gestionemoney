package gestionemoney.compose.homepage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.homepage.components.NavigationTopBar
import gestionemoney.compose.homepage.components.NewCategoryButton
import gestionemoney.compose.model.Category
import gestionemoney.compose.navigation.Screens
import gestionemoney.compose.resource.categorylist

@Composable
fun Homepage(
    navController: NavController
) {
    // Mapping the categorylist to the Category data class. (database implementation)
    val categorynames = UserWrapper.getInstance().getCategoryList()
    Log.w("homepage", categorynames.toString())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        // App navigation bar
        NavigationTopBar(navController)

        // Draw a line to seperate the 2 top menu
        Divider(
            color = colorResource(R.color.orangeUltraLight),
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )
        // Homepage navigation bar at the top of the screen. Include 2 buttons: UserPage and Dashboard
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Le tue spese",
                    fontSize = 25.sp ,
                    fontFamily = FontFamily.Monospace ,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Column(
                horizontalAlignment = Alignment.End
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
@Composable
fun CategoryItem(
    name: String,
    imageUri: String?,
    totalExpences: Double,
    navController: NavController
){
    val image = painterResource(R.drawable.dress)
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
                text = name ,
                fontSize = 25.sp ,
                fontFamily = FontFamily.Monospace ,
                textAlign = TextAlign.Center ,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate(Screens.ExpensePage.route) } ,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orangeLight))
            ) {
                Image(
                    painter = image ,
                    contentDescription = null ,
                    contentScale = ContentScale.Crop ,
                    modifier = Modifier
                        .size(50.dp)
                )
            }
            Text(
                text = "$totalExpences €" ,
                fontSize = 25.sp ,
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
                onClick = { navController.navigate(Screens.ExpensePage.route) },
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