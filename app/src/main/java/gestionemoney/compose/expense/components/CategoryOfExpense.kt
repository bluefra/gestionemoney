package gestionemoney.compose.expense.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryOfExpense(categoryList: List<String>, standardOption: String, onChange: (String) -> Unit = {}){
    Text(
        text = "Select the category you want to add the expense to:" ,
        fontSize = 15.sp ,
        fontFamily = FontFamily.Monospace ,
        textAlign = TextAlign.Center ,
        fontWeight = FontWeight.ExtraBold ,
        modifier = Modifier.padding(top = 10.dp , bottom = 10.dp)
    )
    DropdownCategoryMenu(categoryList, standardOption)
}