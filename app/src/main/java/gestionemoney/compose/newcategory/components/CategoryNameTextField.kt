package gestionemoney.compose.newcategory.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gestionemoney.compose.R
import gestionemoney.compose.components.MediumText
import gestionemoney.compose.components.TextFiledType

// Composable function to display the new category text field.
@Composable
fun NewCategoryNameTextField(onChange: (String) -> Unit = {}) {
    var category by rememberSaveable { mutableStateOf("") }

    MediumText(string = stringResource(id = R.string.new_category_add))
    Spacer(modifier = Modifier.height(5.dp))
    TextField(
        value = category ,
        onValueChange = {
            category = it
            onChange(it)
        },
        label = {
            TextFiledType(string = stringResource(id = R.string.category_name))} ,
    )
}

@Preview
@Composable
fun NewCategoryNameTextFieldPreview(){
    NewCategoryNameTextField()
}