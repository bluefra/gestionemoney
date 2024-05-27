package gestionemoney.compose.register_login

import android.icu.number.NumberFormatter.TrailingZeroDisplay
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.components.AppNameText
import gestionemoney.compose.components.TitlePageText
import gestionemoney.compose.navigation.Screens
import gestionemoney.compose.ui.theme.Black
import gestionemoney.compose.ui.theme.LemonYellon


@Composable
fun TopSection(text1 : String, text2: String){

    var text: String by rememberSaveable {
        mutableStateOf("")
    }

    Box(
        contentAlignment = Alignment.TopCenter,
    ){
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.2f)
            ,
            painter = painterResource(id = R.drawable.shape),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .padding(top = 90.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ){
            AppNameText(string = text1)
            Spacer(modifier = Modifier.height(100.dp))
            TitlePageText(string = text2)

        }
    }
}


@Composable
fun RegisterButton(
    navController: NavController
){
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height((55.dp)),
        onClick = { DBRegister(navController).evaluateRegister()},
        colors = ButtonDefaults.buttonColors(colorResource(R.color.orange)),
        shape = RoundedCornerShape(size = 50.dp)
    ){
        Text(text = "Register", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium), color = Color.Black)
    }
}

@Composable
fun LoginButton(
    navController: NavController
){
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height((55.dp)),
        onClick = { DBLogin(navController).evaluateLogin()},
        colors = ButtonDefaults.buttonColors(colorResource(R.color.orange)),
        shape = RoundedCornerShape(size = 50.dp)
    ){
        Text(text = "Login", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium), color = Color.Black)
    }
}


@Composable
fun EmailEnter(
    modifier: Modifier = Modifier,
    label: String,
    onChange: (String) -> Unit = {}
){
    var text by rememberSaveable { mutableStateOf("")}

    TextField(
        modifier = modifier.border(2.dp, color = colorResource(id = R.color.orange), shape = RoundedCornerShape(50)),
        value = text,
        onValueChange = {text = it
            onChange(it)},
        label = { 
                Text(text = label, style = MaterialTheme.typography.labelMedium, color = Black)
        },
        colors =  TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )

}

@Composable
fun InfoEnter(
    modifier: Modifier = Modifier,
    label: String,
    onChange: (String) -> Unit = {}
){
    var text by rememberSaveable { mutableStateOf("")}

    TextField(
        modifier = modifier.border(2.dp, color = colorResource(id = R.color.orange), shape = RoundedCornerShape(50)),
        value = text,
        onValueChange = {text = it
            onChange(it)},
        label = {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = Black)
        },
        colors =  TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}


@Composable
fun PasswordEnter(
    modifier: Modifier = Modifier,
    label: String,
    onChange: (String) -> Unit = {}){

    var text by rememberSaveable { mutableStateOf("")}

    TextField(
        modifier = modifier.border(2.dp, color = colorResource(id = R.color.orange), shape = RoundedCornerShape(50)),
        value = text,
        onValueChange = {text = it
            onChange(it)},
        label = {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = Black)
        },
        visualTransformation = PasswordVisualTransformation(),
        colors =  TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )

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
            text = stringResource(id = R.string.or),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Divider(
            modifier = Modifier.weight(1f),
            color = colorResource(R.color.orangeUltraLight),
            thickness = 1.dp
        )
    }
}


