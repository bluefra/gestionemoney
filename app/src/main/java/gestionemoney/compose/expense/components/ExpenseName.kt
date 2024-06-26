package gestionemoney.compose.expense.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import gestionemoney.compose.R
import gestionemoney.compose.ui.theme.NormalText
import gestionemoney.compose.ui.theme.TextFiledType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ExpenseName(onChange: (String) -> Unit = {}) {

    var text by rememberSaveable { mutableStateOf("") }
    val maxChar = 15
    val keyboardController = LocalSoftwareKeyboardController.current


    NormalText(string = stringResource(id = R.string.insert_expense_name))
    Spacer(modifier = Modifier.height(5.dp))
    TextField(
        modifier = Modifier.border(2.dp, color = colorResource(id = R.color.orange) , shape = RoundedCornerShape(50)),
        value = text,
        onValueChange = {
            if (it.length <= maxChar) {
                text = it
                onChange(it)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {keyboardController?.hide()}
        ),
        label = { TextFiledType(string = stringResource(id = R.string.expense_name)) },
        singleLine = true,
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