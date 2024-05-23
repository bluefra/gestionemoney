package gestionemoney.compose.statistic_page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.components.NavigationDrawer
import gestionemoney.compose.components.NormalText
import gestionemoney.compose.components.TitlePageText


@Composable
fun StatisticPageNavigation(
    navController: NavController
){
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    NavigationDrawer(drawerState = drawerState , coroutineScope = coroutineScope , navController = navController,
        { StatisticPage(navController = navController) }
    )
}

// Composable function to display the user page.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticPage(
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            Card(
                modifier= Modifier.padding(10.dp),
                shape= RoundedCornerShape(100.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ){
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(R.color.orangeLight),
                        titleContentColor = Color.Black,
                    ),
                    title = {
                        TitlePageText(string = stringResource(id = R.string.statistics_info))
                    },
                )

            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Image(
                painter = painterResource(id = R.drawable.query_stats),
                contentDescription = "Round Image",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.category_medie), string2 = "ciao")
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.length_category), string2 = "ciao")
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.expense_medie), string2 = "ciao")
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.length_expense), string2 = "ciao")
        }
    }

}

@Composable
fun InfoText(string1: String, string2 : String) {
    Row(
        horizontalArrangement = Arrangement.Absolute.Left,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Box(
            modifier = Modifier
                .size(width = 280.dp, height = 50.dp)
                .background(Color.Transparent)
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ){
            NormalText(string = string1)
        }
        Box(
            modifier = Modifier
                .size(width = 90.dp, height = 50.dp)
                .background(colorResource(R.color.orangeUltraLight))
                .padding(16.dp),

            contentAlignment = Alignment.CenterStart
        ) {
            NormalText(string = string2)
        }

    }
}

