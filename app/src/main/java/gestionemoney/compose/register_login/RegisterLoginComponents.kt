package gestionemoney.compose.register_login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.navigation.Screens

@Composable
fun TextTitle(text: String) {
        Text(
            text = text,
            fontSize = 15.sp ,
            fontFamily = FontFamily.Monospace ,
            textAlign = TextAlign.Center ,
            fontWeight = FontWeight.ExtraBold ,
        )
}

@Composable
fun RegisterButton(
    //navController: NavController
){
        Button(
            onClick = { /*navController.navigate(Screens.Homepage.route)*/},
            colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
        ){
            TextTitle(text = "Register")
        }
    
}

@Composable
fun EmailEnter(){

        var text by remember { mutableStateOf("") }

        Text(
                text = "Inserisci la tua email",
                fontSize = 15.sp ,
                fontFamily = FontFamily.Monospace ,
                textAlign = TextAlign.Center ,
                fontWeight = FontWeight.ExtraBold ,
                modifier = Modifier.padding(top = 10.dp , bottom = 10.dp))

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("email") },
            )
}

@Composable
fun PasswordEnter(){

        var text by remember { mutableStateOf("") }

        Text(
            text = "Inserisci la tua password",
            fontSize = 15.sp ,
            fontFamily = FontFamily.Monospace ,
            textAlign = TextAlign.Center ,
            fontWeight = FontWeight.ExtraBold ,
            modifier = Modifier.padding(top = 10.dp , bottom = 10.dp))

        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("password") },
    )
}

@Composable
fun TextWithDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier.weight(1f),
            color = colorResource(R.color.orangeUltraLight),
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicText(
            text = "oppure",
        )
        Spacer(modifier = Modifier.width(8.dp))
        Divider(
            modifier = Modifier.weight(1f),
            color = colorResource(R.color.orangeUltraLight),
            thickness = 1.dp
        )
    }
}

@Composable
fun GoToLoginButton(
    //navController: NavController
){
    Button(
        onClick = { /*navController.navigate(Screens.Login.route)*/},
        colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
    ){
        TextTitle(text = "Login")
    }
}

@Composable
fun LoginButton(
    //navController: NavController
){
    Button(
        onClick = { /*navController.navigate(Screens.Homepage.route)*/},
        colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
    ){
        Text(text = "Login")
    }
}



@Preview
@Composable
fun EmailEnterPreview(){
    EmailEnter()
}


