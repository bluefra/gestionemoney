package gestionemoney.compose.expense.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gestionemoney.compose.R

@Composable
fun CostOfExpense(onChange: (String) -> Unit = {}) {

    var text by rememberSaveable { mutableStateOf("") }

    Text(
        text = stringResource(id = R.string.insert_money_spent) ,
        fontSize = 15.sp ,
        fontFamily = FontFamily.Monospace ,
        textAlign = TextAlign.Center ,
        fontWeight = FontWeight.ExtraBold ,
        modifier = Modifier.padding(top = 10.dp , bottom = 10.dp)
    )
    TextField(
        value = text,
        onValueChange = { text = it
                        onChange(it)},
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        label = { Text(text = stringResource(id = R.string.money_type)) },
    )
}

@Preview
@Composable
fun CostOfExpensePreview(){
    CostOfExpense()
}