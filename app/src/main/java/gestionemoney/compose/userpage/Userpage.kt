package gestionemoney.compose.userpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.components.BackButton
import gestionemoney.compose.components.NavigationDrawer
import gestionemoney.compose.homepage.Homepage

@Composable
fun UserpageNavigation(
    navController: NavController
){
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    NavigationDrawer(drawerState = drawerState , coroutineScope = coroutineScope , navController = navController,
        { Userpage(navController = navController) }
    )
}

// Composable function to display the user page.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Userpage(
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.orangeLight),
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.my_account),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
            )
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
                painter = painterResource(id = R.drawable.account_circle),
                contentDescription = "Round Image",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.name), string2 = "nome da passare")
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.surname), string2 = "Cognome da passare")
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = "Email", string2 = "email da passare")
            Spacer(modifier = Modifier.height(35.dp))
            InfoText(string1 = stringResource(id = R.string.register_date), string2 = "data da passare")
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
                .size(width = 150.dp, height = 50.dp)
                .background(Color.Transparent)
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ){
            Text(
                text = string1,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
        Box(
            modifier = Modifier
                .size(width = 200.dp, height = 50.dp)
                .background(colorResource(R.color.orangeUltraLight))
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = string2,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun Preview(){
    InfoText(string1 = "ciao", string2 = "ciao")
}