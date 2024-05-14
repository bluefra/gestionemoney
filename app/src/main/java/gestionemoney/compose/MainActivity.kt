package gestionemoney.compose

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import gestionemoney.compose.navigation.NavigationGraph
import gestionemoney.compose.navigation.Screens
import gestionemoney.compose.ui.theme.ComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationDrawer()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
){
    var navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .background(color = colorResource(id = R.color.orangeLight))
                        .fillMaxHeight(),
                ){
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "MoneySave",
                                fontSize = 45.sp,
                                fontFamily = FontFamily.Cursive,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Row (
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                        ) {
                            NavigationDrawerItem(
                                label = { Text(text = "Homepage") } ,
                                selected = false ,
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Home ,
                                        contentDescription = null
                                    )
                                } ,
                                onClick = {
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(Screens.Homepage.route) {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                        Row(
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                        ) {
                            NavigationDrawerItem(
                                label = { Text(text = "Dashboard") } ,
                                selected = false ,
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.bar_chart_4_bars_24dp_fill0_wght400_grad0_opsz24)  ,
                                        contentDescription = null
                                    )
                                } ,
                                onClick = {
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(Screens.Dashboard.route) {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                        Row(
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                        ) {
                            NavigationDrawerItem(
                                label = { Text(text = "Userpage") } ,
                                selected = false ,
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle ,
                                        contentDescription = null
                                    )
                                } ,
                                onClick = {
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(Screens.UserPage.route) {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                        Row(
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                        ) {
                            NavigationDrawerItem(
                                label = { Text(text = "Setting") } ,
                                selected = false ,
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Settings ,
                                        contentDescription = null
                                    )
                                } ,
                                onClick = {
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(Screens.Homepage.route) {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    ){
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

        Scaffold(
            topBar = {
                val coroutineScope = rememberCoroutineScope()
                CenterAlignedTopAppBar(
                    title = { Text(
                        text = "MoneySave",
                        fontSize = 45.sp,
                        fontFamily = FontFamily.Cursive,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.ExtraBold
                    )},
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu ,
                                contentDescription = null
                            )
                        }
                    },
                )
            }
        ) {
                innerPadding ->
            Box(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
            ){
                navController = rememberNavController()
                NavigationGraph(
                    navController = navController
                )
            }
        }
    }
}