package gestionemoney.compose.register_login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.controller.AuthObserver
import gestionemoney.compose.controller.DBauthentication
import gestionemoney.compose.navigation.Screens
import gestionemoney.compose.ui.theme.Roboto

private var email = ""
private var password = ""
private var message: Toast? = null
private var verified = false


@Composable
fun Login(
    navController: NavController
) {
    if(!verified) {
        verified = true
        if (DBauthentication.getInstance().isSet()) {
            navController.navigate(Screens.Loading.route)
        }
    }
    message = Toast.makeText(LocalContext.current, "", Toast.LENGTH_SHORT)
    val scrollState = rememberScrollState()

    Surface {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            ) {
                TopSection(stringResource(id = R.string.app_name), "Login")
                Spacer(modifier = Modifier.height(36.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        EmailEnter(
                            label = "Email",
                            modifier = Modifier.fillMaxWidth(),
                            onChange = { email = it })
                        Spacer(modifier = Modifier.height(20.dp))
                        PasswordEnter(
                            label = "Password",
                            modifier = Modifier.fillMaxWidth(),
                            onChange = { password = it })
                        Spacer(modifier = Modifier.height(35.dp))
                        LoginButton(navController)
                        Spacer(modifier = Modifier.height(35.dp))
                        TextWithDivider()
                        Spacer(modifier = Modifier.height(20.dp))

                        Row() {
                                Text(
                                    text = stringResource(id = R.string.no_account_question) ,
                                    color = Color(0xFF94A3B8) ,
                                    fontSize = 14.sp ,
                                    fontFamily = Roboto ,
                                    fontWeight = FontWeight.Normal
                                )
                                Text(
                                    text = stringResource(id = R.string.register_now) ,
                                    color = Color(0xFF000000) ,
                                    fontSize = 14.sp ,
                                    fontFamily = Roboto ,
                                    fontWeight = FontWeight.Bold ,
                                    modifier = Modifier
                                        .clickable { navController.navigate(Screens.Register.route) }
                                        .padding(start = 5.dp, bottom = 20.dp)
                                )
                        }
                }

        }
    }
}

class DBLogin(val navController: NavController): AuthObserver {
    private var connecting = false
    fun evaluateLogin() {
        if (connecting) {
            return
        }
        if (email == "" || password == "") {
            message?.setText("Credenziali inserite errate")
            message?.show()
            return
        }
        Log.w("test", email)
        Log.w("test", password)
        connecting = true
        DBauthentication.getInstance().addObserver(this)
        DBauthentication.getInstance().login(email, password)
    }


    override fun onFail(error: String) {
        DBauthentication.getInstance().removeObserver(this)
        message?.setText("Credenziali inserite errate")
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