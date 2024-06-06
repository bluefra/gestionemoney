package gestionemoney.compose.register_login

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
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
private var name = ""
private var surname = ""
private var message: Toast? = null

@SuppressLint("ShowToast")
@Composable
fun Register(navController: NavController){
    message = makeText(LocalContext.current,"", Toast.LENGTH_SHORT)
    val scrollState = rememberScrollState()

    Surface {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            ) {
                TopSection(stringResource(id = R.string.app_name), "Register")
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
                    Spacer(modifier = Modifier.height(15.dp))
                    PasswordEnter(
                        label = "Password",
                        modifier = Modifier.fillMaxWidth(),
                        onChange = { password = it })
                    Spacer(modifier = Modifier.height(15.dp))
                    InfoEnter(
                        label = stringResource(id = R.string.name),
                        modifier = Modifier.fillMaxWidth(),
                        onChange = { name = it })
                    Spacer(modifier = Modifier.height(15.dp))
                    InfoEnter(
                        label  = stringResource(id = R.string.surname),
                        modifier = Modifier.fillMaxWidth(),
                        onChange = { surname = it })
                    Spacer(modifier = Modifier.height(15.dp))
                    RegisterButton(navController = navController)
                    Spacer(modifier = Modifier.height(15.dp))
                    TextWithDivider()
                    Row(
                        modifier = Modifier.padding(top = 15.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.have_account_sign_in) ,
                            color = Color(0xFF94A3B8) ,
                            fontSize = 14.sp ,
                            fontFamily = Roboto ,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = stringResource(R.string.login_now) ,
                            color = Color(0xFF000000) ,
                            fontSize = 14.sp ,
                            fontFamily = Roboto ,
                            fontWeight = FontWeight.Bold ,
                            modifier = Modifier
                                .clickable { navController.navigate(Screens.Login.route) }
                                .padding(start = 5.dp , bottom = 20.dp)
                        )
                    }
                }

        }
    }

}

class DBRegister(val navController: NavController): AuthObserver {
    private var connecting = false
    fun evaluateRegister() {
        if(connecting) {
            message?.setText("Account già registrato")
            message?.show()
            return
        }
        if(password.length < 6) {
            message?.setText("La password deve essere di almeno 6 caratteri")
            message?.show()
            return
        }
        if(email == "" || password == "") {
            message?.setText("Email o password non corrette")
            message?.show()
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
        message?.setText("Registrazione fallita")
        message?.show()
        Log.w("error", error)
        connecting = false
    }

    override fun onSuccess(data: HashMap<String, String?>) {
        DBauthentication.getInstance().removeObserver(this)
        connecting = false
        Log.w("register", Screens.Loading.route + "?name=$name&surname=$surname")
        navController.navigate(Screens.Loading.route + "?name=$name&surname=$surname")
    }

}