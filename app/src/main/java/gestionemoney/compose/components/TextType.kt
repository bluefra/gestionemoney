package gestionemoney.compose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gestionemoney.compose.R
import gestionemoney.compose.ui.theme.Black
import gestionemoney.compose.ui.theme.Roboto


//
//Class with all the compasable function used in the texts on the app
//

@Composable
fun AppNameText(string: String){
    Text(
        text = string,
        fontSize = 45.sp,
        fontFamily = FontFamily.Cursive,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
fun TitlePageText(string: String){
    Text(
        modifier = Modifier
            .padding(bottom = 10.dp),
        text = string,
        fontSize = 30.sp,
        style = MaterialTheme.typography.titleMedium,
        color = Black,

    )
}

@Composable
fun MediumText(string: String){
    Text(
        modifier = Modifier
            .padding(bottom = 10.dp),
        text = string,
        fontSize = 25.sp,
        color = Black,
        style = MaterialTheme.typography.bodyLarge,
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun NormalText(string: String){
    Text(
        //modifier = Modifier.padding(bottom = 10.dp),
        text = string,
        fontSize = 14.sp,
        style = MaterialTheme.typography.bodySmall,
        color = Black,
        fontFamily = Roboto,
    )
}

@Composable
fun TextFiledType(string : String){
    Text(
        //modifier = Modifier.padding(bottom = 10.dp),
        text = string,
        fontSize = 13.sp,
        style = MaterialTheme.typography.bodySmall,
        fontFamily = Roboto,
        color = colorResource(R.color.dark_grey)

    )


}
