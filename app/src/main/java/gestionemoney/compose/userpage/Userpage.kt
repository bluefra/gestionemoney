package gestionemoney.compose.userpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import gestionemoney.compose.ui.theme.NormalText
import gestionemoney.compose.ui.theme.TitlePageText
import gestionemoney.compose.controller.DBauthentication
import gestionemoney.compose.controller.InfoWrapper
import gestionemoney.compose.controller.StandardInfo
import gestionemoney.compose.model.DateAdapter
import gestionemoney.compose.model.formatReadingDate

// Function that add the navigation drawer to the selected application page.
@Composable
fun UserpageNavigation(
    navController: NavController
){
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    NavigationDrawer(drawerState = drawerState , coroutineScope = coroutineScope , navController = navController
    ) { Userpage() }
}

// Composable function to display the user infos.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Userpage() {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            Card(
                modifier=Modifier.padding(10.dp),
                shape= RoundedCornerShape(100.dp),
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
                            TitlePageText(string = stringResource(id = R.string.my_account))
                            Image(
                                painter = painterResource(id = R.drawable.account_circle_24dp_fill0_wght400_grad0_opsz24),
                                contentDescription = "Round Image",
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .size(40.dp)
                                    .clip(CircleShape)
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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            val stringSubscriptionDate = InfoWrapper.getInstance().getInfo(StandardInfo.subscriptionInfo)
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.name), string2 = InfoWrapper.getInstance().getInfo(StandardInfo.nameInfo))
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.surname), string2 = InfoWrapper.getInstance().getInfo(StandardInfo.surnameInfo))
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.email), string2 = DBauthentication.getInstance().getEmail() ?: "")
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.register_date), string2 = formatReadingDate(DateAdapter().buildDate(stringSubscriptionDate)))
        }
    }

}

@Composable
fun InfoText(string1: String, string2 : String) {
    Row(
        horizontalArrangement = Arrangement.Absolute.Left,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)

    ) {
        Box(
            modifier = Modifier
                .size(width = 150.dp , height = 50.dp)
                .background(Color.Transparent),
            contentAlignment = Alignment.CenterStart
        ){
            NormalText(string = string1)
        }
        Box(
            modifier = Modifier
                .size(width = 300.dp , height = 50.dp)
                .background(Color.White)
                .border(
                    2.dp ,
                    color = colorResource(id = R.color.orange) ,
                    shape = RoundedCornerShape(50)
                ),
            contentAlignment = Alignment.Center
        ) {
            NormalText(string = string2)
        }

    }
}

