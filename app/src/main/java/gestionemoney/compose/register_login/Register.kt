package gestionemoney.compose.register_login

import android.widget.TextView
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
//import com.google.firebase.Firebase
//import com.google.firebase.auth.auth


@Composable
fun Register2( /*navController: NavController*/){

    /*
    var auth = Firebase.auth
    var email by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
*/


    Column(
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center ,
        modifier = Modifier.fillMaxSize()
    ) {
    //scritte Benvenuto
    Row(verticalAlignment = Alignment.CenterVertically){
        Text("Benvenuto/a! \nCrea il tuo account")
    }

    //due caselle per email e pass + bottone register

        Column() {

            EmailEnter()
            PasswordEnter()
            RegisterButton(/*navController*/)
            TextWithDivider()


            Row() {
                Column(
                    horizontalAlignment = Alignment.Start ,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Hai già un account ? ")
                }
                Column {
                    GoToLoginButton(/*navController*/)
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview(){
    Register()
}

@Composable
fun Register( /*navController: NavController*/){

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
            RegisterButton(/*navController*/)
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
                    GoToLoginButton(/*navController*/)
                }
            }
        }

    }
}