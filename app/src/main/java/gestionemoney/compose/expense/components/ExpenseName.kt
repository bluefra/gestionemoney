package gestionemoney.compose.expense.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gestionemoney.compose.R
import gestionemoney.compose.components.NormalText
import gestionemoney.compose.components.TextFiledType

@Composable
fun ExpenseName(onChange: (String) -> Unit = {}) {

    var text by rememberSaveable { mutableStateOf("") }
    val maxChar : Int = 15

    NormalText(string = stringResource(id = R.string.insert_expense_name))
    Spacer(modifier = Modifier.height(5.dp))
    TextField(
        value = text,
        onValueChange = {
            if (it.length <= maxChar) {
                text = it
                onChange(it)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
        ),
        label = { TextFiledType(string = stringResource(id = R.string.expense_name))},
        singleLine = true
    )
}