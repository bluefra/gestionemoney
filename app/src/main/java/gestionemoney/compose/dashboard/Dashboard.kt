package gestionemoney.compose.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import gestionemoney.compose.components.BackButton
import gestionemoney.compose.components.NavigationDrawer
import gestionemoney.compose.components.TitlePageText
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.homepage.Homepage
import gestionemoney.compose.model.Category
import gestionemoney.compose.navigation.Screens
import gestionemoney.compose.ui.theme.Black
import kotlin.math.roundToInt

@Composable
fun DashboardNavigation(
    navController: NavController
){
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    NavigationDrawer(drawerState = drawerState , coroutineScope = coroutineScope , navController = navController
    ) { Dashboard() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            Card(
                modifier=Modifier.padding(10.dp),
                shape= RoundedCornerShape(100.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ){
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(R.color.orangeLight),
                        titleContentColor = Color.Black,
                    ),
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TitlePageText(string = stringResource(id = R.string.dashboard_intro))
                            Image(
                                painter = painterResource(id = R.drawable.bar_chart_4_bars_24dp_fill0_wght400_grad_25_opsz24) ,
                                contentDescription = "Round Image",
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .size(40.dp)
                                    .clip(CircleShape),
                            )
                        }
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Pie(entry = categoryPie())
        }
    }
}

fun categoryPie(): Map<String, Float> {
    val categoryList: List<Category> = UserWrapper.getInstance().getOrderedListByValue(Category.ORDER.DEC)
    val result: MutableMap<String, Float> = mutableMapOf()
    categoryList.forEach{
        result[it.getName()] = it.GetTotalExpences().toFloat()
    }
    return result
}


