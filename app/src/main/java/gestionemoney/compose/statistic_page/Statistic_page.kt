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
import gestionemoney.compose.controller.InfoWrapper
import gestionemoney.compose.controller.StandardInfo
import gestionemoney.compose.controller.StandardInfo.Companion.getDateDifferenceFromNow


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
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TitlePageText(string = stringResource(id = R.string.statistics_info))
                            Image(
                                painter = painterResource(id = R.drawable.query_stats_24dp_fill0_wght400_grad0_opsz24),
                                contentDescription = "Round Image",
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .clip(CircleShape)
                                    .size(40.dp)
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
                .padding(bottom = 20.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            val avgCatChar = InfoWrapper.getInstance().getInfo(StandardInfo.avgCategoryChar)
            val avgExpChar = InfoWrapper.getInstance().getInfo(StandardInfo.avgExpenseChar)
            val avgExp = InfoWrapper.getInstance().getInfo(StandardInfo.avgExpenseVal)
            val avgExpNumber = InfoWrapper.getInstance().getInfo(StandardInfo.avgExpenseNumber)
            val lastCat = InfoWrapper.getInstance().getInfo(StandardInfo.lastCatUpdate)
            val lastExp = InfoWrapper.getInstance().getInfo(StandardInfo.lastExpUpdate)
            val lastCatTime = getDateDifferenceFromNow(lastCat)
            val lastExpTime =getDateDifferenceFromNow(lastExp)
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.length_category), string2 = avgCatChar)
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.length_expense), string2 = avgExpChar)
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.expense_medie), string2 = avgExp)
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.avg_expense_number), string2 = avgExpNumber)
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.last_category), string2 = lastCat)
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.last_expense), string2 = lastExp)
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.last_category_time), string2 = lastCatTime)
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.last_expense_time), string2 = lastExpTime)
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
                .size(width = 280.dp , height = 50.dp)
                .background(Color.Transparent)
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            NormalText(string = string1)
        }
    }
    Row(
        horizontalArrangement = Arrangement.Absolute.Right,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(width = 300.dp , height = 50.dp)
                .background(colorResource(R.color.orangeUltraLight))
                .padding(16.dp),

            contentAlignment = Alignment.CenterStart
        ) {
            NormalText(string = string2)
        }

    }
}