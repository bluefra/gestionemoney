package gestionemoney.compose.register_login

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gestionemoney.compose.controller.AuthObserver
import gestionemoney.compose.controller.DBauthentication
import gestionemoney.compose.navigation.Screens

//import com.google.firebase.Firebase
//import com.google.firebase.auth.auth
private var email = ""
private var password = ""
private var message: Toast? = null

@SuppressLint("ShowToast")
@Composable
fun Register(navController: NavController){
    message = makeText(LocalContext.current,"", Toast.LENGTH_SHORT)
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
            EmailEnter(onChange = {email = it})
            PasswordEnter(onChange = {password = it})
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

class DBRegister(val navController: NavController): AuthObserver {
    private var connecting = false
    fun evaluateRegister() {
        if(connecting) {
            return
        }
        if(email == "" || password == "") {
            return
        }
        Log.w("test", email)
        Log.w("test", password)
        connecting = true
        DBauthentication.getInstance().addObserver(this)
        DBauthentication.getInstance().register(email, password)
    }

    override fun onFail(error: String) {
        DBauthentication.getInstance().removeObserver(this)
        message?.setText(error)
        message?.show()
        Log.w("error", error)
        connecting = false
    }

    override fun onSuccess(data: HashMap<String, String?>) {
        DBauthentication.getInstance().removeObserver(this)
        connecting = false
        navController.navigate(Screens.Loading.route)
    }

}