package gestionemoney.compose.newcategory.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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
import gestionemoney.compose.components.MediumText
import gestionemoney.compose.components.TextFiledType

// Composable function to display the new category text field.
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NewCategoryNameTextField(onChange: (String) -> Unit = {}) {
    var category by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    MediumText(string = stringResource(id = R.string.new_category_add))
    Spacer(modifier = Modifier.height(5.dp))
    TextField(
        modifier = Modifier.border(2.dp, color = colorResource(id = R.color.orange), shape = RoundedCornerShape(50)),
        value = category ,
        onValueChange = {
            category = it
            onChange(it)
        },
        label = {
            TextFiledType(string = stringResource(id = R.string.category_name))} ,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {keyboardController?.hide()}
        ),
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
