package gestionemoney.compose.register_login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

//import com.google.firebase.Firebase
//import com.google.firebase.auth.auth


@Composable
fun Register(navController: NavController){
    /*
    var auth = Firebase.auth
    var email by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
*/
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row() {
            TextTitle(text = "Benvenuto/a! \n" + "Crea il tuo account")
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailEnter()
            PasswordEnter()
            RegisterButton(navController)
            TextWithDivider()
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center ,
                ) {
                    TextTitle(text = "Hai già un account ? ")
                }
                Row {
                    GoToLoginButton(navController)
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun RegisterPreview(){
    Register()
}
*/