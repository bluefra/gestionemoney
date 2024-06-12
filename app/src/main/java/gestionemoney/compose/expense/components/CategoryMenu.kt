package gestionemoney.compose.expense.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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

@Composable
fun CategoryMenu(categoryList: List<String>, standardOption: String, onChange: (String) -> Unit = {}){
    NormalText(string = stringResource(id = R.string.category_expense_insert))
    Spacer(modifier = Modifier.height(5.dp))
    DropdownCategoryMenu(categoryList, standardOption, onChange)
}

@Composable
fun CategoryMenuImage(categoryList: List<String>, standardOption: String, onChange: (String) -> Unit = {}){
    NormalText(string = stringResource(R.string.category_image_selection))
    Spacer(modifier = Modifier.height(5.dp))
    DropdownCategoryMenu(categoryList, standardOption, onChange)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DropdownCategoryMenu(categoryList: List<String>, standardOption: String, onChange: (String) -> Unit = {}) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedCategory by rememberSaveable { mutableStateOf(standardOption) }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Composable native function.
        ExposedDropdownMenuBox(
            expanded = expanded ,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier
                .border(2.dp, colorResource(id = R.color.orange), shape = RoundedCornerShape(50))

        ) {
            TextField(
                value = selectedCategory ,
                onValueChange = {
                    selectedCategory = it
                    onChange(it)
                },
                enabled = false,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) } ,
                modifier = Modifier
                    .background(Color.Transparent)
                    .menuAnchor()
                ,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}
                ),
                colors =  TextFieldDefaults.colors(
                    disabledContainerColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedTrailingIconColor = Color.Transparent,
                    unfocusedTrailingIconColor = Color.Transparent,
                    disabledTrailingIconColor = Color.Transparent,
                )
            )
            //Composable native function.
            ExposedDropdownMenu(
                expanded = expanded ,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(colorResource(id = R.color.orangeLight))
            ) {
                categoryList.forEach { category ->
                    DropdownMenuItem(
                        text = {
                            TextFiledType(string = category)
                            },
                        onClick = {
                            selectedCategory = category
                            expanded = false
                            onChange(category)
                        }
                    )
                }
            }
        }
}