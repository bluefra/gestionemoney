package gestionemoney.compose.register_login

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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

//import com.google.firebase.Firebase
//import com.google.firebase.auth.auth
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
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp)
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