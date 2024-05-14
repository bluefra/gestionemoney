package gestionemoney.compose.register_login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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

@Composable
fun Login(
    navController: NavController
) {
    if (DBauthentication.getInstance().isSet()) {
        navController.navigate(Screens.Loading.route)
    }
    message = Toast.makeText(LocalContext.current, "", Toast.LENGTH_SHORT)

    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            TopSection("Nome app ..... \n Bentornato", "Login")
            Spacer(modifier = Modifier.height(36.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {

                EmailEnter(
                    label = "Email",
                    modifier = Modifier.fillMaxWidth(),
                    onChange = { email = it })
                Spacer(modifier = Modifier.height(35.dp))
                PasswordEnter(
                    label = "Password",
                    modifier = Modifier.fillMaxWidth(),
                    onChange = { password = it })
                Spacer(modifier = Modifier.height(20.dp))
                LoginButton(navController)
                Spacer(modifier = Modifier.height(35.dp))
                TextWithDivider()
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.8f),
                ) {
                   Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFF94A3B8),
                                    fontSize = 14.sp,
                                    fontFamily = Roboto,
                                    fontWeight = FontWeight.Normal
                                )
                            ) {
                                append("Non hai un account? ")
                            }
                        })
                    Surface(onClick = { navController.navigate(Screens.Register.route) }) {
                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFF000000),
                                    fontSize = 14.sp,
                                    fontFamily = Roboto,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(" ")
                                append("Registrati ora ")
                            }
                        })
                    }

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