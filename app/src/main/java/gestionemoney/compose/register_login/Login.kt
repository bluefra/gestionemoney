package gestionemoney.compose.register_login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun Login(
    navController: NavController
){

    //scritte Benvenuto
    Row(verticalAlignment = Alignment.CenterVertically){
        TextTitle("   Bentornato! \nAccedi")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()) {

        EmailEnter()
        PasswordEnter()
        LoginButton(navController)
    }

}