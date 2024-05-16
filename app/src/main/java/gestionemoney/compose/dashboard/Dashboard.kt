package gestionemoney.compose.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.components.BackButton
import gestionemoney.compose.ui.theme.Black


@Composable
fun Dashboard(
    navController: NavController
) {
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            ) {
            BackButton(navController)
        }
        Spacer(modifier = Modifier.width(5.dp))
        Divider(
            color = colorResource(R.color.orangeUltraLight),
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.width(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(alignment = Alignment.CenterVertically),
                text = "Analisi delle tue spese",
                style = MaterialTheme.typography.titleMedium,
                color = Black
            )
        }


    }
}


