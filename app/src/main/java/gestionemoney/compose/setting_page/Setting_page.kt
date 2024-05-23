package gestionemoney.compose.setting_page

import android.widget.Switch
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import gestionemoney.compose.controller.DBauthentication
import gestionemoney.compose.controller.InfoWrapper
import gestionemoney.compose.navigation.Screens
import gestionemoney.compose.statistic_page.InfoText
import gestionemoney.compose.statistic_page.StatisticPage

@Composable
fun SettingPageNavigation(
    navController: NavController
){
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    NavigationDrawer(drawerState = drawerState , coroutineScope = coroutineScope , navController = navController,
        { SettingPage(navController = navController) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingPage(navController: NavController){
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
                        TitlePageText(string = stringResource(id = R.string.privacy))
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
                painter = painterResource(id = R.drawable.admin_panel_settings),
                contentDescription = "Round Image",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )


        }
    }

}



/*
@Composable
fun SwitchWithIconExample() {
    var checked by remember { mutableStateOf(true) }

    
    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
        thumbContent = if (checked) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        } else {
            null
        }
    )
}

 */
