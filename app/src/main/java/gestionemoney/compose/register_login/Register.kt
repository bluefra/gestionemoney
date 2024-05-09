package gestionemoney.compose.register_login

import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun Register(navController: NavController){

    /*
    var auth = Firebase.auth
    var email by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
*/



    //scritte Benvenuto
    Row(verticalAlignment = Alignment.CenterVertically){
        Text("   Benvenuto/a! \nCrea il tuo account")
    }

    //due caselle per email e pass + bottone register
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()){

        EmailEnter()
        PasswordEnter()
        RegisterButton(navController)
        TextWithDivider()


        Row(){
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f),){

                Text(text = "Hai già un account ? ")
            }
            Column{
                GoToLoginButton(navController)
            }


        }

    }



}

/*
@Preview
@Composable
fun RegisterPreview(){
    Register()
}*/