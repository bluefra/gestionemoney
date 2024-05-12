package gestionemoney.compose.register_login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import gestionemoney.compose.controller.AuthObserver
import gestionemoney.compose.controller.DBauthentication
import gestionemoney.compose.navigation.Screens

private var email = ""
private var password = ""
private var message: Toast? = null
@Composable
fun Login(
    navController: NavController
){
    if(DBauthentication.getInstance().isSet()) {
        navController.navigate(Screens.Loading.route)
    }
    message = Toast.makeText(LocalContext.current, "", Toast.LENGTH_SHORT)
    //scritte Benvenuto
    Row(verticalAlignment = Alignment.CenterVertically){
        TextTitle("   Bentornato! \nAccedi")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()) {

        EmailEnter(onChange = {email = it})
        PasswordEnter(onChange = {password = it})
        LoginButton(navController)
    }

}
class DBLogin(val navController: NavController): AuthObserver {
    private var connecting = false
    fun evaluateLogin() {
        if(connecting) {
            return
        }
        Log.w("test", email)
        Log.w("test", password)
        DBauthentication.getInstance().addObserver(this)
        DBauthentication.getInstance().login(email, password)
        connecting = true
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
        Log.w("succ", "connected")
        navController.navigate(Screens.Loading.route)
    }

}