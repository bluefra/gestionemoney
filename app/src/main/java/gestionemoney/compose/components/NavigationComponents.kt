package gestionemoney.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.navigation.Screens
import gestionemoney.compose.register_login_logout.Logout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    navigationIconClicked : () -> Unit
){

    var colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = colorResource(id = R.color.orangeLight)
    )

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 45.sp,
                fontFamily = FontFamily.Cursive,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.ExtraBold
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navigationIconClicked.invoke()
            }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu ,
                    contentDescription = null,
                )
            }
        },
        colors = colors,
    )
}

@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    navController: NavController,
    selectedScreen: @Composable () -> Unit

){
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerBody(drawerState, coroutineScope, navController)
            }
        },
        content = {
            Scaffold(
                topBar = {
                    AppToolbar(
                        navigationIconClicked = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }
                    )
                }
            ){ paddingValues ->

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.orangeUltraLight))
                        .padding(paddingValues)
                ) {
                    selectedScreen()
                }

            }
        }
    )
}

@Composable
fun NavigationDrawerHeader(){
    Row(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 45.sp,
            fontFamily = FontFamily.Cursive,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun NavigationDrawerBody(
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    navController: NavController
){
    Box(
        modifier = Modifier
            .background(color = colorResource(id = R.color.orangeLight))
            .fillMaxHeight(),
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            NavigationDrawerHeader()
            //Body
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.padding(top = 10.dp , bottom = 10.dp)
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
                    modifier = Modifier.padding(top = 10.dp , bottom = 10.dp)
                ) {
                    NavigationDrawerItem(
                        label = { Text(text = "Dashboard") } ,
                        selected = false ,
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.bar_chart_4_bars_24dp_fill0_wght400_grad0_opsz24) ,
                                contentDescription = null
                            )
                        } ,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            navController.navigate(Screens.Dashboard.route) {}
                        }
                    )
                }
                Row(
                    modifier = Modifier.padding(top = 10.dp , bottom = 10.dp)
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
                    modifier = Modifier.padding(top = 10.dp , bottom = 10.dp)
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
            Row(
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Logout(onDismissRequest = { navController.navigate(Screens.Homepage.route) }, navController = navController)
            }
        }
    }
}